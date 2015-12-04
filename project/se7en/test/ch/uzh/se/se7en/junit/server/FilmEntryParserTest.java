package ch.uzh.se.se7en.junit.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.server.FilmEntryConverter;
import ch.uzh.se.se7en.server.FilmEntryParser;
import ch.uzh.se.se7en.shared.model.Film;

public class FilmEntryParserTest {

	@Test
	public void test(){
		
		String[] data = new String[7];
		data[0] = "test";
		data[1] = "100";
		data[2] = "testistan--testania";
		data[3] = "testistanian--testanian";
		data[4] = "1000";
		data[5] = "drama--comedy";
		data[6] = "someId";
		
		FilmEntryParser filmEntryParser = new FilmEntryParser();
		
		Film film = filmEntryParser.parseEntry(data);
		
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		
		countries.add("testistan");
		countries.add("testania");
		
		languages.add("testistanian");
		languages.add("testanian");
		
		genres.add("drama");
		genres.add("comedy");
		
		assertTrue(film.getName().equals("test"));
		assertTrue(film.getLength() == 100);
		assertTrue(film.getCountries().equals(countries));
		assertTrue(film.getLanguages().equals(languages));
		assertTrue(film.getYear() == 1000);
		assertTrue(film.getGenres().equals(genres));
		assertTrue(film.getWikipedia()== "someId");
	}

}
