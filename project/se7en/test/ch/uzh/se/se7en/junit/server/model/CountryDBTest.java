package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.shared.model.Country;

public class CountryDBTest {

	CountryDB country;

	List<FilmCountryDB> filmCountryEntities = new ArrayList<FilmCountryDB>();

	@Before
	public void setup() {
		country = new CountryDB("Absurdistan");
		country.setId(99);
		country.setCode("AB");

		FilmDB film = new FilmDB("Hallo Welt", 10, 1993);
		filmCountryEntities.add(new FilmCountryDB(film, country));

		country.setFilmCountryEntities(filmCountryEntities);
	}

	@Test
	public void testCountryDB() {
		CountryDB country2 = new CountryDB();
		assertEquals(country2.getId(), 0);
		assertEquals(country2.getName(), null);
		assertEquals(country2.getCode(), null);
		assertEquals(country2.getFilmCountryEntities(), null);

		CountryDB country3 = new CountryDB(99);
		assertEquals(country3.getId(), 99);
		assertEquals(country3.getName(), null);
		assertEquals(country3.getCode(), null);
		assertEquals(country3.getFilmCountryEntities(), null);

		CountryDB country4 = new CountryDB("Absurdistan");
		assertEquals(country4.getId(), 0);
		assertEquals(country4.getName(), "Absurdistan");
		assertEquals(country4.getCode(), null);
		assertEquals(country4.getFilmCountryEntities(), null);
	}

	@Test
	public void testToCountry() {
		Country c = country.toCountry();
		assertEquals(c.getId(), 99);
		assertEquals(c.getCode(), "AB");
		assertEquals(c.getName(), "Absurdistan");
		assertEquals(c.getNumberOfFilms(), null);
	}

	@Test
	public void testGetId() {
		assertEquals(country.getId(), 99);
	}

	@Test
	public void testSetId() {
		country.setId(55);
		assertEquals(country.getId(), 55);
	}

	@Test
	public void testGetName() {
		assertEquals(country.getName(), "Absurdistan");
	}

	@Test
	public void testSetName() {
		country.setName("Bunzlistan");
		assertEquals(country.getName(), "Bunzlistan");
	}

	@Test
	public void testGetCode() {
		assertEquals(country.getCode(), "AB");
	}

	@Test
	public void testSetCode() {
		country.setCode("CH");
		assertEquals(country.getCode(), "CH");
	}

	@Test
	public void testGetFilmCountryEntities() {
		assertEquals(country.getFilmCountryEntities().size(), 1);
		assertEquals(country.getFilmCountryEntities().get(0).getFilmName(), "Hallo Welt");
	}

	@Test
	public void testSetFilmCountryEntities() {
		List<FilmCountryDB> filmCountryEntities = country.getFilmCountryEntities();
		filmCountryEntities
				.add(new FilmCountryDB(filmCountryEntities.get(0).getFilm(), filmCountryEntities.get(0).getCountry()));
		country.setFilmCountryEntities(filmCountryEntities);
		assertEquals(country.getFilmCountryEntities().size(), 2);
		assertEquals(country.getFilmCountryEntities().get(1).getFilmName(), "Hallo Welt");
	}
}
