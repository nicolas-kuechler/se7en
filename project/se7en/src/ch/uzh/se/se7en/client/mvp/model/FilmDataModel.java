package ch.uzh.se.se7en.client.mvp.model;

import java.util.List;

import com.googlecode.gwt.charts.client.DataTable;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public interface FilmDataModel {
	//TODO Commments missing
	public void setCountryList(List<Country> countries);
	public List<Country> getCountryList();
	
	public void setCountryDataTable(DataTable countries);
	public DataTable getCountryDataTable();
	
	public void setFilmList(List<Film> films);
	public List<Film> getFilmList();
	
	public void setAppliedFilter(FilmFilter filter);
	public FilmFilter getAppliedFilter();
	
	public void setAppliedMapFilter(FilmFilter filter);
	public FilmFilter getAppliedMapFilter();
}
