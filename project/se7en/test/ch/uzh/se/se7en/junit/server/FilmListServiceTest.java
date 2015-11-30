package ch.uzh.se.se7en.junit.server;

import static ch.uzh.se.se7en.junit.server.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import ch.uzh.se.se7en.shared.model.Genre;

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
	Query totalResultCount = mockSingleResultQuery((long) 3);
	Query countryYearCount;
	Query countryGenresQuery;

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
		fakeFilm.setCountryString("Switzerland");
		fakeFilm.setGenreString("Horror");
		fakeFilm.setLanguageString("German");
		dbFilms.add(fakeFilm);
		
		// the mocked queries should return the fake results
		doReturn(filmQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));
		doReturn(totalResultCount).when(manager).createQuery(Matchers.anyString());
		doReturn(countryQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(CountryDB.class));
		doReturn(genreQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(GenreDB.class));
		doReturn(languageQuery).when(manager).createQuery(Matchers.anyString(), Matchers.same(LanguageDB.class));

		// setup the native query mock for getCountryList
		List<Object[]> nativeResult = new ArrayList<Object[]>();
		Object[] firstRow = { 1, "Germany", 1890, BigInteger.valueOf(25) };
		Object[] secondRow = { 2, "Singapur", 2011, BigInteger.valueOf(33) };
		Object[] thirdRow = { 2, "Singapur", 2015, BigInteger.valueOf(45) };
		Object[] fourthRow = { 3, "Italy", 1960, BigInteger.valueOf(20) };
		nativeResult.add(firstRow);
		nativeResult.add(secondRow);
		nativeResult.add(thirdRow);
		nativeResult.add(fourthRow);
		countryYearCount = mockUntypedQuery(nativeResult);

		// setup the native query mock for getGenreList
		List<Object[]> nativeResult2 = new ArrayList<Object[]>();
		Object[] firstRow2 = { 1, "Horror", BigInteger.valueOf(55) };
		Object[] secondRow2 = { 2, "Adult", BigInteger.valueOf(46) };
		Object[] thirdRow2 = { 3, "Romance", BigInteger.valueOf(33) };
		Object[] fourthRow2 = { 4, "Action", BigInteger.valueOf(20) };
		nativeResult2.add(firstRow2);
		nativeResult2.add(secondRow2);
		nativeResult2.add(thirdRow2);
		nativeResult2.add(fourthRow2);
		countryGenresQuery = mockUntypedQuery(nativeResult2);

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
		List<Film> films = rpcService.getFilmList(filter, 0, 50);
		Film pseudoFilm = films.get(0);
		Film film = films.get(1);

		/* VERIFICATION BLOCK */
		assertEquals(films.size(), 2);
		
		// assert that the query count pseudo film was correctly set
		assertEquals(pseudoFilm.getName(), "GIR_QUERY_COUNT");
		assertEquals(pseudoFilm.getLength(), new Integer(3));
		
		// assert that all the information in the film is correctly set
		assertEquals(film.getName(), "Film 1");
		assertEquals(film.getLength(), new Integer(90));
		assertEquals(film.getYear(), new Integer(2015));
		// TODO: update such that we only need to return the string
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
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter, 0, 50);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1))
				.createQuery("SELECT DISTINCT f FROM FilmDB f WHERE 1=1 ORDER BY f.name", FilmDB.class);
		verify(manager, times(1))
				.createQuery("SELECT COUNT(DISTINCT f.id) FROM FilmDB f WHERE 1=1");

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();
		verify(totalResultCount, times(1)).getSingleResult();

		// verify that min/max results are correctly set
		verify(filmQuery, times(1)).setFirstResult(0);
		verify(filmQuery, times(1)).setMaxResults(50);

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
		verifyNoMoreInteractions(totalResultCount);
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
		filter.setOrderBy("f.year desc");

		/* EXECUTION BLOCK */
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter, 0, 50);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
			"SELECT DISTINCT f " 
			+ "FROM FilmDB f "
				+ "LEFT JOIN FETCH f.filmCountryEntities fc " 
			+ "WHERE 1=1 " 
				+ "AND LOWER(f.name) LIKE :findName "
				+ "AND fc.countryId IN (:countryIds) " 
			+ "ORDER BY f.year desc", 
			FilmDB.class
		);
		verify(manager, times(1)).createQuery(
			"SELECT COUNT(DISTINCT f.id) " 
			+ "FROM FilmDB f "
				+ "LEFT JOIN f.filmCountryEntities fc " 
			+ "WHERE 1=1 " 
				+ "AND LOWER(f.name) LIKE :findName "
				+ "AND fc.countryId IN (:countryIds)"
		);

		// verify that all the parameters are correctly set
		verify(filmQuery, times(1)).setParameter("findName", "%hallo%");
		verify(filmQuery, times(1)).setParameter("countryIds", filter.getCountryIds());
		verify(totalResultCount, times(1)).setParameter("findName", "%hallo%");
		verify(totalResultCount, times(1)).setParameter("countryIds", filter.getCountryIds());

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();
		verify(totalResultCount, times(1)).getSingleResult();

		// verify that min/max results are correctly set
		verify(filmQuery, times(1)).setFirstResult(0);
		verify(filmQuery, times(1)).setMaxResults(50);

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
		verifyNoMoreInteractions(totalResultCount);
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
		filter.setOrderBy("f.name asc");

		/* EXECUTION BLOCK */
		List<FilmDB> films = rpcService.getFilmEntitiesList(filter, 0, 50);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
			"SELECT DISTINCT f " 
			+ "FROM FilmDB f "
				+ "LEFT JOIN FETCH f.filmCountryEntities fc " 
				+ "LEFT JOIN f.filmGenreEntities fg "
				+ "LEFT JOIN f.filmLanguageEntities fl " 
			+ "WHERE (f.length BETWEEN :minLength AND :maxLength) "
				+ "AND (f.year BETWEEN :minYear AND :maxYear) " 
				+ "AND LOWER(f.name) LIKE :findName "
				+ "AND fc.countryId IN (:countryIds) " 
				+ "AND fg.genreId IN (:genreIds) "
				+ "AND fl.languageId IN (:languageIds) " 
			+ "ORDER BY f.name asc", 
			FilmDB.class
		);
		verify(manager, times(1)).createQuery(
			"SELECT COUNT(DISTINCT f.id) " 
			+ "FROM FilmDB f "
				+ "LEFT JOIN f.filmCountryEntities fc " 
				+ "LEFT JOIN f.filmGenreEntities fg "
				+ "LEFT JOIN f.filmLanguageEntities fl " 
			+ "WHERE (f.length BETWEEN :minLength AND :maxLength) "
				+ "AND (f.year BETWEEN :minYear AND :maxYear) " 
				+ "AND LOWER(f.name) LIKE :findName "
				+ "AND fc.countryId IN (:countryIds) " 
				+ "AND fg.genreId IN (:genreIds) "
				+ "AND fl.languageId IN (:languageIds)"
		);

		// verify that all the parameters are correctly set
		verify(filmQuery, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(filmQuery, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(filmQuery, times(1)).setParameter("minYear", filter.getYearStart());
		verify(filmQuery, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(filmQuery, times(1)).setParameter("findName", "%hallo%");
		verify(filmQuery, times(1)).setParameter("countryIds", filter.getCountryIds());
		verify(filmQuery, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(filmQuery, times(1)).setParameter("languageIds", filter.getLanguageIds());
		verify(totalResultCount, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(totalResultCount, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(totalResultCount, times(1)).setParameter("minYear", filter.getYearStart());
		verify(totalResultCount, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(totalResultCount, times(1)).setParameter("findName", "%hallo%");
		verify(totalResultCount, times(1)).setParameter("countryIds", filter.getCountryIds());
		verify(totalResultCount, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(totalResultCount, times(1)).setParameter("languageIds", filter.getLanguageIds());

		// verify that the query is then executed
		verify(filmQuery, times(1)).getResultList();
		verify(totalResultCount, times(1)).getSingleResult();

		// verify that min/max results are correctly set
		verify(filmQuery, times(1)).setFirstResult(0);
		verify(filmQuery, times(1)).setMaxResults(50);

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(filmQuery);
		verifyNoMoreInteractions(totalResultCount);
	}

	@Test
	public void testGetCountryListFilterDefault() {
		/* INITIALIZATION BLOCK */
		doReturn(countryYearCount).when(manager).createNativeQuery(Matchers.anyString());

		// filter with the default filters
		FilmFilter filter = new FilmFilter();

		/* EXECUTION BLOCK */
		List<Country> countries = rpcService.getCountryList(filter);

		/* VERIFICATION BLOCK */
		verify(manager, times(1)).createNativeQuery(
			"SELECT DISTINCT c.id, c.name, f.year, COUNT(*) " 
			+ "FROM films f "
				+ "LEFT JOIN film_countries fc ON f.id = fc.film_id "
				+ "JOIN countries c ON fc.country_id = c.id " 
			+ "WHERE (f.year BETWEEN :minYear AND :maxYear) "
			+ "GROUP BY c.name, f.year "
			+ "HAVING f.year IS NOT NULL "
			+ "ORDER BY c.name, f.year"
		);
		
		verify(countryYearCount, times(1)).setParameter("minYear", 1890);
		verify(countryYearCount, times(1)).setParameter("maxYear", 2015);

		// assert that all 3 countries are present
		assertEquals(countries.size(), 3);

		// assert that the correct countries are present
		assertEquals(countries.get(0).getName(), "Germany");
		assertEquals(countries.get(1).getName(), "Singapur");
		assertEquals(countries.get(2).getName(), "Italy");

		// assert that the countries contain the correct array
		// TODO NK check if those are correct :)
		int[] countGermany = { 0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25 };
		int[] countSingapur = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 33, 33, 33, 78 };
		int[] countItaly = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20 };

		assertTrue(Arrays.equals(countries.get(0).getNumberOfFilms(), countGermany));
		assertTrue(Arrays.equals(countries.get(1).getNumberOfFilms(), countSingapur));
		assertTrue(Arrays.equals(countries.get(2).getNumberOfFilms(), countItaly));
	}

	@Test
	public void testGetCountryListFilterByEverything() {
		/* INITIALIZATION BLOCK */
		doReturn(countryYearCount).when(manager).createNativeQuery(Matchers.anyString());

		// filter with the default filters
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
		List<Country> countries = rpcService.getCountryList(filter);

		/* VERIFICATION BLOCK */
		verify(manager, times(1)).createNativeQuery(
			"SELECT DISTINCT c.id, c.name, f.year, COUNT(*) " 
			+ "FROM films f "
				+ "LEFT JOIN film_countries fc ON f.id = fc.film_id " 
				+ "JOIN countries c ON fc.country_id = c.id "
				+ "LEFT JOIN film_genres fg ON f.id = fg.film_id " 
				+ "LEFT JOIN film_languages fl ON f.id = fl.film_id "
			+ "WHERE (f.year BETWEEN :minYear AND :maxYear) AND (f.length BETWEEN :minLength AND :maxLength) " 
				+ "AND LOWER(f.name) LIKE :findName "
				+ "AND fg.genre_id IN (:genreIds) " 
				+ "AND fl.language_id IN (:languageIds) "
			+ "GROUP BY c.name, f.year " 
			+ "HAVING f.year IS NOT NULL " 
			+ "ORDER BY c.name, f.year"
		);

		// verify that all the parameters are correctly set (country was not filtered by!)
		verify(countryYearCount, times(1)).setParameter("minYear", 1890);
		verify(countryYearCount, times(1)).setParameter("maxYear", 2015);
		verify(countryYearCount, times(1)).setParameter("minLength", 11);
		verify(countryYearCount, times(1)).setParameter("maxLength", 22);
		verify(countryYearCount, times(1)).setParameter("findName", "%hallo%");
		verify(countryYearCount, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(countryYearCount, times(1)).setParameter("languageIds", filter.getLanguageIds());

		// verify that the query is then executed
		verify(countryYearCount, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryYearCount);

		// assert that all 3 countries are present
		assertEquals(countries.size(), 3);

		// assert that the correct countries are present
		assertEquals(countries.get(0).getName(), "Germany");
		assertEquals(countries.get(1).getName(), "Singapur");
		assertEquals(countries.get(2).getName(), "Italy");

		// assert that the countries contain the correct array
		// TODO NK check if those are correct :)
		int[] countGermany = { 0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
				25 };
		int[] countSingapur = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 33, 33, 33, 78 };
		int[] countItaly = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20 };

		assertTrue(Arrays.equals(countries.get(0).getNumberOfFilms(), countGermany));
		assertTrue(Arrays.equals(countries.get(1).getNumberOfFilms(), countSingapur));
		assertTrue(Arrays.equals(countries.get(2).getNumberOfFilms(), countItaly));
	}

	@Test
	public void testGetGenreListFilterDefault() {
		/* INITIALIZATION BLOCK */
		doReturn(countryGenresQuery).when(manager).createNativeQuery(Matchers.anyString());

		// filter with the default filters and country id 99
		FilmFilter filter = new FilmFilter();
		Set<Integer> countryIds = new HashSet<Integer>();
		countryIds.add(99);
		filter.setCountryIds(countryIds);

		/* EXECUTION BLOCK */
		List<Genre> genres = rpcService.getGenreList(filter);

		/* VERIFICATION BLOCK */
		verify(manager, times(1)).createNativeQuery(
			"SELECT sel.id, sel.name, COUNT(*) AS count " 
			+ "FROM ( "
				+ "SELECT DISTINCT g.id AS id, g.name AS name, f.id AS film " 
				+ "FROM films f "
					+ "LEFT JOIN film_genres fg ON f.id = fg.film_id " 
					+ "JOIN genres g ON fg.genre_id = g.id "
					+ "LEFT JOIN film_countries fc ON f.id = fc.film_id " 
					+ "LEFT JOIN film_languages fl ON f.id = fl.film_id "
				+ "WHERE fc.country_id = :countryId "
					+ "AND f.year IS NOT NULL " 
			+ ") AS sel " 
			+ "GROUP BY sel.id " 
			+ "ORDER BY count desc"
		);

		// verify that the correct country id is taken
		verify(countryGenresQuery, times(1)).setParameter("countryId", 99);

		// verify that the query is then executed
		verify(countryGenresQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryGenresQuery);

		// assert that all 4 genres are present
		assertEquals(genres.size(), 4);

		// assert that the correct genres are present in the right order
		assertEquals(genres.get(0).getName(), "Horror");
		assertEquals(genres.get(1).getName(), "Adult");
		assertEquals(genres.get(2).getName(), "Romance");
		assertEquals(genres.get(3).getName(), "Action");

		// assert that the genres contain the right counts
		assertEquals(genres.get(0).getNumberOfFilms(), 55);
		assertEquals(genres.get(1).getNumberOfFilms(), 46);
		assertEquals(genres.get(2).getNumberOfFilms(), 33);
		assertEquals(genres.get(3).getNumberOfFilms(), 20);
	}

	@Test
	public void testGetGenreListFilterByEverything() {
		/* INITIALIZATION BLOCK */
		doReturn(countryGenresQuery).when(manager).createNativeQuery(Matchers.anyString());

		// filter with the default filters and country id 99
		FilmFilter filter = new FilmFilter();
		Set<Integer> countryIds = new HashSet<Integer>();
		countryIds.add(99);
		filter.setName("Hallo");
		filter.setLengthStart(11);
		filter.setLengthEnd(22);
		filter.setYearStart(2013);
		filter.setYearEnd(2015);
		filter.setCountryIds(countryIds);
		filter.setGenreIds(countryIds);
		filter.setLanguageIds(countryIds);

		/* EXECUTION BLOCK */
		List<Genre> genres = rpcService.getGenreList(filter);

		/* VERIFICATION BLOCK */
		verify(manager, times(1)).createNativeQuery(
			"SELECT sel.id, sel.name, COUNT(*) AS count " 
			+ "FROM ( "
				+ "SELECT DISTINCT g.id AS id, g.name AS name, f.id AS film " 
				+ "FROM films f "
					+ "LEFT JOIN film_genres fg ON f.id = fg.film_id " 
					+ "JOIN genres g ON fg.genre_id = g.id "
					+ "LEFT JOIN film_countries fc ON f.id = fc.film_id " 
					+ "LEFT JOIN film_languages fl ON f.id = fl.film_id "
				+ "WHERE fc.country_id = :countryId "
					+ "AND f.year IS NOT NULL " 
					+ "AND (f.length BETWEEN :minLength AND :maxLength) "
					+ "AND (f.year BETWEEN :minYear AND :maxYear) "
					+ "AND LOWER(f.name) LIKE :findName " 
					+ "AND g.id IN (:genreIds) "
					+ "AND fl.language_id IN (:languageIds) " 
				+ ") AS sel " 
			+ "GROUP BY sel.id " 
			+ "ORDER BY count desc"
		);

		// verify that the correct country id is taken
		verify(countryGenresQuery, times(1)).setParameter("countryId", 99);

		// verify that all the parameters are correctly set (and year / country
		// were not filtered by!)
		verify(countryGenresQuery, times(1)).setParameter("minLength", 11);
		verify(countryGenresQuery, times(1)).setParameter("maxLength", 22);
		verify(countryGenresQuery, times(1)).setParameter("minYear", 2013);
		verify(countryGenresQuery, times(1)).setParameter("maxYear", 2015);
		verify(countryGenresQuery, times(1)).setParameter("findName", "%hallo%");
		verify(countryGenresQuery, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(countryGenresQuery, times(1)).setParameter("languageIds", filter.getLanguageIds());

		// verify that the query is then executed
		verify(countryGenresQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(countryGenresQuery);

		// assert that all 4 genres are present
		assertEquals(genres.size(), 4);

		// assert that the correct genres are present in the right order
		assertEquals(genres.get(0).getName(), "Horror");
		assertEquals(genres.get(1).getName(), "Adult");
		assertEquals(genres.get(2).getName(), "Romance");
		assertEquals(genres.get(3).getName(), "Action");

		// assert that the genres contain the right counts
		assertEquals(genres.get(0).getNumberOfFilms(), 55);
		assertEquals(genres.get(1).getNumberOfFilms(), 46);
		assertEquals(genres.get(2).getNumberOfFilms(), 33);
		assertEquals(genres.get(3).getNumberOfFilms(), 20);
	}

	@Test
	public void testGetGenreSelectOption() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */
		HashMap<Integer, String> genreSelects = rpcService.getGenreSelectOption();

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery("FROM GenreDB", GenreDB.class);

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
		verify(manager, times(1)).createQuery("FROM CountryDB", CountryDB.class);

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
		verify(manager, times(1)).createQuery("FROM LanguageDB", LanguageDB.class);

		// verify that the query is then executed
		verify(languageQuery, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(languageQuery);

		// assert that the HashMap entries were properly created
		assertEquals(languageSelects.get(0), "German");
	}

	@Test
	public void testGetSelectOptions() {
		// TODO
	}
}
