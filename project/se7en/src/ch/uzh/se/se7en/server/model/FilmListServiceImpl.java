package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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
	 */
	@Override
	@Transactional
	public List<Film> getFilmList(FilmFilter filter) {
		// create an empty list of movies
		List<Film> movies = new ArrayList<Film>();

		// select all movies from the database
		movies = em.get().createQuery("from Film", Film.class).getResultList();

		// return the filled list of movies
		return movies;
	}

	/**
	 * Returns a filtered list of countries to the client
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param FilmFilter
	 *            filter A filter object
	 */
	@Override
	@Transactional
	public List<Country> getCountryList(FilmFilter filter) {
		// create an empty list of countries
		List<Country> countries = new ArrayList<Country>();

		// select all countries from the database
		countries = em.get().createQuery("from Country", Country.class).getResultList();

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
	 */
	@Override
	@Transactional
	public List<Genre> getGenreList(FilmFilter filter) {
		// create an empty list of genres
		List<Genre> genres = new ArrayList<Genre>();

		// select all genres from the database
		genres = em.get().createQuery("from Genre", Genre.class).getResultList();

		// return the filled list of genres
		return genres;
	}
}
