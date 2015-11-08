package ch.uzh.se.se7en.junit.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.uzh.se.se7en.server.model.FilmDB;
import ch.uzh.se.se7en.server.model.FilmLanguageDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.server.model.LanguageDB;
import ch.uzh.se.se7en.shared.model.Language;

@RunWith(JukitoRunner.class)
public class LanguageDBTest {

	LanguageDB language;
	
	@Before
	public void setup() {
		language = new LanguageDB("German");
		language.setId(99);
		
		List<FilmLanguageDB> filmLanguageEntities = new ArrayList<FilmLanguageDB>();
		
		FilmDB film = new FilmDB("Hallo Welt", 10, 1993);
		filmLanguageEntities.add(new FilmLanguageDB(film, language));
		
		language.setFilmLanguageEntities(filmLanguageEntities);
	}
	
	@Test
	public void testLanguageDB() {
		LanguageDB language2 = new LanguageDB();
		assertEquals(language2.getId(), 0);
		assertEquals(language2.getName(), null);
		assertEquals(language2.getFilmLanguageEntities(), null);
		
		LanguageDB language3 = new LanguageDB("German");
		assertEquals(language3.getId(), 0);
		assertEquals(language3.getName(), "German");
		assertEquals(language3.getFilmLanguageEntities(), null);
		
		LanguageDB language4 = new LanguageDB(99);
		assertEquals(language4.getId(), 99);
		assertEquals(language4.getName(), null);
		assertEquals(language4.getFilmLanguageEntities(), null);
	}
	@Test
	public void testToLanguage() {
		Language l = language.toLanguage();
		assertEquals(l.getName(), "German");
		assertEquals(l.getId(), 99);
	}
	
	@Test
	public void testGetId() {
		assertEquals(language.getId(), 99);
	}
	
	@Test
	public void testSetId() {
		language.setId(88);
		assertEquals(language.getId(), 88);
	}
	
	@Test
	public void testGetName() {
		assertEquals(language.getName(), "German");
	}
	
	@Test
	public void testSetName() {
		language.setName("English");
		assertEquals(language.getName(), "English");
	}
}
