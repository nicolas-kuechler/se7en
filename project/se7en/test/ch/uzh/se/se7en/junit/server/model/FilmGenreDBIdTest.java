package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.server.model.CountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDB;
import ch.uzh.se.se7en.server.model.FilmCountryDBId;
import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDBId;
import ch.uzh.se.se7en.server.model.GenreDB;

public class FilmGenreDBIdTest {
	
	GenreDB genre = new GenreDB("Action");
	FilmDB film = new FilmDB("Hallo Welt", 10, 1993);
	
	@Test
	public void testFilmGenreDBId() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		assertEquals(filmGenreDBId.getGenre(),null);
	}

	@Test
	public void testGetFilm() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		filmGenreDBId.setFilm(film);
		assertEquals(filmGenreDBId.getFilm(),film);
	}

	@Test
	public void testSetFilm() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		filmGenreDBId.setFilm(film);
		assertEquals(filmGenreDBId.getFilm(),film);
	}

	@Test
	public void testGetGenre() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		filmGenreDBId.setGenre(genre);
		assertEquals(filmGenreDBId.getGenre(),genre);
	}

	@Test
	public void testSetGenre() {
		FilmGenreDBId filmGenreDBId = new FilmGenreDBId();
		filmGenreDBId.setGenre(genre);
		assertEquals(filmGenreDBId.getGenre(),genre);
	}

}
