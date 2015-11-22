package ch.uzh.se.se7en.server;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.FilterOptions;
import ch.uzh.se.se7en.shared.model.Genre;

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

	// TODO: find other way of caching stuff
	public FilmFilter cachedFilter;
	public List<FilmDB> cachedFilms;
	public HashMap<Integer, String> cachedCountries;
	public HashMap<Integer, String> cachedGenres;
	public HashMap<Integer, String> cachedLanguages;

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
	public List<FilmDB> getFilmEntitiesList(FilmFilter filter) {
		if (cachedFilter != null && cachedFilms != null && filter.equals(cachedFilter)) {
			return cachedFilms;
		}

		// the starting position of the query
		// TODO: replace by filter information?
		int startPosition = 0;

		// the max number of results the query should return
		// TODO: replace by filter information?
		int maxResults = 80000;

		// defines the ordering of the query results
		// TODO: replace by filter information?
		String ordering = "f.name";

		// create an empty list of film entities
		List<FilmDB> dbFilms = new ArrayList<FilmDB>();

		// initialize the selector in the query
		String selector = "SELECT DISTINCT f FROM FilmDB f";

		// initialize the where string with the basic filters
		String wheres = "";
		String whereLength = "";
		String whereYear = "";

		// only filter for a length if the filter values are not the defaults
		if (filter.getLengthStart() > 0 || filter.getLengthEnd() < 600) {
			whereLength = "(f.length BETWEEN :minLength AND :maxLength)";
			wheres += whereLength;
		}

		// only filter for a year if the filter values are not the defaults
		if (filter.getYearStart() > 1890 || filter.getYearEnd() < 2015) {
			if (wheres.length() > 0) {
				wheres += " AND ";
			}

			whereYear = "(f.year BETWEEN :minYear AND :maxYear)";
			wheres += whereYear;
		}

		// if some where clauses have been set already
		if (wheres.length() > 0) {
			wheres = "WHERE " + wheres;
		} else {
			// TODO: refactor so that we don't need this :)
			wheres = "WHERE 1=1";
		}

		// initialize an empty string for all the joins
		String joiners = "";

		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += " AND LOWER(f.name) LIKE :findName";
		}

		// if at least one country is in the list of filter countries
		if (filter.getCountryIds() != null) {
			joiners += " JOIN FETCH f.filmCountryEntities fc";
			wheres += " AND fc.countryId IN (:countryIds)";
		}

		// if at least one genre is in the list of filter genres
		if (filter.getGenreIds() != null) {
			joiners += " JOIN f.filmGenreEntities fg";
			wheres += " AND fg.genreId IN (:genreIds)";
		}

		// if at least one language is in the list of filter languages
		if (filter.getLanguageIds() != null) {
			joiners += " JOIN f.filmLanguageEntities fl";
			wheres += " AND fl.languageId IN (:languageIds)";
		}

		// concat the query string
		String queryString = selector + joiners + " " + wheres + " ORDER BY " + ordering;

		// create a typed query from our query string
		TypedQuery<FilmDB> query = em.get().createQuery(queryString, FilmDB.class);

		// make the query cacheable if it is select *
		// TODO: query cache
		/*
		 * if(wheres == "WHERE 1=1") { query.setHint(QueryHints.CACHEABLE,
		 * true); }
		 */

		// set offset and limit
		query.setFirstResult(startPosition);
		query.setMaxResults(maxResults);

		// if filter for length != defaults
		if (whereLength.length() > 0) {
			// set the min & max length params
			query.setParameter("minLength", filter.getLengthStart());
			query.setParameter("maxLength", filter.getLengthEnd());
		}

		// if filter for year != defaults
		if (whereYear.length() > 0) {
			// set the min & max year params
			query.setParameter("minYear", filter.getYearStart());
			query.setParameter("maxYear", filter.getYearEnd());
		}

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

		// fill the local "cache"
		// TODO: use another way of caching
		cachedFilter = filter;
		cachedFilms = dbFilms;

		// return the list of film entities
		return dbFilms;
	}

	/**
	 * Returns a filtered list of countries to the client
	 * 
	 * @author Roland Schläfli
	 * @pre There are only films with years 1890-2015 in the database
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 * @return List<Country> countries The filtered list of country data
	 *         transfer objects
	 */
	@Transactional
	@Override
	public List<Country> getCountryList(FilmFilter filter) {
		// create an empty list of countries
		// List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		List<Country> countries = new ArrayList<Country>();

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		// build the query
		String selector = "SELECT c.id, c.name, f.year, COUNT(*)";

		// initialize the where string with the basic filters
		String wheres = "";
		String whereLength = "";

		if (filter.getLengthStart() > 0 || filter.getLengthEnd() < 600) {
			whereLength = "WHERE (f.length BETWEEN :minLength AND :maxLength)";
			wheres += whereLength;
		} else {
			wheres = "WHERE 1=1";
		}

		String joiners = "JOIN film_countries fc ON f.id = fc.film_id JOIN countries c ON fc.country_id = c.id";

		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += " AND LOWER(f.name) LIKE :findName";
		}

		// if at least one genre is in the list of filter genres
		if (filter.getGenreIds() != null) {
			joiners += " JOIN film_genres fg ON f.id = fg.film_id";
			wheres += " AND fg.genre_id IN (:genreIds)";
		}

		// if at least one language is in the list of filter languages
		if (filter.getLanguageIds() != null) {
			joiners += " JOIN film_languages fl ON f.id = fl.film_id";
			wheres += " AND fl.language_id IN (:languageIds)";
		}

		String queryString = selector + " FROM films f " + joiners + " " + wheres
				+ " GROUP BY c.name, f.year HAVING f.year IS NOT NULL ORDER BY c.name, f.year";

		Query query = em.get().createNativeQuery(queryString);

		if (whereLength.length() > 0) { // set the min & max length params
			query.setParameter("minLength", filter.getLengthStart());
			query.setParameter("maxLength", filter.getLengthEnd());
		}

		// if the name in the filter is set, set the param if
		if (filter.getName() != null) { // use % to also find incomplete words
			query.setParameter("findName", "%" + filter.getName().toLowerCase() + "%");
		}

		// if there are genres specified as a list of Integers if
		if (filter.getGenreIds() != null) {
			query.setParameter("genreIds", filter.getGenreIds());
		}

		// if there are languages specified as a list of Integers if
		if (filter.getLanguageIds() != null) {
			query.setParameter("languageIds", filter.getLanguageIds());
		}

		// fetch the result set
		List<Object[]> yearCounts = query.getResultList();

		// iterate through the entire resulting list
		int i = 0;
		while (i < yearCounts.size()) {
			int[] filmsPerYear = new int[currentYear - Country.YEAR_OFFSET + 1];

			// as long as the next element is the same as the current one, go on
			int j = 0;

			// always add the first row
			filmsPerYear[(int) yearCounts.get(i + j)[2] - Country.YEAR_OFFSET] = ((BigInteger) yearCounts.get(i + j)[3])
					.intValue();
			j++;

			// as long as the next element's id equals the first one, use the
			// same array
			while (i + j < yearCounts.size() && yearCounts.get(i + j - 1)[0] == yearCounts.get(i + j)[0]) {
				// TODO: make the max and min years dynamic, depending on real data
				if((int)yearCounts.get(i + j)[2] >= 1890 && (int)yearCounts.get(i + j)[2] <= 2015) {
					filmsPerYear[(int) yearCounts.get(i + j)[2]
							- Country.YEAR_OFFSET] = ((BigInteger) yearCounts.get(i + j)[3]).intValue();
					j++;
				}
			}

			// hydrate a new country and add it to the result list
			Country c = new Country((String) yearCounts.get(i)[1]);
			c.setNumberOfFilms(filmsPerYear);
			c.setId((int) yearCounts.get(i)[0]);
			countries.add(c);

			i = i + j;
		}

		// return the filled list of countries
		return countries;
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
		// create an empty list of genres
		List<Genre> genres = new ArrayList<Genre>();
		
		String wheres = "";
		String whereLength = "";

		if (filter.getLengthStart() > 0 || filter.getLengthEnd() < 600) {
			whereLength = "AND (f.length BETWEEN :minLength AND :maxLength) ";
			wheres += whereLength;
		}
		
		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += "AND LOWER(f.name) LIKE :findName ";
		}
		
		if(filter.getGenreIds() != null) {
			wheres += "AND g.id IN (:genreIds) ";
		}
		
		if(filter.getLanguageIds() != null) {
			wheres += "AND fl.language_id IN (:languageIds) ";
		}
		
		String queryString = 
				"SELECT sel.id, sel.name, COUNT(*) AS count "
				+ "FROM ( "
					+ "SELECT DISTINCT g.id AS id, g.name AS name, f.name AS film "
					+ "FROM films f "
						+ "JOIN film_genres fg ON f.id = fg.film_id "
						+ "JOIN genres g ON fg.genre_id = g.id "
						+ "JOIN film_countries fc ON f.id = fc.film_id "
						+ "JOIN film_languages fl ON f.id = fl.film_id "
					+ "WHERE fc.country_id = :countryId " 
					+ wheres
				+ ") AS sel "
				+ "GROUP BY sel.id "
				+ "ORDER BY count desc";
		
		Query query = em.get().createNativeQuery(queryString);
		
		query.setParameter("countryId", filter.getCountryIds().toArray()[0]);
		
		if (whereLength.length() > 0) { // set the min & max length params
			query.setParameter("minLength", filter.getLengthStart());
			query.setParameter("maxLength", filter.getLengthEnd());
		}

		// if the name in the filter is set, set the param
		if (filter.getName() != null) { // use % to also find incomplete words
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

		List<Object[]> rows = query.getResultList();
		
		for(Object[] row : rows) {
			genres.add(new Genre((int)row[0], (String)row[1], ((BigInteger)row[2]).intValue()));
		}
		
		// return the filled list of genres, ordered by count
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
	public HashMap<Integer, String> getGenreSelectOption() {
		if (cachedGenres != null) {
			return cachedGenres;
		}

		List<GenreDB> dbGenres = new ArrayList<GenreDB>();

		// select * from the genre table
		// TODO: query cache .setHint(QueryHints.CACHEABLE, true)
		dbGenres = em.get().createQuery("FROM GenreDB", GenreDB.class).getResultList();

		HashMap<Integer, String> availableGenres = new HashMap<Integer, String>(dbGenres.size());

		for (GenreDB genre : dbGenres) {
			availableGenres.put(genre.getId(), genre.getName());
		}

		cachedGenres = availableGenres;

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
	public HashMap<Integer, String> getCountrySelectOption() {
		if (cachedCountries != null) {
			return cachedCountries;
		}

		List<CountryDB> dbCountries = new ArrayList<CountryDB>();

		// select * from the country table
		// TODO: query cache .setHint(QueryHints.CACHEABLE, true)
		dbCountries = em.get().createQuery("FROM CountryDB", CountryDB.class).getResultList();

		HashMap<Integer, String> availableCountries = new HashMap<Integer, String>(dbCountries.size());

		for (CountryDB country : dbCountries) {
			availableCountries.put(country.getId(), country.getName());
		}

		cachedCountries = availableCountries;

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
	public HashMap<Integer, String> getLanguageSelectOption() {
		if (cachedLanguages != null) {
			return cachedLanguages;
		}

		List<LanguageDB> dbLanguages = new ArrayList<LanguageDB>();

		// select * from the language table
		// TODO: query cache .setHint(QueryHints.CACHEABLE, true)
		dbLanguages = em.get().createQuery("FROM LanguageDB", LanguageDB.class).getResultList();

		HashMap<Integer, String> availableLanguages = new HashMap<Integer, String>(dbLanguages.size());

		for (LanguageDB language : dbLanguages) {
			availableLanguages.put(language.getId(), language.getName());
		}

		cachedLanguages = availableLanguages;

		return availableLanguages;
	}
	
	//TODO RS comment,test...
	//TODO Ich han das eifach mal so für mich zum teste gmacht. Fallses ändere wettsch machsch eifach. -Nicolas
	@Override
	public FilterOptions getSelectOptions() {
		FilterOptions options = new FilterOptions();
		options.setCountrySelectOptions(getCountrySelectOption());
		options.setGenreSelectOptions(getGenreSelectOption());
		options.setLanguageSelectOptions(getLanguageSelectOption());
		return options;
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


}
