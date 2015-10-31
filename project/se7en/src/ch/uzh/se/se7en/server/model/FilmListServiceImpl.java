package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.ejb.QueryHints;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import ch.uzh.se.se7en.client.rpc.FilmListService;

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

		// create a query
		TypedQuery query = em.get()
				.createQuery("from FilmDB", FilmDB.class)
				// allow the query to cache it's results
				.setHint(QueryHints.HINT_CACHEABLE, Boolean.TRUE);
		
		// execute the query
		dbFilms = query.getResultList();

		// convert each FilmDB instance to a Film DataTransferObject
		for (FilmDB film : dbFilms) {
			Film f = film.toFilm();
			System.out.println(f);
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

		// create a query
		TypedQuery query = em.get()
				.createQuery("from CountryDB", CountryDB.class)
				// allow the query to cache it's results
				.setHint("org.hibernate.cacheable", Boolean.TRUE);
				
		// execute the query
		dbCountries = query.getResultList();

		// convert each CountryDB instance to a Country DataTransferObject
		for (CountryDB country : dbCountries) {
			Country c = country.toCountry();
			System.out.println(c);
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

		// create a query
		TypedQuery query = em.get()
				.createQuery("from GenreDB", GenreDB.class)
				// allow the query to cache it's results
				.setHint("org.hibernate.cacheable", Boolean.TRUE);
				
		// execute the query
		dbGenres = query.getResultList();

		// convert each GenreDB instance to a Genre DataTransferObject
		for (GenreDB genre : dbGenres) {
			Genre g = genre.toGenre();
			System.out.println(g);
			genres.add(g);
		}

		// return the filled list of countries
		return genres;
	}
}
