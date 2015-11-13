package ch.uzh.se.se7en.client.mvp.model;

import java.util.List;


import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;

public interface FilmDataModel {
	//TODO NK Commments missing
	public void setCountryList(List<Country> countries);
	public List<Country> getCountryList();
	
	public void setCountryDataTable(List<DataTableEntity> countries);
	public List<DataTableEntity> getCountryDataTable();
	
	public void setFilmList(List<Film> films);
	public List<Film> getFilmList();
	
	public void setAppliedFilter(FilmFilter filter);
	public FilmFilter getAppliedFilter();
	
	public void setAppliedMapFilter(FilmFilter filter);
	public FilmFilter getAppliedMapFilter();
	
	public void setCountryOptions(List<SelectOption> selectOptions);
	public String getCountryName(int id);
	
	public void setGenreOptions(List<SelectOption> selectOptions);
	public String getGenreName(int id);
	
	public void setLanguageOptions(List<SelectOption> selectOptions);
	public String getLanguageName(int id);
}
