package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDBId;
import ch.uzh.se.se7en.server.model.FilmDB;

/**
 * Tests for the FilmCountryDBId primary key entity
 * 
 * @author Dominik Bünzli
 */
public class FilmCountryDBIdTest {

	CountryDB country = new CountryDB("Absurdistan");
	FilmDB film = new FilmDB("Hallo Welt", 10, 1993);

	// test the constructors
	@Test
	public void testFilmCountryDBId() {
		FilmCountryDBId countryDBId = new FilmCountryDBId();
		assertEquals(countryDBId.getFilm(), null);
	}

	// test getters and setters
	@Test
	public void testGetFilm() {
		FilmCountryDBId countryDBId = new FilmCountryDBId();
		countryDBId.setFilm(film);
		assertEquals(countryDBId.getFilm(), film);
	}

	@Test
	public void testSetFilm() {
		FilmCountryDBId countryDBId = new FilmCountryDBId();
		countryDBId.setFilm(film);
		assertEquals(countryDBId.getFilm(), film);
	}

	@Test
	public void testGetCountry() {
		FilmCountryDBId countryDBId = new FilmCountryDBId();
		countryDBId.setCountry(country);
		assertEquals(countryDBId.getCountry(), country);
	}

	@Test
	public void testSetCountry() {
		FilmCountryDBId countryDBId = new FilmCountryDBId();
		countryDBId.setCountry(country);
		assertEquals(countryDBId.getCountry(), country);
	}

}
