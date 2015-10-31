package ch.uzh.se.se7en.server.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Restrictions;

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
		
		System.out.println(filter);

		// instantiate an entity manager
		EntityManager manager = em.get();
		
		// instantiate a criteria builder for dynamic queries
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		
		// create a new query
		CriteriaQuery<FilmDB> q = cb.createQuery(FilmDB.class);
		Root<FilmDB> filmRoot = q.from(FilmDB.class);
		
		// define the parameters
		ParameterExpression<Integer> minLength = cb.parameter(Integer.class);
		ParameterExpression<Integer> maxLength = cb.parameter(Integer.class);
		ParameterExpression<Integer> minYear = cb.parameter(Integer.class);
		ParameterExpression<Integer> maxYear = cb.parameter(Integer.class);
		ParameterExpression<String> findName = cb.parameter(String.class);
		Path<Integer> length = filmRoot.get("length");
		Path<Integer> year = filmRoot.get("year");
		Path<String> name = filmRoot.get("name");
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		
		// add length and year (always valid numbers)
		criteria.add(cb.between(length, minLength, maxLength));
		criteria.add(cb.between(year, minYear, maxYear));
		
		// if the name in the filter is set
		if(filter.getName() != null) {
			criteria.add(cb.like(name, findName));
		}
		
		// build the query
		q.select(filmRoot).where(
				criteria.toArray(new Predicate[criteria.size()])
		);
		
		// create a typed query from our criteria query
		TypedQuery<FilmDB> query = manager.createQuery(q);
		
		// set the min & max length params
		query.setParameter(minLength, filter.getLengthStart());
		query.setParameter(maxLength, filter.getLengthEnd());
		
		// set the min & max year params
		query.setParameter(minYear, filter.getYearStart());
		query.setParameter(maxYear, filter.getYearEnd());
		
		if(filter.getName() != null) {
			query.setParameter(findName, "%" + filter.getName() + "%");
		}
		
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

		// select all countries from the database
		dbCountries = em.get().createQuery("from CountryDB", CountryDB.class).getResultList();

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

		// select all countries from the database
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
}
