package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import org.junit.Test;


import ch.uzh.se.se7en.shared.model.Language;

public class LanguageTest {

	@Test
	public void testLanguage() {
		Language language = new Language();
		assertEquals(language.getName(),null);
	}

	@Test
	public void testLanguageIntStringInt() {
		Language language = new Language(10,"Switzerland",5);
		assertEquals(10,language.getId());
		assertEquals("Switzerland",language.getName());
	}

	@Test
	public void testToString() {
		Language language = new Language(10,"Switzerland",5);
		assertEquals("Id: 10 - Name: Switzerland - Anzahl Filme: 5",language.toString());
	}

	@Test
	public void testGetId() {
		Language language = new Language();
		language.setId(10);
		assertEquals(language.getId(),10);
	}

	@Test
	public void testSetId() {
		Language language = new Language();
		language.setId(10);
		assertEquals(language.getId(),10);
	}

	@Test
	public void testGetName() {
		Language language = new Language();
		language.setName("Action");
		assertEquals(language.getName(),"Action");
	}

	@Test
	public void testSetName() {
		Language language = new Language();
		language.setName("Action");
		assertEquals(language.getName(),"Action");
	}

}
