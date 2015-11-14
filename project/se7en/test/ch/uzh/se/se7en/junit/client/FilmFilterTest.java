package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.dev.util.collect.HashSet;

import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;

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
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		countries.add("Switzerland");
		languages.add("German");
		genres.add("Action");
		FilmFilter filter = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		assertEquals(filter.getName(),"Test");
		assertEquals(filter.getLengthStart(),1);
		assertEquals(filter.getLengthEnd(),100);
		assertEquals(filter.getYearStart(),2000);
		assertEquals(filter.getYearEnd(),2004);
		assertEquals(filter.getCountries(),countries);
		assertEquals(filter.getLanguages(),languages);
		assertEquals(filter.getGenres(),genres);
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
	public void testGetCountries() {
		FilmFilter filter = new FilmFilter();
		List<String> countries = new ArrayList<String>();
		countries.add("Switzerland");
		filter.setCountries(countries);
		assertEquals(filter.getCountries(),countries);
	}

	@Test
	public void testSetCountries() {
		FilmFilter filter = new FilmFilter();
		List<String> countries = new ArrayList<String>();
		countries.add("Switzerland");
		filter.setCountries(countries);
		assertEquals(filter.getCountries(),countries);
	}

	@Test
	public void testSetCountryOptions() {
		FilmFilter filter = new FilmFilter();
		SelectOption select1 = new SelectOption(1,"Switzerland");
		List<SelectOption> options = new ArrayList<SelectOption>();
		options.add(select1);
		
		filter.setCountryOptions(options);
		String getOptionName = options.get(0).getName();
		String getCountryString = "";
		for(String str :filter.getCountries()){
			getCountryString = getCountryString + str;
		}
		
		assertEquals(getCountryString,getOptionName);
	}

	@Test
	public void testSetGenreOptions() {
		FilmFilter filter = new FilmFilter();
		SelectOption select1 = new SelectOption(1,"Action");
		List<SelectOption> options = new ArrayList<SelectOption>();
		options.add(select1);
		
		filter.setGenreOptions(options);
		String getOptionName = options.get(0).getName();
		String getGenreString = "";
		for(String str :filter.getGenres()){
			getGenreString = getGenreString + str;
		}
		
		assertEquals(getGenreString,getOptionName);
	}

	@Test
	public void testSetLanguageOptions() {
		FilmFilter filter = new FilmFilter();
		SelectOption select1 = new SelectOption(1,"German");
		List<SelectOption> options = new ArrayList<SelectOption>();
		options.add(select1);
		
		filter.setLanguageOptions(options);
		String getOptionName = options.get(0).getName();
		String getLanguageString = "";
		for(String str :filter.getLanguages()){
			getLanguageString = getLanguageString + str;
		}
		
		assertEquals(getLanguageString,getOptionName);
	}

	@Test
	public void testGetLanguages() {
		FilmFilter filter = new FilmFilter();
		List<String> languages = new ArrayList<String>();
		languages.add("German");
		filter.setLanguages(languages);
		assertEquals(filter.getLanguages(),languages);
	}

	@Test
	public void testSetLanguages() {
		FilmFilter filter = new FilmFilter();
		List<String> languages = new ArrayList<String>();
		languages.add("German");
		filter.setLanguages(languages);
		assertEquals(filter.getLanguages(),languages);
	}

	@Test
	public void testGetGenres() {
		FilmFilter filter = new FilmFilter();
		List<String> genres = new ArrayList<String>();
		genres.add("Action");
		filter.setGenres(genres);
		assertEquals(filter.getGenres(),genres);
	}

	@Test
	public void testSetGenres() {
		FilmFilter filter = new FilmFilter();
		List<String> genres = new ArrayList<String>();
		genres.add("Action");
		filter.setGenres(genres);
		assertEquals(filter.getGenres(),genres);
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
		List<String> countries = new ArrayList<String>();
		List<String> languages = new ArrayList<String>();
		List<String> genres = new ArrayList<String>();
		countries.add("Switzerland");
		languages.add("German");
		genres.add("Action");
		FilmFilter filter = new FilmFilter("Test",1,100,2000,2004,countries,languages,genres);
		assertEquals(filter.toString(),"FilmFilter [name=Test, lengthStart=1, lengthEnd=100, yearStart=2000, yearEnd=2004, countries=[Switzerland], countryIds=null,"
				+ " languages=[German], languageIds=null, genres=[Action], genreIds=null]");
		
		
;
	}

}
