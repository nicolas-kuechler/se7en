package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDBId;
import ch.uzh.se.se7en.server.model.FilmDB;

/**
 * Tests for the FilmCountryDB join table entity
 * 
 * @author Roland Schläfli
 */
public class FilmCountryDBTest {

	FilmCountryDB entity;
	FilmDB film;
	CountryDB country;

	// setup the fake data for testing
	@Before
	public void setup() {
		film = new FilmDB("Hallo Welt", 60, 1993);
		country = new CountryDB("Absurdistan");
		entity = new FilmCountryDB(film, country);
		entity.setFilmId(99);
		entity.setCountryId(88);
	}

	// test the constructors
	@Test
	public void testFilmCountryDB() {
		FilmCountryDB entity2 = new FilmCountryDB();

		// assert that an empty primary key was created
		assertTrue(entity2.getPrimaryKey() != null);

		// assert that the linked entities were not initialized
		assertEquals(entity2.getCountry(), null);
		assertEquals(entity2.getFilm(), null);
		assertEquals(entity2.getFilmId(), 0);
		assertEquals(entity2.getCountryId(), 0);

		// assert that entities are set correctly via the constructor
		FilmCountryDB entity3 = new FilmCountryDB(entity.getFilm(), entity.getCountry());
		assertEquals(entity3.getFilm(), entity.getFilm());
		assertEquals(entity3.getCountry(), entity.getCountry());
		assertEquals(entity3.getFilmId(), 0);
		assertEquals(entity3.getCountryId(), 0);
	}

	// test getters and setters
	@Test
	public void testGetFilm() {
		assertEquals(entity.getFilm(), film);
	}

	@Test
	public void testSetFilm() {
		FilmDB film = new FilmDB("Testovie", 44, 2000);
		entity.setFilm(film);
		assertEquals(entity.getFilm(), film);
	}

	@Test
	public void testGetCountry() {
		assertEquals(entity.getCountry(), country);
	}

	@Test
	public void testSetCountry() {
		CountryDB country = new CountryDB("Testistan");
		entity.setCountry(country);
		assertEquals(entity.getCountry(), country);
	}

	@Test
	public void testGetFilmName() {
		assertEquals(entity.getFilmName(), "Hallo Welt");
	}

	@Test
	public void testGetCountryName() {
		assertEquals(entity.getCountryName(), "Absurdistan");
	}

	@Test
	public void testGetPrimaryKey() {
		assertEquals(entity.getPrimaryKey().getCountry(), country);
		assertEquals(entity.getPrimaryKey().getFilm(), film);
	}

	@Test
	public void testSetPrimaryKey() {
		FilmCountryDBId primaryKey = new FilmCountryDBId();
		primaryKey.setFilm(new FilmDB("Hallo Mond", 90, 2015));
		primaryKey.setCountry(new CountryDB("Bunzlistan"));

		entity.setPrimaryKey(primaryKey);
		assertEquals(entity.getCountry(), primaryKey.getCountry());
		assertEquals(entity.getFilm(), primaryKey.getFilm());
		assertEquals(entity.getFilmName(), primaryKey.getFilm().getName());
		assertEquals(entity.getCountryName(), primaryKey.getCountry().getName());
	}

	@Test
	public void testGetFilmId() {
		assertEquals(entity.getFilmId(), 99);
	}

	@Test
	public void testSetFilmId() {
		entity.setFilmId(66);
		assertEquals(entity.getFilmId(), 66);
	}

	@Test
	public void testGetCountryId() {
		assertEquals(entity.getCountryId(), 88);
	}

	@Test
	public void testSetCountryId() {
		entity.setCountryId(77);
		assertEquals(entity.getCountryId(), 77);
	}
}
