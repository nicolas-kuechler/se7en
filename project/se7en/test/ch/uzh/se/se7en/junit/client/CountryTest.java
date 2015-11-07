package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.se.se7en.shared.model.Country;

public class CountryTest {

	@Test
	public void testCountry() {
		Country country = new Country();
		assertEquals(country.getName(),null);
	}

	@Test
	public void testCountryString() {
		Country country = new Country("TestCountry");
		assertEquals(country.getName(),"TestCountry");
	}

	@Test
	public void testCountryStringString() {
		Country country = new Country("TestCountry","Test1234");
		assertEquals(country.getName(),"TestCountry");
	}

	@Test
	public void testCountryIntStringString() {
		Country country = new Country(1,"TestCountry","Test1234");
		assertEquals(country.getId(),1);
	}

	@Test
	public void testToString() {
		Country country = new Country(1,"TestCountry","Test1234");
		String countryString = country.toString();
		assertEquals("Id: 1 - Name: TestCountry - Code: Test1234 - Anzahl Filme: null",countryString);
		
	}

	@Test
	public void testGetNumberOfFilms() {
		Country country = new Country(1,"TestCountry","Test1234");
		int sum = country.getNumberOfFilms(0,0);
		assertEquals(sum,0);
	}

	@Test
	public void testSetNumberOfFilms() {
		Country country = new Country(1,"TestCountry","Test1234");
		//country.setNumberOfFilms();
		assertEquals(200,country.getNumberOfFilms(1889,1890));
	}

	@Test
	public void testGetId() {
		Country country = new Country(1,"TestCountry","TEST1234");
		assertEquals(1,country.getId());
	}

	@Test
	public void testSetId() {
		Country country = new Country();
		country.setId(1);
		assertEquals(1,country.getId());
	}

	@Test
	public void testGetName() {
		Country country = new Country(1,"TestCountry","TEST1234");
		assertEquals("TestCountry",country.getName());
	}

	@Test
	public void testSetName() {
		Country country = new Country();
		country.setName("TestCountry");
		assertEquals("TestCountry",country.getName());
	}

	@Test
	public void testGetCode() {
		Country country = new Country(1,"TestCountry","TEST1234");
		assertEquals("TEST1234",country.getCode());
	}

	@Test
	public void testSetCode() {
		Country country = new Country();
		country.setCode("TEST1234");
		assertEquals("TEST1234",country.getCode());
	}

}
