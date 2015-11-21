package ch.uzh.se.se7en.junit.server;

import static ch.uzh.se.se7en.junit.server.TestUtil.mockQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.uzh.se.se7en.server.FilmListServiceImpl;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

/**
 * Tests for the answering of rpc calls
 * 
 * @author Roland Schläfli
 */
@RunWith(JukitoRunner.class)
public class FilmListServiceTest {

	@Inject
	FilmListServiceImpl rpcService;

	@Inject
	EntityManager manager;

	Provider<EntityManager> em;

	// create fake lists/sets of all the entities in the database
	List<FilmDB> dbFilms = new ArrayList<FilmDB>();
	List<CountryDB> dbCountries = new ArrayList<CountryDB>();
	List<GenreDB> dbGenres = new ArrayList<GenreDB>();
	List<LanguageDB> dbLanguages = new ArrayList<LanguageDB>();
	Set<FilmCountryDB> dbFilmCountries = new HashSet<FilmCountryDB>();
	Set<FilmGenreDB> dbFilmGenres = new HashSet<FilmGenreDB>();
	Set<FilmLanguageDB> dbFilmLanguages = new HashSet<FilmLanguageDB>();

	// create fake queries
	TypedQuery<FilmDB> filmQuery = mockQuery(dbFilms);
	TypedQuery<CountryDB> countryQuery = mockQuery(dbCountries);
	TypedQuery<GenreDB> genreQuery = mockQuery(dbGenres);
	TypedQuery<LanguageDB> languageQuery = mockQuery(dbLanguages);

	// setup the fake data for testing
	@Before
	public void setup() {
		// need to do this manually for mocking a generic
		em = (Provider<EntityManager>) mock(Provider.class);

		// add one fake film to the mocked database
		dbCountries.add(new CountryDB("Switzerland"));
		dbGenres.add(new GenreDB("Horror"));
		dbLanguages.add(new LanguageDB("German"));

		FilmDB fakeFilm = new FilmDB("Film 1", 90, 2015);

		dbFilmCountries.add(new FilmCountryDB(fakeFilm, dbCountries.get(0)));
		dbFilmGenres.add(new FilmGenreDB(fakeFilm, dbGenres.get(0)));
		dbFilmLanguages.add(new FilmLanguageDB(fakeFilm, dbLanguages.get(0)));

		fakeFilm.setFilmCountryEntities(dbFilmCountries);
		fakeFilm.setFilmGenreEntities(dbFilmGenres);
		fakeFilm.setFilmLanguageEntities(dbFilmLanguages);
		dbFilms.add(fakeFilm);

		// the mocked queries should return the fake results
		doReturn(filmQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));
		doReturn(countryQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(CountryDB.class));
		doReturn(genreQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(GenreDB.class));
		doReturn(languageQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(LanguageDB.class));

		// mock the call get() on the provider
		doReturn(manager).when(em).get();

