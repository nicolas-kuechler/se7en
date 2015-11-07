package ch.uzh.se.se7en.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
import ch.uzh.se.se7en.server.model.FilmHelper;
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
	public boolean importFile(String nameOfFile) {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		GcsFilename gcsFilename = new GcsFilename("se-team-se7en", nameOfFile);
		List<Film> importedFilms = new LinkedList<Film>();

		try {

			// open GCS channel for specified file name and create reader
			GcsInputChannel csvReadChannel = gcsService.openReadChannel(gcsFilename, 0);
			Reader csvFileReader = new InputStreamReader(Channels.newInputStream(csvReadChannel));

			// create csv reader on inputstream reader
			ValueProcessorProvider vpp = new ValueProcessorProvider();
			CSVReader<FilmHelper> filmReader = new CSVReaderBuilder<FilmHelper>(csvFileReader)
					.entryParser(new AnnotationEntryParser<FilmHelper>(FilmHelper.class, vpp)).build();

			// read csv to FilmHelper objects, convert them to Film objects and
			// add them to the importedFilms List
			FilmHelper tempFilm;
			while ((tempFilm = filmReader.readNext()) != null) {
				importedFilms.add(new Film(tempFilm.getName(), tempFilm.getLength(), tempFilm.getYear(),
						new ArrayList<String>(Arrays.asList(tempFilm.getCountries().split("--"))),
						new ArrayList<String>(Arrays.asList(tempFilm.getLanguages().split("--"))),
						new ArrayList<String>(Arrays.asList(tempFilm.getGenres().split("--")))));
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// import the films into the db
		if (importFilmsToDB(importedFilms)) {
			return true;
		}

		return false;
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
		// get an instance of the entity manager
		EntityManager manager = em.get();
		
		boolean success = false;
		
		// create maps of already existing entities keyed by name for more efficient importing
		Map<String, CountryDB> countryMap = new HashMap<String, CountryDB>();
		Map<String, GenreDB> genreMap = new HashMap<String, GenreDB>();
		Map<String, LanguageDB> languageMap = new HashMap<String, LanguageDB>();
		
		// get all the existing entities from the database
		List<CountryDB> dbCountries = manager.createQuery("from CountryDB", CountryDB.class).getResultList();
		List<GenreDB> dbGenres = manager.createQuery("from GenreDB", GenreDB.class).getResultList();
		List<LanguageDB> dbLanguages = manager.createQuery("from LanguageDB", LanguageDB.class).getResultList();
		
		// hydrate the maps with the existing entities
		for(CountryDB country : dbCountries) {
			countryMap.put(country.getName(), country);
		}
		for(GenreDB genre : dbGenres) {
			genreMap.put(genre.getName(), genre);
		}
		for(LanguageDB language : dbLanguages) {
			languageMap.put(language.getName(), language);
		}
		
		// iterate over each new film
		for (Film film : films) {
			// extract information from the film object and initialize the sets
			Set<String> countries = film.getCountries().isEmpty() ? new HashSet<String>() : new HashSet<String>(film.getCountries());
			Set<String> genres = film.getGenres().isEmpty() ? new HashSet<String>() : new HashSet<String>(film.getGenres());
			Set<String> languages = film.getLanguages().isEmpty() ? new HashSet<String>() : new HashSet<String>(film.getLanguages());
			
			// initialize empty entity lists for the join table
			Set<FilmCountryDB> filmCountryEntities = new HashSet<FilmCountryDB>();
			Set<FilmGenreDB> filmGenreEntities = new HashSet<FilmGenreDB>();
			Set<FilmLanguageDB> filmLanguageEntities = new HashSet<FilmLanguageDB>();

			// create a new FilmDB object with the basic content
			FilmDB dbFilm = new FilmDB(film.getName(), film.getLength(), film.getYear());
			
			// for each country, look it up and add it if necessary
			for (String c : countries) {
				CountryDB country;
				
				// if the country already exists, get the entity, else persist a
				// new one
				if(countryMap.containsKey(c)) {
					country = countryMap.get(c);
				} else {
					country = new CountryDB(c);
					
					try {
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
				if(genreMap.containsKey(g)) {
					genre = genreMap.get(g);
				} else {
					genre = new GenreDB(g);
					
					try {
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

				// if the language already exists, get the entity, else persist a
				// new one
				if(languageMap.containsKey(l)) {
					language = languageMap.get(l);
				} else {
					language = new LanguageDB(l);
					
					try {
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
				success = true;
			} catch (EntityExistsException e) {
				return false;
			}

		}
		
		return success;
	}
}
