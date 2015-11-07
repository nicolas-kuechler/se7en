package ch.uzh.se.se7en.junit.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ch.uzh.se.se7en.junit.server.TestUtil.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.uzh.se.se7en.server.TriggerImportServiceImpl;
import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Test for the importing of data into the database
 * 
 * @author Roland Schläfli
 */
@RunWith(JukitoRunner.class)
public class TriggerImportServiceTest {

	@Inject
	TriggerImportServiceImpl importService;
	
	@Inject
	EntityManager manager;
	
	Provider<EntityManager> em;
	
	@Before
	public void setup() {
		// need to do this manually for mocking a generic
		em = (Provider<EntityManager>) mock(Provider.class);
		
		// create fake return values for existing countries
		List<CountryDB> existingCountries = new ArrayList<CountryDB>();
		existingCountries.add(new CountryDB("Roland"));
		existingCountries.add(new CountryDB("Legoland"));
		
		// create fake return values for existing genres
		List<GenreDB> existingGenres = new ArrayList<GenreDB>();
		existingGenres.add(new GenreDB("Action"));
		existingGenres.add(new GenreDB("Romance"));
		existingGenres.add(new GenreDB("Horror"));
		
		// create fake return values for existing languages
		List<LanguageDB> existingLanguages = new ArrayList<LanguageDB>();
		existingLanguages.add(new LanguageDB("German"));
		
		// mock the queries for fetching all the existing countries, genres, languages
		TypedQuery<CountryDB> existingCountriesQuery = mockQuery(existingCountries);
		TypedQuery<GenreDB> existingGenresQuery = mockQuery(existingGenres);
		TypedQuery<LanguageDB> existingLanguagesQuery = mockQuery(existingLanguages);
		when(manager.createQuery("from CountryDB", CountryDB.class)).thenReturn(existingCountriesQuery);
		when(manager.createQuery("from GenreDB", GenreDB.class)).thenReturn(existingGenresQuery);
		when(manager.createQuery("from LanguageDB", LanguageDB.class)).thenReturn(existingLanguagesQuery);
		
		// mock the call get() on the provider
		doReturn(manager).when(em).get();
		
		// inject the mock into the class
		importService.setEm(em);
	}
	
	@Test
	public void testImportFilmsToDB() {
		/* INITIALIZATION BLOCK */
		// create a list of test films for importing
		List<Film> films = new ArrayList<Film>();
		List<FilmDB> dbFilms = new ArrayList<FilmDB>();
		
		// create string lists for saving test data
		List<String> countries = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		
		// initialize the test data
		for(int i = 1; i <= 10; i++) {
			// Add 10 countries
			countries.add("Country" + i);
			
			if(i % 2 == 0) {
				// Add 5 genres
				genres.add("Genre" + i / 2);
			}
			
			if(i % 3 == 0) {
				// Add 3 languages
				languages.add("Language" + i / 3);
			}
		}
		
		// create some test films
		films.add(new Film("Film 1", 30, 2015, countries, languages, genres));
		films.add(new Film("Film 2", 60, 1993, countries, languages, genres));
		films.add(new Film("Film 3", 90, 1996, countries, languages, genres));
		
		// execute the import method, passing the test films
		importService.importFilmsToDB(films);
		
		/* VERIFICATION BLOCK */
		// verify that the existing entities were all fetched from the database
		verify(manager).createQuery("from CountryDB", CountryDB.class);
		verify(manager).createQuery("from GenreDB", GenreDB.class);
		verify(manager).createQuery("from LanguageDB", LanguageDB.class);
		
		// assert that the internal maps contain the right amount of entities
		assertEquals(importService.getCountryMap().size(), 12); // 2 existing + 10 added
		assertEquals(importService.getGenreMap().size(), 8); // 3 existing + 5 added
		assertEquals(importService.getLanguageMap().size(), 4); // 1 existing + 3 added
		
		// verify that each new entity was persisted only once
		verifyPersisted(manager, 10, 1, importService.getCountryMap(), "Country");
		verifyPersisted(manager, 5, 1, importService.getGenreMap(), "Genre");
		verifyPersisted(manager, 3, 1, importService.getLanguageMap(), "Language");
		
		// verify that 3 films were persisted
		verify(manager, times(3)).persist(Matchers.isA(FilmDB.class));
	}
}
