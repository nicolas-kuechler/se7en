package ch.uzh.se.se7en.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import ch.uzh.se.se7en.client.rpc.TriggerImportService;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Used to import film data from csv files in the Google Cloud Storage file
 * repository
 * 
 * @author Cyrill Halter, Roland Schläfli
 *
 */
@Singleton
public class TriggerImportServiceImpl extends RemoteServiceServlet implements TriggerImportService {

	@Inject
	Provider<EntityManager> em;

	// create maps of already existing entities keyed by name for more
	// efficient importing
	private Map<String, CountryDB> countryMap = new HashMap<String, CountryDB>();
	private Map<String, GenreDB> genreMap = new HashMap<String, GenreDB>();
	private Map<String, LanguageDB> languageMap = new HashMap<String, LanguageDB>();
	private final String BUCKET_NAME = "se-team-se7en";

	/**
	 * This method is called to to import a file from the Google Cloud Storage
	 * file repository and convert it to a list of film objects
	 * 
	 * @author Cyrill Halter
	 * @pre A correctly formatted csv file has been deposited in the Cloud
	 *      Storage
	 * @post -
	 * @param String
	 *            nameOfFile The name of the csv file that should be imported
	 */
	@Override
	public String importFile(String nameOfFile, String password) {
		// TODO: activate pw checking if implemented client-side
		/*if(password != "qBWdyY:t<{I*O@[R<3#_4{Of8:u%ar3r5i+].kC0'noeoc0v5;zQb|/%W6T22nu") {
			return "INVALID_PASSWORD";
		}*/
		
		List<Film> importedFilms = null;
		try {

			GcsService gcsService = GcsServiceFactory.createGcsService();
			GcsFilename gcsFilename = new GcsFilename(BUCKET_NAME, nameOfFile);

			// open GCS channel for specified file name and create reader
			GcsInputChannel csvReadChannel = gcsService.openReadChannel(gcsFilename, 0);
			Reader csvFileReader = new InputStreamReader(Channels.newInputStream(csvReadChannel), "UTF-8");

			// create csv reader on inputstream reader
			CSVReader<Film> filmReader = new CSVReaderBuilder<Film>(csvFileReader).entryParser(new FilmEntryParser())
					.build();

			// read csv to Film objects
			importedFilms = filmReader.readAll();

		} catch (IOException e) {
			e.printStackTrace();
			return "IO_EXCEPTION";
		}

		// import the films into the db
		if (importFilmsToDB(importedFilms)) {
			return "SUCCESS";
		}

		return "UNKNOWN_ERROR";
	}

