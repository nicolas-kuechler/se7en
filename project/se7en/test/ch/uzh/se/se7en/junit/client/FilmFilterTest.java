package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Set;

import org.junit.Test;

import com.google.gwt.dev.util.collect.HashSet;

import ch.uzh.se.se7en.shared.model.FilmFilter;

public class FilmFilterTest {

	@Test
	public void testFilmFilter() {
		FilmFilter filter = new FilmFilter();
		assertEquals(filter.getName(),null);
	}

	@Test
	public void testFilmFilterString() {
		FilmFilter filter = new FilmFilter("Test");
		assertEquals(filter.getName(),"Test");
	}
	
	@Test
	public void testFilmFilterStringIntIntIntIntListOfStringListOfStringListOfString() {
		
		Set<Integer> countries = new HashSet<Integer>();
		Set<Integer> languages = new HashSet<Integer>();
		Set<Integer> genres = new HashSet<Integer>();
		
		countries.add(1);
		countries.add(2);
		languages.add(5);
		genres.add(4);
		
		FilmFilter filter = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		assertEquals(filter.getName(),"Test");
		assertEquals(filter.getLengthStart(),1);
		assertEquals(filter.getLengthEnd(),100);
		assertEquals(filter.getYearStart(),2000);
		assertEquals(filter.getYearEnd(),2004);
		assertEquals(filter.getCountryIds(),countries);
		assertEquals(filter.getLanguageIds(),languages);
		assertEquals(filter.getGenreIds(),genres);
	}

	@Test
	public void testGetName() {
		FilmFilter filter = new FilmFilter();
		filter.setName("Test");
		assertEquals(filter.getName(),"Test");
	}

	@Test
	public void testSetName() {
		FilmFilter filter = new FilmFilter();
		filter.setName("Test");
		assertEquals(filter.getName(),"Test");
	}

	@Test
	public void testGetLengthStart() {
		FilmFilter filter = new FilmFilter();
		filter.setLengthStart(1);
		assertEquals(filter.getLengthStart(),1);
	}

	@Test
	public void testSetLengthStart() {
		FilmFilter filter = new FilmFilter();
		filter.setLengthStart(1);
		assertEquals(filter.getLengthStart(),1);
	}

	@Test
	public void testGetLengthEnd() {
		FilmFilter filter = new FilmFilter();
		filter.setLengthEnd(100);
		assertEquals(filter.getLengthEnd(),100);
	}

	@Test
	public void testSetLengthEnd() {
		FilmFilter filter = new FilmFilter();
		filter.setLengthEnd(100);
		assertEquals(filter.getLengthEnd(),100);
	}

	@Test
	public void testGetYearStart() {
		FilmFilter filter = new FilmFilter();
		filter.setYearStart(2000);
		assertEquals(filter.getYearStart(),2000);
	}

	@Test
	public void testSetYearStart() {
		FilmFilter filter = new FilmFilter();
		filter.setYearStart(2000);
		assertEquals(filter.getYearStart(),2000);
	}

	@Test
	public void testGetYearEnd() {
		FilmFilter filter = new FilmFilter();
		filter.setYearEnd(2004);
		assertEquals(filter.getYearEnd(),2004);
	}

	@Test
	public void testSetYearEnd() {
		FilmFilter filter = new FilmFilter();
		filter.setYearEnd(2004);
		assertEquals(filter.getYearEnd(),2004);
	}


	@Test
	public void testGetCountryIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setCountryIds(set);
		assertEquals(filter.getCountryIds(),set);
	}

	@Test
	public void testSetCountryIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setCountryIds(set);
		assertEquals(filter.getCountryIds(),set);
	}

	@Test
	public void testGetLanguageIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setLanguageIds(set);
		assertEquals(filter.getLanguageIds(),set);
	}

	@Test
	public void testSetLanguageIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setLanguageIds(set);
		assertEquals(filter.getLanguageIds(),set);
	}

	@Test
	public void testGetGenreIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setGenreIds(set);
		assertEquals(filter.getGenreIds(),set);
	}

	@Test
	public void testSetGenreIds() {
		FilmFilter filter = new FilmFilter();
		Integer value1 = 1;
		Integer value2 = 2;
		Set<Integer> set = new HashSet<Integer>();
		set.add(value1);
		set.add(value2);
		
		filter.setGenreIds(set);
		assertEquals(filter.getGenreIds(),set);
	}

	@Test
	public void testToString() {
		Set<Integer> countries = new HashSet<Integer>();
		Set<Integer> languages = new HashSet<Integer>();
		Set<Integer> genres = new HashSet<Integer>();
		
		countries.add(1);
		countries.add(2);
		languages.add(5);
		genres.add(4);
		
		FilmFilter filter = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		assertEquals(filter.toString(),"FilmFilter [name=Test, lengthStart=1, lengthEnd=100, yearStart=2000, yearEnd=2004, countryIds=[2, 1],"
				+ " languageIds=[5], genreIds=[4]]");
	}
	
	@Test
	public void testEquals()
	{
		Set<Integer> countries = new HashSet<Integer>();
		Set<Integer> languages = new HashSet<Integer>();
		Set<Integer> genres = new HashSet<Integer>();
		
		countries.add(1);
		countries.add(2);
		languages.add(5);
		genres.add(4);
		
		FilmFilter filter1 = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		FilmFilter filter2 = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		FilmFilter filter3 = new FilmFilter("Test",1,400,2000,2004,countries,languages,genres);
		
		assertEquals(filter1, filter2);
		assertThat(filter1, not(filter3));
	}

}
