package ch.uzh.se.se7en.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.QueryHints;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.CountryYearCountDB;
import ch.uzh.se.se7en.server.model.CountryYearCountDBId;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
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
	FilmFilter cachedFilter;
	List<FilmDB> cachedFilms;
	HashMap<Integer, String> cachedCountries;
	HashMap<Integer, String> cachedGenres;
	HashMap<Integer, String> cachedLanguages;

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
		if(cachedFilter != null && cachedFilms != null && filter.equals(cachedFilter)) {
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
		String queryString = selector + joiners + " " + wheres + " ORDER BY " + ordering;

		// create a typed query from our query string
		TypedQuery<FilmDB> query = em.get().createQuery(queryString, FilmDB.class);

		// make the query cacheable if it is select *
		// TODO: query cache
		/* if(wheres == "WHERE 1=1") {
			query.setHint(QueryHints.CACHEABLE, true);
		} */
		
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
	@Override
	public List<Country> getCountryList(FilmFilter filter) {
		// create an empty list of countries
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		List<Country> countries = new ArrayList<Country>();

		List<FilmDB> dbFilms = new ArrayList<FilmDB>();
		dbFilms = getFilmEntitiesList(filter);
		
		Map<String, int[]> filmsPerYear = new HashMap<String, int[]>(250);
		
		// get the current year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				
		// iterate over each film
		for(FilmDB film : dbFilms) {
			
			// check if we have the necessary data to include the film in the calculation
			// TODO: allow 2016 as a year
			if(film.getYear() != null && film.getYear() <= 2015 && film.getCountryString() != null) {
				
				// for each country that is associated with this film
				for(FilmCountryDB filmCountry : film.getFilmCountryEntities()) {
					
					// calculate 
					if(filmsPerYear.containsKey(filmCountry.getCountryName())) {
						int[] calculationArray = filmsPerYear.get(filmCountry.getCountryName());
						
						calculationArray[filmCountry.getFilm().getYear() - Country.YEAR_OFFSET]++;
						
						filmsPerYear.put(filmCountry.getCountryName(), calculationArray);
					} else {
						// initialize a new array for all years
						int[] calculationArray = new int[currentYear - Country.YEAR_OFFSET + 1];
						
						calculationArray[filmCountry.getFilm().getYear() - Country.YEAR_OFFSET]++;
						
						filmsPerYear.put(filmCountry.getCountryName(), calculationArray);
					}
				}
			}
		}
		
		for(Entry<String, int[]> country : filmsPerYear.entrySet()) {
			Country c = new Country(country.getKey());
			c.setNumberOfFilms(country.getValue());
			countries.add(c);
		}
		
		// fetch the list of filtered country entities from the database
		/*dbCountries = getCountryEntitiesList(filter);

		// get the current year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		// convert each CountryDB instance to a Country DataTransferObject
		for (CountryDB country : dbCountries) {
			Country c = country.toCountry();
			
			// initialize a new array for all years
			int[] filmsPerYear = new int[currentYear - Country.YEAR_OFFSET + 1];

			// for each film entity, increment it's year in the array
			for (FilmCountryDB filmCountry : country.getFilmCountryEntities()) {
				if (filmCountry.getFilm().getYear() != null) {
					filmsPerYear[filmCountry.getFilm().getYear() - Country.YEAR_OFFSET]++;
				}
			}

			// start the number of films array transformation
			c.setNumberOfFilms(filmsPerYear);

			// add the country to the result list
			countries.add(c);
		}*/

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
	public List<CountryDB> getCountryEntitiesList(FilmFilter filter) {
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		// initialize the selector in the query
		String selector = "SELECT DISTINCT c FROM CountryDB c";

		// initialize the where string with the basic filters
		String wheres = "";
		String whereLength = "";
		String whereYear = "";

		if (filter.getLengthStart() > 0 || filter.getLengthEnd() < 600) {
			whereLength = "(f.length BETWEEN :minLength AND :maxLength)";
			wheres += whereLength;
		}

		if (filter.getYearStart() > 1890 || filter.getYearEnd() < 2015) {
			if (wheres.length() > 0) {
				wheres += " AND ";
			}

			whereYear = "(f.year BETWEEN :minYear AND :maxYear)";
			wheres += whereYear;
		}

		if (wheres.length() > 0) {
			wheres = "WHERE " + wheres;
		} else {
			wheres = "WHERE 1=1";
		}

		// initialize an empty string for all the joins
		String joiners = "JOIN FETCH c.filmCountryEntities fc JOIN FETCH fc.primaryKey.film fi";

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
		String queryString = selector + " " + joiners + " " + wheres + " ORDER BY c.name";

		// select all countries from the database
		TypedQuery<CountryDB> query = em.get().createQuery(queryString, CountryDB.class);

		if (whereLength.length() > 0) {
			// set the min & max length params
			query.setParameter("minLength", filter.getLengthStart());
			query.setParameter("maxLength", filter.getLengthEnd());
		}

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
		
		//Query yearCounts = em.get().createNativeQuery("SELECT c.name AS name, f.year AS year, COUNT(*) AS count FROM countries c JOIN film_countries fc ON c.id = fc.country_id JOIN films f ON fc.film_id = f.id GROUP BY c.name, f.year", CountryYearCountDB.class); 
		//em.get().createQuery("SELECT CountryYearCountDB FROM CountryDB c JOIN c.filmCountryEntities fc JOIN fc.primaryKey.film f GROUP BY c.name, f.year", CountryYearCountDB.class);
		
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
		// create an empty list of genres
		List<GenreDB> dbGenres = new ArrayList<GenreDB>();
		List<Genre> genres = new ArrayList<Genre>();

		// TODO RS Sprint 2

		// return the filled list of genres

		// DEMO Code Start
		genres.add(new Genre(1, "Action", 10));
		genres.add(new Genre(2, "Adventure", 5));
		genres.add(new Genre(3, "Comedy", 20));
		// DEMO Code Start
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
	public HashMap<Integer, String> getGenreSelectOption() {
		if(cachedGenres != null) {
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
	@Override
	public HashMap<Integer, String> getCountrySelectOption() {
		if(cachedCountries != null) {
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
	@Override
	public HashMap<Integer, String> getLanguageSelectOption() {
		if(cachedLanguages != null) {
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
