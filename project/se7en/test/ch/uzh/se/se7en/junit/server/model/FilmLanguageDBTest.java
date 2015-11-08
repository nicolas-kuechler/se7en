package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDBId;
import ch.uzh.se.se7en.server.model.LanguageDB;

public class FilmLanguageDBTest {

	FilmLanguageDB entity;
	FilmDB film;
	LanguageDB language;

	@Before
	public void setup() {
		film = new FilmDB("Hallo Welt", 60, 1993);
		language = new LanguageDB("German");
		entity = new FilmLanguageDB(film, language);
		entity.setFilmId(99);
		entity.setLanguageId(88);
	}

	@Test
	public void testFilmLanguageDB() {
		FilmLanguageDB entity2 = new FilmLanguageDB();

		// assert that an empty primary key was created
		assertTrue(entity2.getPrimaryKey() != null);

		// assert that the linked entities were not initialized
		assertEquals(entity2.getLanguage(), null);
		assertEquals(entity2.getFilm(), null);
		assertEquals(entity2.getFilmId(), 0);
		assertEquals(entity2.getLanguageId(), 0);

		// assert that entities are set correctly via the constructor
		FilmLanguageDB entity3 = new FilmLanguageDB(entity.getFilm(), entity.getLanguage());
		assertEquals(entity3.getFilm(), entity.getFilm());
		assertEquals(entity3.getLanguage(), entity.getLanguage());
		assertEquals(entity3.getFilmId(), 0);
		assertEquals(entity3.getLanguageId(), 0);
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
	public void testGetLanguage() {
		assertEquals(entity.getLanguage(), language);
	}

	@Test
	public void testSetLanguage() {
		LanguageDB language = new LanguageDB("English");
		entity.setLanguage(language);
		assertEquals(entity.getLanguage(), language);
	}

	@Test
	public void testGetFilmName() {
		assertEquals(entity.getFilmName(), "Hallo Welt");
	}

	@Test
	public void testGetLanguageName() {
		assertEquals(entity.getLanguageName(), "German");
	}

	@Test
	public void testGetPrimaryKey() {
		assertEquals(entity.getPrimaryKey().getLanguage(), language);
		assertEquals(entity.getPrimaryKey().getFilm(), film);
	}

	@Test
	public void testSetPrimaryKey() {
		FilmLanguageDBId primaryKey = new FilmLanguageDBId();
		primaryKey.setFilm(new FilmDB("Hallo Mond", 90, 2015));
		primaryKey.setLanguage(new LanguageDB("Testish"));

		entity.setPrimaryKey(primaryKey);
		assertEquals(entity.getLanguage(), primaryKey.getLanguage());
		assertEquals(entity.getFilm(), primaryKey.getFilm());
		assertEquals(entity.getFilmName(), primaryKey.getFilm().getName());
		assertEquals(entity.getLanguageName(), primaryKey.getLanguage().getName());
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
	public void testGetLanguageId() {
		assertEquals(entity.getLanguageId(), 88);
	}

	@Test
	public void testSetLanguageId() {
		entity.setLanguageId(77);
		assertEquals(entity.getLanguageId(), 77);
	}
}
