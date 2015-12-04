package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.shared.model.Film;

public class FilmTest {

	@Test
	public void testFilm() {
		Film film = new Film();
		assertEquals(film.getName(),null);
	}

	@Test
	public void testFilmString() {
		Film film = new Film("TestName");
		assertEquals(film.getName(),"TestName");
	}
		
	@Test
	public void testFilmStringIntIntListOfStringListOfStringListOfString() {
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		countries.add("Switzerland");
		languages.add("German");
		genres.add("Action");
		Film film = new Film("TestName",100,2000,countries,languages,genres);
		assertEquals(film.getName(),"TestName");
		assertEquals(film.getLanguages(),languages);
		assertEquals(film.getGenres(),genres);
	}

	@Test
	public void testFilmIntStringIntIntListOfStringListOfStringListOfString() {
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		countries.add("Switzerland");
		languages.add("German");
		genres.add("Action");
		Film film = new Film(10,"TestName",100,2000,countries,languages,genres);
		assertEquals(film.getName(),"TestName");
		assertEquals(film.getLanguages(),languages);
		assertEquals(film.getGenres(),genres);
		assertEquals(film.getId(),10);
	}

	@Test
	public void testToString() {
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		countries.add("Switzerland");
		languages.add("German");
		genres.add("Action");
		Film film = new Film(10,"TestName",100,2000,countries,languages,genres);
		film.setWikipedia("someId");
		assertEquals(film.toString(),"Id: 10 - Name: TestName - WikipediaID: someId - Länge: 100 - Länder: [Switzerland] - Sprachen: [German] - Genres: [Action]");
	}

	@Test
	public void testGetId() {
		Film film = new Film();
		film.setId(10);
		assertEquals(film.getId(),10);
	}

	@Test
	public void testSetId() {
		Film film = new Film();
		film.setId(10);
		assertEquals(film.getId(),10);
	}

	@Test
	public void testGetName() {
		Film film = new Film();
		film.setName("TestName");
		assertEquals(film.getName(),"TestName");
	}

	@Test
	public void testSetName() {
		Film film = new Film();
		film.setName("TestName");
		assertEquals(film.getName(),"TestName");
	}

	@Test
	public void testGetLength() {
		Film film = new Film();
		film.setLength(100);
		Integer digit = 100;
		assertEquals(film.getLength(),digit);
	}

	@Test
	public void testSetLength() {
		Film film = new Film();
		film.setLength(100);
		Integer digit = 100;
		assertEquals(film.getLength(),digit);
	}

	@Test
	public void testGetYear() {
		Film film = new Film();
		film.setYear(2000);
		Integer year = 2000;
		assertEquals(film.getYear(),year);
	}

	@Test
	public void testSetYear() {
		Film film = new Film();
		film.setYear(2000);
		Integer year = 2000;
		assertEquals(film.getYear(),year);
	}

	@Test
	public void testGetCountries() {
		Film film = new Film();
		List<String> countries = new ArrayList<String>();
		countries.add("Switzerland");
		film.setCountries(countries);
		List<String> countryList = film.getCountries();
		
		assertEquals(film.getCountries(),countryList);
	}

	@Test
	public void testSetCountries() {
		Film film = new Film();
		List<String> countries = new ArrayList<String>();
		countries.add("Switzerland");
		film.setCountries(countries);
		List<String> countryList = film.getCountries();
		
		assertEquals(film.getCountries(),countryList);
	}

	@Test
	public void testGetLanguages() {
		Film film = new Film();
		List<String> language = new ArrayList<String>();
		language.add("German");
		film.setLanguages(language);
		List<String> languageList = film.getLanguages();
		
		assertEquals(film.getLanguages(),languageList);
	}

	@Test
	public void testSetLanguages() {
		Film film = new Film();
		List<String> language = new ArrayList<String>();
		language.add("German");
		film.setLanguages(language);
		List<String> languageList = film.getLanguages();
		
		assertEquals(film.getLanguages(),languageList);
	}

	@Test
	public void testGetGenres() {
		Film film = new Film();
		List<String> genre = new ArrayList<String>();
		genre.add("Action");
		film.setLanguages(genre);
		List<String> genreList = film.getGenres();
		
		assertEquals(film.getGenres(),genreList);
	}

	@Test
	public void testSetGenres() {
		Film film = new Film();
		List<String> genre = new ArrayList<String>();
		genre.add("Action");
		film.setLanguages(genre);
		List<String> genreList = film.getGenres();
		
		assertEquals(film.getGenres(),genreList);
	}

}