		// inject the mock into the class
		rpcService.setEm(em);
	}

	@Test
	public void testGetFilmList() {
		/* INITIALIZATION BLOCK */
		// filter with the default filters
		FilmFilter filter = new FilmFilter();

		/* EXECUTION BLOCK */
		List<Film> films = rpcService.getFilmList(filter);
		Film film = films.get(0);

		/* VERIFICATION BLOCK */
		// assert that all the information in the film is correctly set
		assertEquals(films.size(), 1);
		assertEquals(film.getName(), "Film 1");
		assertEquals(film.getLength(), new Integer(90));
		assertEquals(film.getYear(), new Integer(2015));
		assertEquals(film.getCountries().get(0), "Switzerland");
		assertEquals(film.getGenres().get(0), "Horror");
		assertEquals(film.getLanguages().get(0), "German");
	}

	@Test
	public void testGetFilmEntitiesListFilterDefault() {
		/* INITIALIZATION BLOCK */
		// filter with the default filters
		FilmFilter filter = new FilmFilter();

		/* EXECUTION BLOCK */
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f WHERE 1=1 ORDER BY f.name",
				FilmDB.class);

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
	}

	@Test
	public void testGetFilmEntitiesListFilterByNameAndCountryIds() {
		/* INITIALIZATION BLOCK */
		// set the filters
		FilmFilter filter = new FilmFilter("Hallo");
		Set<Integer> countryIds = new HashSet<Integer>();
		countryIds.add(1);
		countryIds.add(99);
		filter.setCountryIds(countryIds);

		/* EXECUTION BLOCK */
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f JOIN f.filmCountryEntities fc WHERE 1=1 AND LOWER(f.name) LIKE :findName AND fc.countryId IN :countryIds ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(filmQuery, times(1)).setParameter("findName", "%hallo%");
		verify(filmQuery, times(1)).setParameter("countryIds", filter.getCountryIds());

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
	}

	@Test
	public void testGetFilmEntitiesListFilterByEverything() {
		/* INITIALIZATION BLOCK */
		// set the filters
		FilmFilter filter = new FilmFilter();
		Set<Integer> ids = new HashSet<Integer>();
		ids.add(1);
		ids.add(99);
		filter.setName("Hallo");
		filter.setLengthStart(11);
		filter.setLengthEnd(22);
		filter.setYearStart(2013);
		filter.setYearEnd(2015);
		filter.setCountryIds(ids);
		filter.setGenreIds(ids);
		filter.setLanguageIds(ids);

		/* EXECUTION BLOCK */
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f JOIN f.filmCountryEntities fc JOIN f.filmGenreEntities fg JOIN f.filmLanguageEntities fl WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear) AND LOWER(f.name) LIKE :findName AND fc.countryId IN :countryIds AND fg.genreId IN :genreIds AND fl.languageId IN :languageIds ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(filmQuery, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(filmQuery, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(filmQuery, times(1)).setParameter("minYear", filter.getYearStart());
		verify(filmQuery, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(filmQuery, times(1)).setParameter("findName", "%hallo%");
		verify(filmQuery, times(1)).setParameter("countryIds", filter.getCountryIds());
		verify(filmQuery, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(filmQuery, times(1)).setParameter("languageIds", filter.getLanguageIds());

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
	}

	@Test
	public void testGetCountryList() {
		/* INITIALIZATION BLOCK */
		// filter with the default filters
		FilmFilter filter = new FilmFilter();
		int[] numOfFilms = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

		/* EXECUTION BLOCK */
		List<Country> countries = rpcService.getCountryList(filter);
		Country country = countries.get(0);

		/* VERIFICATION BLOCK */
		assertEquals(country.getName(), "Switzerland");

		// assert that the number of films array was correctly generated
		assertTrue(Arrays.equals(country.getNumberOfFilms(), numOfFilms));
	}

	@Test
	public void testGetCountryEntitiesListFilteredDefault() {
		/* INITIALIZATION BLOCK */
		// filter with the default filters
		FilmFilter filter = new FilmFilter();

		/* EXECUTION BLOCK */
		List<CountryDB> countries = rpcService.getCountryEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT c FROM CountryDB c JOIN c.filmCountryEntities fc JOIN fc.primaryKey.film fi WHERE (fi.length BETWEEN :minLength AND :maxLength) AND (fi.year BETWEEN :minYear AND :maxYear) ORDER BY c.name",
				CountryDB.class);

		// verify that all the parameters are correctly set
		verify(countryQuery, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(countryQuery, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(countryQuery, times(1)).setParameter("minYear", filter.getYearStart());
		verify(countryQuery, times(1)).setParameter("maxYear", filter.getYearEnd());

		// verify that the query is then executed
		verify(countryQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryQuery);
	}

	@Test
	public void testGetCountryEntitiesListFilteredByName() {
		/* INITIALIZATION BLOCK */
		// filter by name only
		FilmFilter filter = new FilmFilter("Hallo");

		/* EXECUTION BLOCK */
		List<CountryDB> countries = rpcService.getCountryEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT c FROM CountryDB c JOIN c.filmCountryEntities fc JOIN fc.primaryKey.film fi WHERE (fi.length BETWEEN :minLength AND :maxLength) AND (fi.year BETWEEN :minYear AND :maxYear) AND LOWER(fi.name) LIKE :findName ORDER BY c.name",
				CountryDB.class);

		// verify that all the parameters are correctly set
		verify(countryQuery, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(countryQuery, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(countryQuery, times(1)).setParameter("minYear", filter.getYearStart());
		verify(countryQuery, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(countryQuery, times(1)).setParameter("findName", "%hallo%");

		// verify that the query is then executed
		verify(countryQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryQuery);
	}

	@Test
	public void testGetCountryEntitiesListFilteredByEverything() {
		/* INITIALIZATION BLOCK */
		// set the filters
		FilmFilter filter = new FilmFilter();
		Set<Integer> ids = new HashSet<Integer>();
		ids.add(1);
		ids.add(99);
		filter.setName("Hallo");
		filter.setLengthStart(11);
		filter.setLengthEnd(22);
		filter.setYearStart(2013);
		filter.setYearEnd(2015);
		filter.setCountryIds(ids); // should not have any effect
		filter.setGenreIds(ids);
		filter.setLanguageIds(ids);

		/* EXECUTION BLOCK */
		List<CountryDB> countries = rpcService.getCountryEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT c FROM CountryDB c JOIN c.filmCountryEntities fc JOIN fc.primaryKey.film fi JOIN fi.filmGenreEntities fg JOIN fi.filmLanguageEntities fl WHERE (fi.length BETWEEN :minLength AND :maxLength) AND (fi.year BETWEEN :minYear AND :maxYear) AND LOWER(fi.name) LIKE :findName AND fg.genreId IN :genreIds AND fl.languageId IN :languageIds ORDER BY c.name",
				CountryDB.class);

		// verify that all the parameters are correctly set
		verify(countryQuery, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(countryQuery, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(countryQuery, times(1)).setParameter("minYear", filter.getYearStart());
		verify(countryQuery, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(countryQuery, times(1)).setParameter("findName", "%hallo%");
		verify(countryQuery, times(1)).setParameter("genreIds", ids);
		verify(countryQuery, times(1)).setParameter("languageIds", ids);

		// verify that the query is then executed
		verify(countryQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryQuery);
	}

	@Test
	public void testGetGenreList() {
		// TODO RS Sprint 2
	}

	@Test
	public void testGetGenreSelectOption() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */
		HashMap<Integer, String> genreSelects = rpcService.getGenreSelectOption();

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery("FROM GenreDB ORDER BY name", GenreDB.class);

		// verify that the query is then executed
		verify(genreQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(genreQuery);

		// assert that the HashMap entries were properly created
		assertEquals(genreSelects.get(0), "Horror");
	}

	@Test
	public void testGetCountrySelectOption() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */
		HashMap<Integer, String> countrySelects = rpcService.getCountrySelectOption();

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery("FROM CountryDB ORDER BY name", CountryDB.class);

		// verify that the query is then executed
		verify(countryQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryQuery);

		// assert that the HashMap entries were properly created
		assertEquals(countrySelects.get(0), "Switzerland");
	}

	@Test
	public void testGetLanguageSelectOption() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */
		HashMap<Integer, String> languageSelects = rpcService.getLanguageSelectOption();

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery("FROM LanguageDB ORDER BY name", LanguageDB.class);

		// verify that the query is then executed
		verify(languageQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(languageQuery);

		// assert that the HashMap entries were properly created
		assertEquals(languageSelects.get(0), "German");
	}
}
