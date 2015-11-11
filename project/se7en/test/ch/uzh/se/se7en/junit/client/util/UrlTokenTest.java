package ch.uzh.se.se7en.junit.client.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.google.gwt.http.client.URL;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.presenters.impl.util.UrlToken;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;

@RunWith(JukitoRunner.class)
public class UrlTokenTest {

	FilmFilter filter;
	
	
	@Before 
	public void setup(){
		//demo values used to test
		String testName = "TestFilm";
		int testLengthStart = 10;
		int testLengthEnd = 300;
		int testYearStart = 1980;
		int testYearEnd = 2010;
		int testMinYear = 1890;
		int testMaxYear = 2015;

		// multiple entries from multiselect are selected
		Set<Integer> selectedCountry = new HashSet<Integer>();
		selectedCountry.add(1);
		selectedCountry.add(2);

		// only one entry from multiselect is selected
		Set<Integer> selectedLanguage = new HashSet<Integer>();
		selectedLanguage.add(1);

		// no entry from multiselect is selected
		Set<Integer> selectedGenre = new HashSet<Integer>();

		filter = new FilmFilter();
		filter.setName(testName);
		filter.setLengthStart(testLengthStart);
		filter.setLengthEnd(testLengthEnd);
		filter.setYearStart(testYearStart);
		filter.setYearEnd(testYearEnd);
		filter.setCountryIds(selectedCountry);
		filter.setLanguageIds(selectedLanguage);
		filter.setGenreIds(null);
	}
	
	@Test
	public void testCreateUrlToken() {
		String resultToken = UrlToken.createUrlToken(filter, true);
		String expected = "?sb=1&na=TestFilm&le=10:300&ye=1980:2010&la=1&co=1:2";
		
		assertEquals(expected, resultToken);
		FilmFilter parsedFilter = UrlToken.parseFilter(resultToken);
		
		assertEquals(parsedFilter, filter);
	}
	
	@Test
	public void testparseFilter() {
		
		String token = "?sb=1&na=TestFilm&le=10:300&ye=1980:2010&la=1&co=1:2";
		FilmFilter parsedFilter = UrlToken.parseFilter(token);
		
		String resultToken = UrlToken.createUrlToken(parsedFilter, true);
		
		assertEquals(parsedFilter, filter);
		assertEquals(token, resultToken);
	}
	
	

}
