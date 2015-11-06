package ch.uzh.se.se7en.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;
import ch.uzh.se.se7en.shared.model.SelectOption;

/**
 * Handles the server side of RPC requests, coordinates with the database
 * 
 * @author Roland Schläfli
 */
@Singleton
public class FilmListServiceImpl extends RemoteServiceServlet implements FilmListService {
	// guice injection of an entity manager
	@Inject
	Provider<EntityManager> em;

	/**
	 * Returns a filtered list of films to the client
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<Film> films The filtered list of film data transfer objects
	 */
	@Override
	public List<Film> getFilmList(FilmFilter filter) {
		// query the database for filtered entities
		List<FilmDB> dbFilms = getFilmEntitiesList(filter);

		// create an empty list of films
		List<Film> films = new ArrayList<Film>();

		// convert each FilmDB instance to a Film DataTransferObject
		for (FilmDB film : dbFilms) {
			Film f = film.toFilm();
			films.add(f);
		}

		// return the filled list of movies
		return films;
	}

	/**
	 * Returns a filtered list of film entities
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<FilmDB> dbFilms A filtered list of film entities
	 */
	@Transactional
	private List<FilmDB> getFilmEntitiesList(FilmFilter filter) {
		// create an empty list of film entities
		List<FilmDB> dbFilms = new ArrayList<FilmDB>();

		// initialize the selector in the query
		String selector = "SELECT DISTINCT f from FilmDB f";

		// initialize the where string with the basic filters
		String wheres = "WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear)";

		// initialize an empty string for all the joins
		String joiners = "";

		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += " AND LOWER(f.name) LIKE :findName";
		}

		// if at least one country is in the list of filter countries
		if (filter.getCountryIds() != null) {
			joiners += " JOIN f.filmCountryEntities fc";
			wheres += " AND fc.countryId IN :countryIds";
		}

		// if at least one genre is in the list of filter genres
		if (filter.getGenreIds() != null) {
			joiners += " JOIN f.filmGenreEntities fg";
			wheres += " AND fg.genreId IN :genreIds";
		}

		// if at least one language is in the list of filter languages
		if (filter.getLanguageIds() != null) {
			joiners += " JOIN f.filmLanguageEntities fl";
			wheres += " AND fl.languageId IN :languageIds";
		}

		// concat the query string
		String queryString = selector + " " + joiners + " " + wheres;

		// create a typed query from our query string
		TypedQuery<FilmDB> query = em.get().createQuery(queryString, FilmDB.class);

		// set the min & max length params
		query.setParameter("minLength", filter.getLengthStart());
		query.setParameter("maxLength", filter.getLengthEnd());

		// set the min & max year params
		query.setParameter("minYear", filter.getYearStart());
		query.setParameter("maxYear", filter.getYearEnd());

		// if the name in the filter is set, set the param
		if (filter.getName() != null) {
			// use % to also find incomplete words
			query.setParameter("findName", "%" + filter.getName().toLowerCase() + "%");
		}

		// if there are countries specified as a list of Integers
		if (filter.getCountryIds() != null) {
			query.setParameter("countryIds", filter.getCountryIds());
		}

		// if there are genres specified as a list of Integers
		if (filter.getGenreIds() != null) {
			query.setParameter("genreIds", filter.getGenreIds());
		}

		// if there are languages specified as a list of Integers
		if (filter.getLanguageIds() != null) {
			query.setParameter("languageIds", filter.getLanguageIds());
		}

		// execute the query
		dbFilms = query.getResultList();

