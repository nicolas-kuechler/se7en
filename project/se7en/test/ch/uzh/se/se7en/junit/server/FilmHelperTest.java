package ch.uzh.se.se7en.junit.server;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.se.se7en.server.model.FilmHelper;

public class FilmHelperTest {

	@Test
	public void getNameTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertTrue(filmHelper.getName().equals("Test1"));
	}
	
	@Test
	public void getLengthTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertEquals(200, (int)filmHelper.getLength());
	}
	
	@Test
	public void getCountriesTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertTrue(filmHelper.getCountries().equals("Test2"));
	}

	@Test
	public void getLanguagesTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertTrue(filmHelper.getLanguages().equals("Test3"));
	}
	
	@Test
	public void getYearTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertEquals(2000, (int)filmHelper.getYear());
	}
	
	@Test
	public void getGenresTest() {
		FilmHelper filmHelper = new FilmHelper("Test1", 200, "Test2", "Test3", 2000, "Test4");
		assertTrue(filmHelper.getGenres().equals("Test4"));
	}
}
