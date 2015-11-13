package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModelImpl;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public class FilmDataModelImplTest {

	@Test
	public void testFilmDataModelImpl() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Film>films = new ArrayList<Film>();	
		assertEquals(model.getFilmList(),films);
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
	public void testSetCountryDataTable() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<DataTableEntity> tableEntityList = new ArrayList<DataTableEntity>();
		DataTableEntity tableEntity = new DataTableEntity("Switzerland",30);
		tableEntityList.add(tableEntity);
		model.setCountryDataTable(tableEntityList);
		assertEquals(model.getCountryDataTable(),tableEntityList);
	}

	@Test
	public void testGetCountryDataTable() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<DataTableEntity> tableEntityList = new ArrayList<DataTableEntity>();
		DataTableEntity tableEntity = new DataTableEntity("Switzerland",30);
		tableEntityList.add(tableEntity);
		model.setCountryDataTable(tableEntityList);
		assertEquals(model.getCountryDataTable(),tableEntityList);
	}

	@Test
	public void testSetFilmList() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Film> films = new ArrayList<Film>();
		Film film = new Film("Test");
		films.add(film);
		model.setFilmList(films);
		assertEquals(model.getFilmList(),films);
	}

	@Test
	public void testGetFilmList() {
		FilmDataModelImpl model = new FilmDataModelImpl();
		List<Film> films = new ArrayList<Film>();
		Film film = new Film("Test");
		films.add(film);
		model.setFilmList(films);
		assertEquals(model.getFilmList(),films);
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

}
