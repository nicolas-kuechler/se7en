package ch.uzh.se.se7en.junit.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.server.FilmEntryConverter;
import ch.uzh.se.se7en.shared.model.Film;

public class FilmEntryConverterTest {

	@Test
	public void test(){
		
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		
		countries.add("testistan");
		countries.add("testania");
		
		languages.add("testistanian");
		languages.add("testanian");
		
		genres.add("drama");
		genres.add("comedy");
		
		Film film = new Film("test", 100, 1000, countries, languages, genres);
		
		FilmEntryConverter filmEntryConverter = new FilmEntryConverter();
		
		String[] result = filmEntryConverter.convertEntry(film);
		
		assertTrue(result[0].equals("test"));
		assertTrue(result[1].equals("100"));
		assertTrue(result[2].equals("testistan--testania"));
		assertTrue(result[3].equals("testistanian--testanian"));
		assertTrue(result[4].equals("1000"));
		assertTrue(result[5].equals("drama--comedy"));
		
	}

}
