package ch.uzh.se.se7en.junit.server;

import static ch.uzh.se.se7en.junit.server.TestUtil.mockQuery;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
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
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.FilmFilter;

/**
 * Test for the answering of rpc calls
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

	// create fake lists of all the entities in the database
	List<FilmDB> dbFilms = new ArrayList<FilmDB>();
	List<CountryDB> dbCountries = new ArrayList<CountryDB>();
	List<GenreDB> dbGenres = new ArrayList<GenreDB>();
	List<LanguageDB> dbLanguages = new ArrayList<LanguageDB>();

	@Before
	public void setup() {
		// need to do this manually for mocking a generic
		em = (Provider<EntityManager>) mock(Provider.class);

		// add fake content to the entity lists
		dbFilms.add(new FilmDB("Film 1", 90, 2015));
		dbCountries.add(new CountryDB("Switzerland"));
		dbGenres.add(new GenreDB("Horror"));
		dbLanguages.add(new LanguageDB("German"));

		// mock the call get() on the provider
		doReturn(manager).when(em).get();

		// inject the mock into the class
		rpcService.setEm(em);
	}

	@Test
	public void testGetFilmList() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */
		// rpcService.getFilmList(new FilmFilter());

		/* VERIFICATION BLOCK */

	}

	@Test
	public void testGetFilmEntitiesListFilterDefault() {
		/* INITIALIZATION BLOCK */
		// return the fake film list on any call
		TypedQuery<FilmDB> query = mockQuery(dbFilms);
		doReturn(query).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));

		// filter with the default filters
		FilmFilter filter = new FilmFilter();

		/* EXECUTION BLOCK */
		rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear) ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(query, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(query, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(query, times(1)).setParameter("minYear", filter.getYearStart());
		verify(query, times(1)).setParameter("maxYear", filter.getYearEnd());

		// verify that the query is then executed
		verify(query, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(query);
	}

	@Test
	public void testGetFilmEntitiesListFilterByName() {
		/* INITIALIZATION BLOCK */
		// return the fake film list on any call
		TypedQuery<FilmDB> query = mockQuery(dbFilms);
		doReturn(query).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));

		// filter only by name
		FilmFilter filter = new FilmFilter("Hallo");

		/* EXECUTION BLOCK */
		rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear) AND LOWER(f.name) LIKE :findName ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(query, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(query, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(query, times(1)).setParameter("minYear", filter.getYearStart());
		verify(query, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(query, times(1)).setParameter("findName", "%hallo%");

		// verify that the query is then executed
		verify(query, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(query);
	}

	@Test
	public void testGetFilmEntitiesListFilterByNameAndCountries() {
		/* INITIALIZATION BLOCK */
		// return the fake film list on any call
		TypedQuery<FilmDB> query = mockQuery(dbFilms);
		doReturn(query).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));

		// set the filters
		FilmFilter filter = new FilmFilter("Hallo");
		Set<Integer> countryIds = new HashSet<Integer>();
		countryIds.add(1);
		countryIds.add(99);
		filter.setCountryIds(countryIds);

		/* EXECUTION BLOCK */
		rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f JOIN f.filmCountryEntities fc WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear) AND LOWER(f.name) LIKE :findName AND fc.countryId IN :countryIds ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(query, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(query, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(query, times(1)).setParameter("minYear", filter.getYearStart());
		verify(query, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(query, times(1)).setParameter("findName", "%hallo%");
		verify(query, times(1)).setParameter("countryIds", filter.getCountryIds());

		// verify that the query is then executed
		verify(query, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(query);
	}

	@Test
	public void testGetFilmEntitiesListFilterByEverything() {
		/* INITIALIZATION BLOCK */
		// return the fake film list on any call
		TypedQuery<FilmDB> query = mockQuery(dbFilms);
		doReturn(query).when(manager).createQuery(Matchers.anyString(), Matchers.same(FilmDB.class));

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
		rpcService.getFilmEntitiesList(filter);

		/* VERIFICATION BLOCK */
		// verify that the correct query string is generated
		verify(manager, times(1)).createQuery(
				"SELECT DISTINCT f FROM FilmDB f JOIN f.filmCountryEntities fc JOIN f.filmGenreEntities fg JOIN f.filmLanguageEntities fl WHERE (f.length BETWEEN :minLength AND :maxLength) AND (f.year BETWEEN :minYear AND :maxYear) AND LOWER(f.name) LIKE :findName AND fc.countryId IN :countryIds AND fg.genreId IN :genreIds AND fl.languageId IN :languageIds ORDER BY f.name",
				FilmDB.class);

		// verify that all the parameters are correctly set
		verify(query, times(1)).setParameter("minLength", filter.getLengthStart());
		verify(query, times(1)).setParameter("maxLength", filter.getLengthEnd());
		verify(query, times(1)).setParameter("minYear", filter.getYearStart());
		verify(query, times(1)).setParameter("maxYear", filter.getYearEnd());
		verify(query, times(1)).setParameter("findName", "%hallo%");
		verify(query, times(1)).setParameter("countryIds", filter.getCountryIds());
		verify(query, times(1)).setParameter("genreIds", filter.getGenreIds());
		verify(query, times(1)).setParameter("languageIds", filter.getLanguageIds());

		// verify that the query is then executed
		verify(query, times(1)).getResultList();

		// verify that the query isn't touched any further
		verifyNoMoreInteractions(query);
	}

	@Test
	public void testGetCountryList() {
		/* INITIALIZATION BLOCK */

		/* EXECUTION BLOCK */

		/* VERIFICATION BLOCK */
	}

	@Test
	public void testGetCountryEntitiesList() {
		/* INITIALIZATION BLOCK */
		// return the country list on any call
		doReturn(mockQuery(dbCountries)).when(manager).createQuery(Matchers.anyString(),
				Matchers.same(CountryDB.class));

		/* VERIFICATION BLOCK */
		// execute without any filters
		rpcService.getCountryEntitiesList(new FilmFilter());
	}

	@Test
	public void testGetGenreList() {
	}

	@Test
	public void testGetGenreSelectOption() {

	}

	@Test
	public void testGetCountrySelectOption() {

	}

	@Test
	public void testGetLanguageSelectOption() {

	}
}
