package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmDB;

public class CountryDBTest {

	CountryDB country;
	
	@Before
	public void setup() {
		country = new CountryDB("Absurdistan");
		country.setId(99);
		country.setCode("AB");
		
		List<FilmCountryDB> filmCountryEntities = new ArrayList<FilmCountryDB>();
		
		FilmDB film = new FilmDB("Hallo Welt", 10, 1993);
		filmCountryEntities.add(new FilmCountryDB(film, country));
		
		country.setFilmCountryEntities(filmCountryEntities);
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
		filmCountryEntities.add(new FilmCountryDB(filmCountryEntities.get(0).getFilm(), filmCountryEntities.get(0).getCountry()));
		country.setFilmCountryEntities(filmCountryEntities);
		assertEquals(country.getFilmCountryEntities().size(), 2);
		assertEquals(country.getFilmCountryEntities().get(1).getFilmName(), "Hallo Welt");
	}
}
