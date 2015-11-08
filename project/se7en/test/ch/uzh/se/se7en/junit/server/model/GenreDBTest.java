package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmGenreDB;
import ch.uzh.se.se7en.server.model.GenreDB;
import ch.uzh.se.se7en.shared.model.Genre;

@RunWith(JukitoRunner.class)
public class GenreDBTest {

	GenreDB genre;
	
	@Before
	public void setup() {
		genre = new GenreDB("Horror");
		genre.setId(99);
		
		List<FilmGenreDB> filmGenreEntities = new ArrayList<FilmGenreDB>();
		
		FilmDB film = new FilmDB("Hallo Welt", 10, 1993);
		filmGenreEntities.add(new FilmGenreDB(film, genre));
		
		genre.setFilmGenreEntities(filmGenreEntities);
	}
	
	@Test
	public void testGenreDB() {
		GenreDB genre2 = new GenreDB();
		assertEquals(genre2.getId(), 0);
		assertEquals(genre2.getName(), null);
		assertEquals(genre2.getFilmGenreEntities(), null);
		
		GenreDB genre3 = new GenreDB("Horror");
		assertEquals(genre3.getId(), 0);
		assertEquals(genre3.getName(), "Horror");
		assertEquals(genre3.getFilmGenreEntities(), null);
		
		GenreDB genre4 = new GenreDB(99);
		assertEquals(genre4.getId(), 99);
		assertEquals(genre4.getName(), null);
		assertEquals(genre4.getFilmGenreEntities(), null);
	}
	
	@Test
	public void testToGenre() {
		Genre g = genre.toGenre();
		assertEquals(g.getName(), "Horror");
		assertEquals(g.getId(), 99);
		assertEquals(g.getNumberOfFilms(), 1);
	}
	
	@Test
	public void testGetId() {
		assertEquals(genre.getId(), 99);
	}
	
	@Test
	public void testSetId() {
		genre.setId(88);
		assertEquals(genre.getId(), 88);
	}
	
	@Test
	public void testGetName() {
		assertEquals(genre.getName(), "Horror");
	}
	
	@Test
	public void testSetName() {
		genre.setName("Romance");
		assertEquals(genre.getName(), "Romance");
	}
}
