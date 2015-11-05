package ch.uzh.se.se7en.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
	@Transactional
	public List<Film> getFilmList(FilmFilter filter) {
		// create an empty list of movies
		List<FilmDB> dbFilms = new ArrayList<FilmDB>();
		List<Film> films = new ArrayList<Film>();

		// instantiate an entity manager
		EntityManager manager = em.get();

		// initialize the where string with the basic filters (that are always valid)
		String wheres = " WHERE f.length >= :minLength AND f.length <= :maxLength AND f.year >= :minYear AND f.year <= :maxYear";

		// if the name in the filter is set
		if (filter.getName() != null) {
			wheres += " AND f.name LIKE :findName";
		}

		// initialize an empty string for all the joins
		String joiners = "";

		// if at least one country is in the list of filter countries
		if (filter.getCountries() != null) {
			joiners += " JOIN f.filmCountryEntities fc";
			wheres += " AND fc.countryId IN :countryIds";
		}

		// if at least one genre is in the list of filter genres
		if (filter.getGenres() != null) {
			joiners += " JOIN f.filmGenreEntities fg";
			wheres += " AND fg.genreId IN :genreIds";
		}

		// if at least one language is in the list of filter languages
		if (filter.getLanguages() != null) {
			joiners += " JOIN f.filmLanguageEntities fl";
			wheres += " AND fl.languageId IN :languageIds";
		}

		// concat the query string
		String queryString = "SELECT DISTINCT f from FilmDB f" + joiners + wheres;

		// create a typed query from our criteria query
		TypedQuery<FilmDB> query = manager.createQuery(queryString, FilmDB.class);

		// set the min & max length params
		query.setParameter("minLength", filter.getLengthStart());
		query.setParameter("maxLength", filter.getLengthEnd());

		// set the min & max year params
		query.setParameter("minYear", filter.getYearStart());
		query.setParameter("maxYear", filter.getYearEnd());

		// if the name in the filter is set, set the param
		if (filter.getName() != null) {
			query.setParameter("findName", "%" + filter.getName() + "%");
		}

		if (filter.getCountries() != null) {
			// TODO: pass real filter list
			List<Integer> countryIds = new ArrayList<Integer>();
			countryIds.add(1);
			countryIds.add(2);
			countryIds.add(3);

			query.setParameter("countryIds", countryIds);
		}

		if (filter.getGenres() != null) {
			// TODO: pass real filter list
			List<Integer> countryIds = new ArrayList<Integer>();
			countryIds.add(1);
			countryIds.add(2);
			countryIds.add(3);

			query.setParameter("genreIds", countryIds);
		}

		if (filter.getLanguages() != null) {
			// TODO: pass real filter list
			List<Integer> countryIds = new ArrayList<Integer>();
			countryIds.add(1);
			countryIds.add(2);
			countryIds.add(3);

			query.setParameter("languageIds", countryIds);
		}

		// execute the query
		dbFilms = query.getResultList();

		// convert each FilmDB instance to a Film DataTransferObject
		for (FilmDB film : dbFilms) {
			Film f = film.toFilm();
			films.add(f);
		}

		// return the filled list of movies
		return films;
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
	@Transactional
	public List<Country> getCountryList(FilmFilter filter) {
		// create an empty list of countries
		List<CountryDB> dbCountries = new ArrayList<CountryDB>();
		List<Country> countries = new ArrayList<Country>();

		// select all countries from the database
		dbCountries = em.get().createQuery("from CountryDB", CountryDB.class).getResultList();

		// get the current year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		// convert each CountryDB instance to a Country DataTransferObject
		for (CountryDB country : dbCountries) {
			Country c = country.toCountry();

			// initialize a new array for all years
			int[] filmsPerYear = new int[currentYear - Country.YEAR_OFFSET + 1];

			// iterate through all the films of this country
			List<FilmCountryDB> filmCountries = country.getFilmCountryEntities();
			List<FilmDB> films = new ArrayList<FilmDB>();

			for (FilmCountryDB filmCountry : filmCountries) {
				films.add(filmCountry.getFilm());
			}

			for (FilmDB film : films) {
				// TODO: there have to be better ways to do this..
				if (film.getYear() != null && filter.getLengthStart() < film.getLength()
						&& filter.getLengthEnd() > film.getLength() && filter.getYearStart() < film.getYear()
						&& filter.getYearEnd() > film.getYear() && (filter.getName() == null
								|| film.getName().toLowerCase().contains(filter.getName().toLowerCase()))) {
					// increment the value at the year of the current film by 1
					filmsPerYear[film.getYear() - Country.YEAR_OFFSET + 1]++;
				}
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
