package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDBId;
import ch.uzh.se.se7en.server.model.FilmLanguageDBId;
import ch.uzh.se.se7en.server.model.LanguageDB;

/**
 * Tests for the FilmLanguageDBId primary key entity
 * 
 * @author Dominik Bünzli
 */
public class FilmLanguageDBIdTest {

	LanguageDB language = new LanguageDB("German");
	FilmDB film = new FilmDB("Hallo Welt", 10, 1993);

	// test the constructors
	@Test
	public void testFilmLanguageDBId() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		assertEquals(filmGenreDBId.getGenre(), null);
	}

	// test getters and setters
	@Test
	public void testGetFilm() {
		FilmLanguageDBId filmLanguageDBId = new FilmLanguageDBId();
		filmLanguageDBId.setFilm(film);
		assertEquals(filmLanguageDBId.getFilm(), film);
	}

	@Test
	public void testSetFilm() {
		FilmLanguageDBId filmLanguageDBId = new FilmLanguageDBId();
		filmLanguageDBId.setFilm(film);
		assertEquals(filmLanguageDBId.getFilm(), film);
	}

	@Test
	public void testGetLanguage() {
		FilmLanguageDBId filmLanguageDBId = new FilmLanguageDBId();
		filmLanguageDBId.setLanguage(language);
		assertEquals(filmLanguageDBId.getLanguage(), language);
	}

	@Test
	public void testSetLanguage() {
		FilmLanguageDBId filmLanguageDBId = new FilmLanguageDBId();
		filmLanguageDBId.setLanguage(language);
		assertEquals(filmLanguageDBId.getLanguage(), language);
	}

}
