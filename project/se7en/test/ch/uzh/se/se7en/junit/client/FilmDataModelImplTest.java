package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModelImpl;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public class FilmDataModelImplTest {
//TODO NK Write test for id name matching components (country, genre, language)
	
	@Test
	public void testFilmDataModelImpl() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Country>films = new ArrayList<Country>();	
		assertEquals(model.getCountryList(),films);
	}

	@Test
	public void testSetCountryList() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Country> countries = new ArrayList<Country>();
		Country country = new Country("Switzerland");
		countries.add(country);
		model.setCountryList(countries);
		assertEquals(model.getCountryList(),countries);
	}

	@Test
	public void testGetCountryList() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Country> countries = new ArrayList<Country>();
		Country country = new Country("Switzerland");
		countries.add(country);
		model.setCountryList(countries);
		assertEquals(model.getCountryList(),countries);
	}

	@Test
	public void testSetAppliedFilter() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		FilmFilter filter = new FilmFilter("Switzerland");
		model.setAppliedFilter(filter);
		assertEquals(model.getAppliedFilter(),filter);
	}

	@Test
	public void testGetAppliedFilter() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		FilmFilter filter = new FilmFilter("Switzerland");
		model.setAppliedFilter(filter);
		assertEquals(model.getAppliedFilter(),filter);
	}

	@Test
	public void testSetAppliedMapFilter() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		FilmFilter filter = new FilmFilter("Switzerland");
		model.setAppliedFilter(filter);
		assertEquals(model.getAppliedFilter(),filter);
	}

	@Test
	public void testGetAppliedMapFilter() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		FilmFilter filter = new FilmFilter("Switzerland");
		model.setAppliedFilter(filter);
		assertEquals(model.getAppliedFilter(),filter);
	}
	
	
	@Test
	public void testSetGetCountryOptions()
	{
		HashMap<Integer,String> options= new HashMap<Integer,String>();
		options.put(1, "Switzerland");
		options.put(2, "Germany");
		FilmDataModelImpl model = new FilmDataModelImpl();
		
		model.setCountryOptions(options);
		
		assertEquals(model.getCountryName(1), "Switzerland");
		assertEquals(model.getCountryName(2), "Germany");
		assertEquals(model.getCountryName(3), null);
		
		//Simulate that ids have a gap (some options were deleted in db)
		options.put(5, "France");
		model.setCountryOptions(options);
		assertEquals(model.getCountryName(1), "Switzerland");
		assertEquals(model.getCountryName(2), "Germany");
		assertEquals(model.getCountryName(3), null);
		assertEquals(model.getCountryName(4), null);
		assertEquals(model.getCountryName(5), "France");
		assertEquals(model.getCountryName(6), null);
	}
	
	@Test
	public void testSetGetGenreOptions()
	{
		HashMap<Integer,String> options= new HashMap<Integer,String>();
		options.put(1, "Action");
		options.put(2, "Adventure");
		FilmDataModelImpl model = new FilmDataModelImpl();
		
		model.setGenreOptions(options);
		
		assertEquals(model.getGenreName(1), "Action");
		assertEquals(model.getGenreName(2), "Adventure");
		assertEquals(model.getGenreName(3), null);
		
		//Simulate that ids have a gap (some options were deleted in db)
		options.put(6, "Comedy");
		model.setGenreOptions(options);
		assertEquals(model.getGenreName(1), "Action");
		assertEquals(model.getGenreName(2), "Adventure");
		assertEquals(model.getGenreName(3), null);
		assertEquals(model.getGenreName(4), null);
		assertEquals(model.getGenreName(5), null);
		assertEquals(model.getGenreName(6), "Comedy");
	}
	
	@Test
	public void testSetGetLanguageOptions()
	{
		HashMap<Integer,String> options= new HashMap<Integer,String>();
		options.put(1, "German");
		options.put(2, "English");
		FilmDataModelImpl model = new FilmDataModelImpl();
		
		model.setLanguageOptions(options);
		
		assertEquals(model.getLanguageName(1), "German");
		assertEquals(model.getLanguageName(2), "English");
		assertEquals(model.getLanguageName(3), null);
		
		//Simulate that ids have a gap (some options were deleted in db)
		options.put(5, "French");
		model.setLanguageOptions(options);
		assertEquals(model.getLanguageName(1), "German");
		assertEquals(model.getLanguageName(2), "English");
		assertEquals(model.getLanguageName(3), null);
		assertEquals(model.getLanguageName(4), null);
		assertEquals(model.getLanguageName(5), "French");
		assertEquals(model.getLanguageName(6), null);
	}
	


}
