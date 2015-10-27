package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.utils.SystemProperty;

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
	// create the entity manager factory to spawn entityManagers later on
	private static EntityManagerFactory entityManagerFactory = createFactory();

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
	
	private static EntityManagerFactory createFactory() {
		Map<String, String> properties = new HashMap<String, String>();
		
		// set the properties of the db connection depending on production/development environment
		if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:mysql://se-team-se7en:db/se7en");
			properties.put("javax.persistence.jdbc.user", "root");
			properties.put("javax.persistence.jdbc.password", "");
		} else {
			properties.put("javax.persistence.jdbc.driver", "com.google.appengine.api.rdbms.AppEngineDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:rdbms://173.194.250.0/se7en");
			properties.put("javax.persistence.jdbc.user", "se7en");
			properties.put("javax.persistence.jdbc.password", "k1vttuIYXqOPe5!");
		}
		
		// return a new entity manager factory
		return Persistence.createEntityManagerFactory("ch.uzh.se.se7en.hibernate", properties);
	}

}