		// return the list of film entities
		return dbFilms;
	}

	/**
	 * Returns a filtered list of countries to the client
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<Country> countries The filtered list of country data
	 *         transfer objects
	 */
	@Override
	public List<Country> getCountryList(FilmFilter filter) {
		// create an empty list of countries
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		List<Country> countries = new ArrayList<Country>();

		// fetch the list of filtered country entities from the database
		dbCountries = getCountryEntitiesList(filter);

		// get the current year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		// convert each CountryDB instance to a Country DataTransferObject
		for (CountryDB country : dbCountries) {
			Country c = country.toCountry();

			// set the wanted countries to only this id
			Set<Integer> wantedCountries = new HashSet<Integer>();
			wantedCountries.add(c.getId());
			filter.setCountryIds(wantedCountries);

			// get the filtered films for each country
			List<FilmDB> filteredFilms = getFilmEntitiesList(filter);

			// initialize a new array for all years
			int[] filmsPerYear = new int[currentYear - Country.YEAR_OFFSET + 1];

			// for each film entity, increment it's year in the array
			for (FilmDB film : filteredFilms) {
				filmsPerYear[film.getYear() - Country.YEAR_OFFSET]++;
			}

			// start the number of films array transformation
			c.setNumberOfFilms(filmsPerYear);

			// add the country to the result list
			countries.add(c);
		}

		// return the filled list of countries
		return countries;
	}

	/**
	 * Returns a filtered list of country entities
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<CountryDB> dbCountries A filtered list of country entities
	 */
	@Transactional
	private List<CountryDB> getCountryEntitiesList(FilmFilter filter) {
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		// initialize the selector in the query
		String selector = "SELECT DISTINCT c FROM CountryDB c";

		// initialize the where string with the basic filters
		String wheres = "WHERE (fi.length BETWEEN :minLength AND :maxLength) AND (fi.year BETWEEN :minYear AND :maxYear)";

		// initialize an empty string for all the joins
		String joiners = "JOIN c.filmCountryEntities fc JOIN fc.primaryKey.film fi";

		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += " AND LOWER(fi.name) LIKE :findName";
		}

		// if at least one genre is in the list of filter genres
		if (filter.getGenreIds() != null) {
			joiners += " JOIN fi.filmGenreEntities fg";
			wheres += " AND fg.genreId IN :genreIds";
		}

		// if at least one language is in the list of filter languages
		if (filter.getLanguageIds() != null) {
			joiners += " JOIN fi.filmLanguageEntities fl";
			wheres += " AND fl.languageId IN :languageIds";
		}

		// build the query string
		String queryString = selector + " " + joiners + " " + wheres;

		// select all countries from the database
		TypedQuery<CountryDB> query = em.get().createQuery(queryString, CountryDB.class);

		// set the min & max length params
		query.setParameter("minLength", filter.getLengthStart());
		query.setParameter("maxLength", filter.getLengthEnd());

		// set the min & max year params
		query.setParameter("minYear", filter.getYearStart());
		query.setParameter("maxYear", filter.getYearEnd());

		// if the name in the filter is set, set the param
		if (filter.getName() != null) {
			// use % to also find incomplete words
			query.setParameter("findName", "%" + filter.getName().toLowerCase() + "%");
		}

		// if there are genres specified as a list of Integers
		if (filter.getGenreIds() != null) {
			query.setParameter("genreIds", filter.getGenreIds());
		}

		// if there are languages specified as a list of Integers
		if (filter.getLanguageIds() != null) {
			query.setParameter("languageIds", filter.getLanguageIds());
		}

		// execute the query
		dbCountries = query.getResultList();

		// return the filtered list of countries
		return dbCountries;
	}

	/**
	 * Returns a filtered list of genres to the client
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<Genre> genres The filtered list of genre data transfer
	 *         objects
	 */
	@Override
	@Transactional
	public List<Genre> getGenreList(FilmFilter filter) {
		// TODO: real implementation
		
		// create an empty list of countries
		List<GenreDB> dbGenres = new ArrayList<GenreDB>();
		List<Genre> genres = new ArrayList<Genre>();

		// select all genres from the database
		dbGenres = em.get().createQuery("from GenreDB", GenreDB.class).getResultList();

		// convert each GenreDB instance to a Genre DataTransferObject
		for (GenreDB genre : dbGenres) {
			Genre g = genre.toGenre();
			System.out.println(g);
			genres.add(g);
		}

		// return the filled list of countries
		return genres;
	}

	/***
	 * Returns a list of all available genres as options (names and ids) to the
	 * client for use in the filter's multiselect boxes.
	 * 
	 * @author Cyrill Halter, Roland Schläfli
	 * @pre -
	 * @post -
	 * @param -
	 * @return List<SelectOption> availableGenres The list of all available
	 *         genres as SelectOption objects
	 */
	@Override
	public List<SelectOption> getGenreSelectOption() {
		List<SelectOption> availableGenres = new ArrayList<SelectOption>();
		List<GenreDB> dbGenres = new ArrayList<GenreDB>();

		// select * from the genre table
		dbGenres = em.get().createQuery("from GenreDB order by name", GenreDB.class).getResultList();

		for (GenreDB genre : dbGenres) {
			availableGenres.add(new SelectOption(genre.getId(), genre.getName()));
		}

		return availableGenres;
	}

	/***
	 * Returns a list of all available countries as options (names and ids) to
	 * the client for use in the filter's multiselect boxes.
	 * 
	 * @author Cyrill Halter, Roland Schläfli
	 * @pre -
	 * @post -
	 * @param -
	 * @return List<SelectOption> availableCountries The list of all available
	 *         countries as SelectOption objects
	 */
	@Override
	public List<SelectOption> getCountrySelectOption() {
		List<SelectOption> availableCountries = new ArrayList<SelectOption>();
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();

		// select * from the country table
		dbCountries = em.get().createQuery("from CountryDB order by name", CountryDB.class).getResultList();

		for (CountryDB country : dbCountries) {
			availableCountries.add(new SelectOption(country.getId(), country.getName()));
		}

		return availableCountries;
	}

	/***
	 * Returns a list of all available languages as options (names and ids) to
	 * the client for use in the filter's multiselect boxes.
	 * 
	 * @author Cyrill Halter, Roland Schläfli
	 * @pre -
	 * @post -
	 * @param -
	 * @return List<SelectOption> availableLanguages The list of all available
	 *         languages as SelectOption objects
	 */
	@Override
	public List<SelectOption> getLanguageSelectOption() {
		List<SelectOption> availableLanguages = new ArrayList<SelectOption>();
		List<LanguageDB> dbLanguages = new ArrayList<LanguageDB>();

		// select * from the language table
		dbLanguages = em.get().createQuery("from LanguageDB order by name", LanguageDB.class).getResultList();

		for (LanguageDB language : dbLanguages) {
			availableLanguages.add(new SelectOption(language.getId(), language.getName()));
		}

		return availableLanguages;
	}
}
