package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Tests for the FilmDB entity
 * 
 * @author Roland Schläfli
 */
@RunWith(JukitoRunner.class)
public class FilmDBTest {

	FilmDB dbFilm;

	Set<FilmCountryDB> countries = new HashSet<FilmCountryDB>();
	Set<FilmGenreDB> genres = new HashSet<FilmGenreDB>();
	Set<FilmLanguageDB> languages = new HashSet<FilmLanguageDB>();

	// setup the fake data for testing
	@Before
	public void setup() {
		dbFilm = new FilmDB("Der Tiger", 120, 1993);
		dbFilm.setId(11);
		dbFilm.setWikipedia("a7ah34ad");

		CountryDB country = new CountryDB("Absurdistan");
		country.setId(22);
		country.setCode("AB");

		GenreDB genre = new GenreDB("Horror");
		genre.setId(33);

		LanguageDB language = new LanguageDB("Deutsch");
		language.setId(44);

		FilmCountryDB filmCountryEntity = new FilmCountryDB(dbFilm, country);
		countries.add(filmCountryEntity);
		dbFilm.setFilmCountryEntities(countries);

		FilmGenreDB filmGenreEntity = new FilmGenreDB(dbFilm, genre);
		genres.add(filmGenreEntity);
		dbFilm.setFilmGenreEntities(genres);

		FilmLanguageDB filmLanguageEntity = new FilmLanguageDB(dbFilm, language);
		languages.add(filmLanguageEntity);
		dbFilm.setFilmLanguageEntities(languages);
	}

	// test the constructors
	@Test
	public void testFilmDB() {
		FilmDB film2 = new FilmDB();
		assertEquals(film2.getId(), 0);
		assertEquals(film2.getName(), null);
		assertEquals(film2.getLength(), null);
		assertEquals(film2.getYear(), null);
		assertEquals(film2.getWikipedia(), null);
		assertEquals(film2.getFilmCountryEntities(), null);
		assertEquals(film2.getFilmGenreEntities(), null);
		assertEquals(film2.getFilmLanguageEntities(), null);

		FilmDB film3 = new FilmDB("Film 1", 120, 1993);
		assertEquals(film3.getId(), 0);
		assertEquals(film3.getName(), "Film 1");
		assertEquals(film3.getLength(), new Integer(120));
		assertEquals(film3.getYear(), new Integer(1993));
		assertEquals(film3.getWikipedia(), null);
		assertEquals(film3.getFilmCountryEntities(), null);
		assertEquals(film3.getFilmGenreEntities(), null);
		assertEquals(film3.getFilmLanguageEntities(), null);

		FilmDB film4 = new FilmDB("Film 1", 130, 2014, countries, languages, genres);
		assertEquals(film4.getId(), 0);
		assertEquals(film4.getName(), "Film 1");
		assertEquals(film4.getLength(), new Integer(130));
		assertEquals(film4.getYear(), new Integer(2014));
		assertEquals(film4.getWikipedia(), null);
		assertEquals(film4.getFilmCountryEntities(), countries);
		assertEquals(film4.getFilmGenreEntities(), genres);
		assertEquals(film4.getFilmLanguageEntities(), languages);
	}

	// test the FilmDB to Film conversion
	@Test
	public void testToFilm() {
		Film film = dbFilm.toFilm();

		assertEquals(film.getId(), 11);
		assertEquals(film.getName(), "Der Tiger");
		assertEquals(film.getLength(), new Integer(120));
		assertEquals(film.getYear(), new Integer(1993));
		assertEquals(film.getCountries().size(), 1);
		assertEquals(film.getCountries().get(0), "Absurdistan");
		assertEquals(film.getGenres().size(), 1);
		assertEquals(film.getGenres().get(0), "Horror");
		assertEquals(film.getLanguages().size(), 1);
		assertEquals(film.getLanguages().get(0), "Deutsch");
	}
	
	// test getters and setters
	@Test
	public void testGetId() {
		assertEquals(dbFilm.getId(), 11);
	}