	/**
	 * Imports the parsed films into the database
	 * 
	 * @author Roland Schläfli
	 * @pre A deposited csv has been correctly parsed into a list of Film
	 *      objects.
	 * @post -
	 * @param List<Film>
	 *            films A list of film objects to import
	 * @return boolean Whether the import was successfully pushed to the
	 *         database
	 */
	@Transactional
	public boolean importFilmsToDB(List<Film> films) {
		// TODO: replace this boolean with a parameter
		boolean force = false;

		// get an instance of the entity manager
		EntityManager manager = em.get();

		boolean success = false;

		// clear the hashmaps to remove any existing entities from previous
		// imports
		countryMap.clear();
		genreMap.clear();
		languageMap.clear();

		// if the import was forced, don't get the entities from the database
		// only do this if the db was purged beforehand!!! (resolves caching
		// problem)
		if (!force) {
			// get all the existing entities from the database
			List<CountryDB> dbCountries = manager.createQuery("from CountryDB", CountryDB.class).getResultList();
			List<GenreDB> dbGenres = manager.createQuery("from GenreDB", GenreDB.class).getResultList();
			List<LanguageDB> dbLanguages = manager.createQuery("from LanguageDB", LanguageDB.class).getResultList();

			// hydrate the maps with the existing entities
			for (CountryDB country : dbCountries) {
				countryMap.put(country.getName(), country);
			}
			for (GenreDB genre : dbGenres) {
				genreMap.put(genre.getName(), genre);
			}
			for (LanguageDB language : dbLanguages) {
				languageMap.put(language.getName(), language);
			}
		}

		int i = 0;
		// iterate over each new film
		for (Film film : films) {
			i++;

			// query batching to prevent out of memory exception
			if (i % 50 == 0) {
				manager.flush();
				manager.clear();
			}

			// extract information from the film object and initialize the sets
			Set<String> countries = film.getCountries() == null || film.getCountries().isEmpty() ? new HashSet<String>()
					: new HashSet<String>(film.getCountries());
			Set<String> genres = film.getGenres() == null || film.getGenres().isEmpty() ? new HashSet<String>()
					: new HashSet<String>(film.getGenres());
			Set<String> languages = film.getLanguages() == null || film.getLanguages().isEmpty() ? new HashSet<String>()
					: new HashSet<String>(film.getLanguages());

			// initialize empty entity sets for the join table entites
			Set<FilmCountryDB> filmCountryEntities = new HashSet<FilmCountryDB>();
			Set<FilmGenreDB> filmGenreEntities = new HashSet<FilmGenreDB>();
			Set<FilmLanguageDB> filmLanguageEntities = new HashSet<FilmLanguageDB>();

			// create a new FilmDB object with the basic content
			FilmDB dbFilm = new FilmDB(film.getName(), film.getLength(), film.getYear());
			dbFilm.setWikipedia(film.getWikipedia());

			dbFilm.setCountryString(asSortedString(countries));
			dbFilm.setGenreString(asSortedString(genres));
			dbFilm.setLanguageString(asSortedString(languages));
			
			// for each country, look it up and add it if necessary
			for (String c : countries) {
				CountryDB country;

				// if the country already exists, get the entity, else persist a
				// new one
				if (countryMap.containsKey(c)) {
					country = countryMap.get(c);
				} else {
					country = new CountryDB(c);

					try {
						// persist the new country and add it to the local map
						manager.persist(country);
						countryMap.put(c, country);
					} catch (EntityExistsException e) {
						return false;
					}
				}

				// create a new join table entity
				filmCountryEntities.add(new FilmCountryDB(dbFilm, country));
			}

			// save the country join table entities into the film
			dbFilm.setFilmCountryEntities(filmCountryEntities);

			// for each genre, look it up and add it if necessary
			for (String g : genres) {
				GenreDB genre;

				// if the genre already exists, get the entity, else persist a
				// new one
				if (genreMap.containsKey(g)) {
					genre = genreMap.get(g);
				} else {
					genre = new GenreDB(g);

					try {
						// persist the new genre and add it to the local map
						manager.persist(genre);
						genreMap.put(g, genre);
					} catch (EntityExistsException e) {
						return false;
					}
				}

				// create a new join table entity
				filmGenreEntities.add(new FilmGenreDB(dbFilm, genre));
			}

			// save the genre join table entities into the film
			dbFilm.setFilmGenreEntities(filmGenreEntities);

			// for each language, look it up and add it if necessary
			for (String l : languages) {
				LanguageDB language;

				// if the language already exists, get the entity, else persist
				// a
				// new one
				if (languageMap.containsKey(l)) {
					language = languageMap.get(l);
				} else {
					language = new LanguageDB(l);

					try {
						// persist the new language and add it to the local map
						manager.persist(language);
						languageMap.put(l, language);
					} catch (EntityExistsException e) {
						return false;
					}
				}

				// create a new join table entity
				filmLanguageEntities.add(new FilmLanguageDB(dbFilm, language));
			}

			// save the language join table entities into the film
			dbFilm.setFilmLanguageEntities(filmLanguageEntities);
			
			// persist the new film
			try {
				manager.persist(dbFilm);
				// System.out.println(dbFilm.getName());
				success = true;
			} catch (EntityExistsException e) {
				return false;
			}

		}

		return success;
	}

	/**
	 * @pre -
	 * @post em==em
	 * @param em
	 *            the em to set
	 */
	public void setEm(Provider<EntityManager> em) {
		this.em = em;
	}

	/**
	 * @pre countryMap!= null
	 * @post -
	 * @return the countryMap
	 */
	public Map<String, CountryDB> getCountryMap() {
		return countryMap;
	}

	/**
	 * @pre genreMap!= null
	 * @post -
	 * @return the genreMap
	 */
	public Map<String, GenreDB> getGenreMap() {
		return genreMap;
	}

	/**
	 * @pre languageMap!= null
	 * @post -
	 * @return the languageMap
	 */
	public Map<String, LanguageDB> getLanguageMap() {
		return languageMap;
	}

	/**
	 * Static method for converting an unsorted collection of strings into its string representation
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param Collection<String>
	 *            An unsorted collection of strings
	 * @return String A string containing the sorted items of the collection
	 */
	public static String asSortedString(Collection<String> c) {
		// TODO: test
		List<String> list = new ArrayList<String>(c);
		String sorted = "";
		
		java.util.Collections.sort(list);
		
		for(String string : list) {
			if(sorted.length() == 0) {
				sorted += string;
			} else {
				sorted += " / " + string;
			}
		}
		
		return sorted;
	}
}
