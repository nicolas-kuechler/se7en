package ch.uzh.se.se7en.junit.shared.model;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import ch.uzh.se.se7en.shared.model.FilterOptions;

public class FilterOptionsTest {

	@Test
	public void getSetGenreSelectOptionsTest() {
		
		HashMap<Integer,String> testMap = new HashMap<Integer,String>();
		testMap.put(1, "test1");
		testMap.put(2, "test2");
		
		FilterOptions filterOptions = new FilterOptions();
		
		filterOptions.setGenreSelectOptions(testMap);
		assertTrue(filterOptions.getGenreSelectOptions().equals(testMap));
	}
	
	@Test
	public void getSetLanguageSelectOptionsTest() {
		
		HashMap<Integer,String> testMap = new HashMap<Integer,String>();
		testMap.put(1, "test1");
		testMap.put(2, "test2");
		
		FilterOptions filterOptions = new FilterOptions();
		
		filterOptions.setLanguageSelectOptions(testMap);
		assertTrue(filterOptions.getLanguageSelectOptions().equals(testMap));
	}
	
	@Test
	public void getSetCountrySelectOptionsTest() {
		
		HashMap<Integer,String> testMap = new HashMap<Integer,String>();
		testMap.put(1, "test1");
		testMap.put(2, "test2");
		
		FilterOptions filterOptions = new FilterOptions();
		
		filterOptions.setCountrySelectOptions(testMap);
		assertTrue(filterOptions.getCountrySelectOptions().equals(testMap));
	}

}