	@Test
	public void testSetId() {
		dbFilm.setId(12);
		assertEquals(dbFilm.getId(), 12);
	}

	@Test
	public void testGetName() {
		assertEquals(dbFilm.getName(), "Der Tiger");
	}

	@Test
	public void testSetName() {
		dbFilm.setName("Der Gorilla");
		assertEquals(dbFilm.getName(), "Der Gorilla");
	}

	@Test
	public void testGetLength() {
		assertEquals(dbFilm.getLength(), new Integer(120));
	}

	@Test
	public void testSetLength() {
		dbFilm.setLength(150);
		assertEquals(dbFilm.getLength(), new Integer(150));
	}

	@Test
	public void testGetYear() {
		assertEquals(dbFilm.getYear(), new Integer(1993));
	}

	@Test
	public void testSetYear() {
		dbFilm.setYear(2015);
		assertEquals(dbFilm.getYear(), new Integer(2015));
	}

	@Test
	public void testGetWikipedia() {
		assertEquals(dbFilm.getWikipedia(), "a7ah34ad");
	}

	@Test
	public void testSetWikipedia() {
		dbFilm.setWikipedia("a8m2of9");
		assertEquals(dbFilm.getWikipedia(), "a8m2of9");
	}

	@Test
	public void testGetFilmCountryEntities() {
		assertEquals(dbFilm.getFilmCountryEntities().size(), 1);
	}

	@Test
	public void testSetFilmCountryEntities() {
		// assert that the same film can only be added once
		List<FilmCountryDB> films = new ArrayList<FilmCountryDB>(dbFilm.getFilmCountryEntities());
		films.add(films.get(0));
		dbFilm.setFilmCountryEntities(new HashSet<FilmCountryDB>(films));
		assertEquals(dbFilm.getFilmCountryEntities().size(), 1);

		// assert that a different film can be added
		FilmCountryDB filmCountry = new FilmCountryDB(dbFilm, films.get(0).getCountry());
		films.add(filmCountry);
		dbFilm.setFilmCountryEntities(new HashSet<FilmCountryDB>(films));
		assertEquals(dbFilm.getFilmCountryEntities().size(), 2);
	}

	@Test
	public void testGetFilmGenreEntities() {
		assertEquals(dbFilm.getFilmGenreEntities().size(), 1);
	}

	@Test
	public void testSetFilmGenreEntities() {
		// assert that the same genre can only be added once
		List<FilmGenreDB> genres = new ArrayList<FilmGenreDB>(dbFilm.getFilmGenreEntities());
		genres.add(genres.get(0));
		dbFilm.setFilmGenreEntities(new HashSet<FilmGenreDB>(genres));
		assertEquals(dbFilm.getFilmGenreEntities().size(), 1);

		// assert that a different genre can be added
		FilmGenreDB filmGenre = new FilmGenreDB(dbFilm, genres.get(0).getGenre());
		genres.add(filmGenre);
		dbFilm.setFilmGenreEntities(new HashSet<FilmGenreDB>(genres));
		assertEquals(dbFilm.getFilmGenreEntities().size(), 2);
	}

	@Test
	public void testGetFilmLanguageEntities() {
		assertEquals(dbFilm.getFilmLanguageEntities().size(), 1);
	}

	@Test
	public void testSetFilmLanguageEntities() {
		// assert that the same language can only be added once
		List<FilmLanguageDB> languages = new ArrayList<FilmLanguageDB>(dbFilm.getFilmLanguageEntities());
		languages.add(languages.get(0));
		dbFilm.setFilmLanguageEntities(new HashSet<FilmLanguageDB>(languages));
		assertEquals(dbFilm.getFilmLanguageEntities().size(), 1);

		// assert that a different film can be added
		FilmLanguageDB filmLanguage = new FilmLanguageDB(dbFilm, languages.get(0).getLanguage());
		languages.add(filmLanguage);
		dbFilm.setFilmLanguageEntities(new HashSet<FilmLanguageDB>(languages));
		assertEquals(dbFilm.getFilmLanguageEntities().size(), 2);
	}
}
