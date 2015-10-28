package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.utils.SystemProperty;

import ch.uzh.se.se7en.server.ServerUtil;

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
public class FilmListServiceImpl extends RemoteServiceServlet implements FilmListService {
	// initialize the entity manager factory
	private EntityManagerFactory entityManagerFactory = ServerUtil.createFactory();

	/**
	 * Returns a list of films to the client
	 */
	@Override
	public List<Film> getFilmList(FilmFilter filter) {

		// create an empty list of movies
		List<Film> movies = new ArrayList<Film>();

		// create an entity manager
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// start a new transaction with the database
		entityManager.getTransaction().begin();

		// select all movies from the database
		movies = entityManager.createQuery("from Film", Film.class).getResultList();

		// commit the transaction and close the entity manager
		entityManager.getTransaction().commit();
		entityManager.close();

		// return the filled list of movies
		return movies;
	}

	/**
	 * Returns a list of countries to the client
	 */
	@Override
	public List<Country> getCountryList(FilmFilter filter) {
	
		// create an empty list of countries
		List<Country> countries = new ArrayList<Country>();

		// create an entity manager
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// start a new transaction with the database
		entityManager.getTransaction().begin();

		// select all countries from the database
		countries = entityManager.createQuery("from Country", Country.class).getResultList();

		// commit the transaction and close the entity manager
		entityManager.getTransaction().commit();
		entityManager.close();

		// return the filled list of countries
		return countries;
	
	}

	/**
	 * Returns a list of genres to the client
	 */
	@Override
	public List<Genre> getGenreList(FilmFilter filter) {
		// create an empty list of genres
		List<Genre> genres = new ArrayList<Genre>();

		// create an entity manager
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// start a new transaction with the database
		entityManager.getTransaction().begin();

		// select all genres from the database
		genres = entityManager.createQuery("from Genre", Genre.class).getResultList();

		// commit the transaction and close the entity manager
		entityManager.getTransaction().commit();
		entityManager.close();

		// return the filled list of genres
		return genres;
	}
}
