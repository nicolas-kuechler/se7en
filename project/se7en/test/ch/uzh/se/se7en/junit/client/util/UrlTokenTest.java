package ch.uzh.se.se7en.junit.client.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.uzh.se.se7en.client.mvp.presenters.impl.util.BrowserUtil;
import ch.uzh.se.se7en.client.mvp.presenters.impl.util.UrlToken;
import ch.uzh.se.se7en.shared.model.FilmFilter;

@RunWith(MockitoJUnitRunner.class)
public class UrlTokenTest {

	FilmFilter filter;
	
	@Mock
	BrowserUtil browserUtil;
	
	UrlToken urlToken;
	
	
	@Before 
	public void setup(){
		//demo values used to test
		String testName = "TestFilm";
		int testLengthStart = 10;
		int testLengthEnd = 300;
		int testYearStart = 1980;
		int testYearEnd = 2010;

		// multiple entries from multiselect are selected
		Set<Integer> selectedCountry = new HashSet<Integer>();
		selectedCountry.add(1);
		selectedCountry.add(2);

		// only one entry from multiselect is selected
		Set<Integer> selectedLanguage = new HashSet<Integer>();
		selectedLanguage.add(1);


		filter = new FilmFilter();
		filter.setName(testName);
		filter.setLengthStart(testLengthStart);
		filter.setLengthEnd(testLengthEnd);
		filter.setYearStart(testYearStart);
		filter.setYearEnd(testYearEnd);
		filter.setCountryIds(selectedCountry);
		filter.setLanguageIds(selectedLanguage);
		filter.setGenreIds(null);
		
		
		when(browserUtil.encode(Matchers.anyString())).then(returnsFirstArg());
		when(browserUtil.decode(Matchers.anyString())).then(returnsFirstArg());
		
		urlToken  = new UrlToken(browserUtil);
	}
	
	@Test
	public void testCreateUrlToken() {
		String resultToken = urlToken.createUrlToken(filter, true);
		String expected = "?sb=1&na=TestFilm&le=10:300&ye=1980:2010&la=1&co=1:2";
		
		assertEquals(expected, resultToken);
		FilmFilter parsedFilter = urlToken.parseFilter(resultToken);
		
		assertEquals(parsedFilter, filter);
	}
	
	@Test
	public void testparseFilter() {
		String token = "?sb=1&na=TestFilm&le=10:300&ye=1980:2010&la=1&co=1:2";
		FilmFilter parsedFilter = urlToken.parseFilter(token);
		
		String resultToken = urlToken.createUrlToken(parsedFilter, true);
		
		assertEquals(parsedFilter, filter);
		assertEquals(token, resultToken);
	}
}
