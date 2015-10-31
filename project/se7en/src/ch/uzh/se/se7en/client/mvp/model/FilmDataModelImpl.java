package ch.uzh.se.se7en.client.mvp.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.DataTable;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;


public class FilmDataModelImpl implements FilmDataModel{
	
	private List<Film> films;
	private DataTable countryDataTable;		//used for GeoChart
	private List<Country> countries;		//used to adjust GeoChart with YearRangeSlider
	private FilmFilter appliedFilter;
	private FilmFilter appliedMapFilter;

	
	@Inject
	public FilmDataModelImpl()
	{
		films = new ArrayList<Film>();		
		countries = new ArrayList<Country>();
		appliedFilter = new FilmFilter();
	}
	@Override
	public void setCountryList(List<Country> countries) {
		this.countries = countries;
		
	}
	@Override
	public List<Country> getCountryList() {
		return countries;
	}
	@Override
	public void setCountryDataTable(DataTable countries) {
		this.countryDataTable = countries;
		
	}
	@Override
	public DataTable getCountryDataTable() {
		return countryDataTable;
	}
	@Override
	public void setFilmList(List<Film> films) {
		this.films = films;
	}
	@Override
	public List<Film> getFilmList() {
		return films;
	}
	@Override
	public void setAppliedFilter(FilmFilter filter) {
		this.appliedFilter=filter;
		
	}
	@Override
	public FilmFilter getAppliedFilter() {
		return appliedFilter;
	}
	@Override
	public void setAppliedMapFilter(FilmFilter filter) {
		this.appliedMapFilter=filter;
		
	}
	@Override
	public FilmFilter getAppliedMapFilter() {
		return appliedMapFilter;
	}

}
