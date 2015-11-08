package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDB;
import ch.uzh.se.se7en.server.model.FilmGenreDBId;
import ch.uzh.se.se7en.server.model.GenreDB;

public class FilmGenreDBTest {

	FilmGenreDB entity;
	FilmDB film;
	GenreDB genre;

	@Before
	public void setup() {
		film = new FilmDB("Hallo Welt", 60, 1993);
		genre = new GenreDB("Horror");
		entity = new FilmGenreDB(film, genre);
		entity.setFilmId(99);
		entity.setGenreId(88);
	}

	@Test
	public void testFilmGenreDB() {
		FilmGenreDB entity2 = new FilmGenreDB();

		// assert that an empty primary key was created
		assertTrue(entity2.getPrimaryKey() != null);

		// assert that the linked entities were not initialized
		assertEquals(entity2.getGenre(), null);
		assertEquals(entity2.getFilm(), null);
		assertEquals(entity2.getFilmId(), 0);
		assertEquals(entity2.getGenreId(), 0);

		// assert that entities are set correctly via the constructor
		FilmGenreDB entity3 = new FilmGenreDB(entity.getFilm(), entity.getGenre());
		assertEquals(entity3.getFilm(), entity.getFilm());
		assertEquals(entity3.getGenre(), entity.getGenre());
		assertEquals(entity3.getFilmId(), 0);
		assertEquals(entity3.getGenreId(), 0);
	}

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
	public void testGetGenre() {
		assertEquals(entity.getGenre(), genre);
	}

	@Test
	public void testSetGenre() {
		GenreDB genre = new GenreDB("Romance");
		entity.setGenre(genre);
		assertEquals(entity.getGenre(), genre);
	}

	@Test
	public void testGetFilmName() {
		assertEquals(entity.getFilmName(), "Hallo Welt");
	}

	@Test
	public void testGetGenreName() {
		assertEquals(entity.getGenreName(), "Horror");
	}

	@Test
	public void testGetPrimaryKey() {
		assertEquals(entity.getPrimaryKey().getGenre(), genre);
		assertEquals(entity.getPrimaryKey().getFilm(), film);
	}

	@Test
	public void testSetPrimaryKey() {
		FilmGenreDBId primaryKey = new FilmGenreDBId();
		primaryKey.setFilm(new FilmDB("Hallo Mond", 90, 2015));
		primaryKey.setGenre(new GenreDB("Testenre"));

		entity.setPrimaryKey(primaryKey);
		assertEquals(entity.getGenre(), primaryKey.getGenre());
		assertEquals(entity.getFilm(), primaryKey.getFilm());
		assertEquals(entity.getFilmName(), primaryKey.getFilm().getName());
		assertEquals(entity.getGenreName(), primaryKey.getGenre().getName());
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
	public void testGetGenreId() {
		assertEquals(entity.getGenreId(), 88);
	}

	@Test
	public void testSetGenreId() {
		entity.setGenreId(77);
		assertEquals(entity.getGenreId(), 77);
	}
}
