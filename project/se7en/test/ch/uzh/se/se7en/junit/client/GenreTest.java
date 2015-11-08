package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.se.se7en.shared.model.Genre;

public class GenreTest {

	@Test
	public void testGenre() {
		Genre genre = new Genre();
		assertEquals(null,genre.getName());
	}

	@Test
	public void testGenreIntStringInt() {
		Genre genre = new Genre(10,"Action",5);
		assertEquals(10,genre.getId());
		assertEquals("Action",genre.getName());
		assertEquals(5,genre.getNumberOfFilms());
	}

	@Test
	public void testToString() {
		Genre genre = new Genre(10,"Action",5);
		assertEquals("Id: 10 - Name: Action - Anzahl Filme: 5",genre.toString());
	}

	@Test
	public void testGetId() {
		Genre genre = new Genre();
		genre.setId(10);
		assertEquals(10,genre.getId());
	}

	@Test
	public void testSetId() {
		Genre genre = new Genre();
		genre.setId(10);
		assertEquals(10,genre.getId());
	}

	@Test
	public void testGetName() {
		Genre genre = new Genre();
		genre.setName("Action");
		assertEquals("Action",genre.getName());
	}

	@Test
	public void testSetName() {
		Genre genre = new Genre();
		genre.setName("Action");
		assertEquals("Action",genre.getName());
	}

	@Test
	public void testGetNumberOfFilms() {
		Genre genre = new Genre();
		genre.setNumberOfFilms(10);
		assertEquals(10,genre.getNumberOfFilms());
	}

	@Test
	public void testSetNumberOfFilms() {
		Genre genre = new Genre();
		genre.setNumberOfFilms(10);
		assertEquals(10,genre.getNumberOfFilms());
	}

}
