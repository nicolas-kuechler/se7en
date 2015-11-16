package ch.uzh.se.se7en.client.mvp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;


public class FilmDataModelImpl implements FilmDataModel{
	
	private List<Country> countries;		//used to adjust GeoChart with YearRangeSlider
	private FilmFilter appliedFilter;
	private FilmFilter appliedMapFilter;

	private HashMap<Integer,String> genreOptions;
	private HashMap<Integer,String> countryOptions;
	private HashMap<Integer,String> languageOptions;


	@Inject
	public FilmDataModelImpl()
	{	
		countries = new ArrayList<Country>();
		appliedFilter = new FilmFilter();
		appliedMapFilter = new FilmFilter();
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

	@Override
	public void setCountryOptions(HashMap<Integer,String> selectOptions) {
		this.countryOptions = selectOptions;
	}

	@Override
	public String getCountryName(int id) {
		if (countryOptions == null) //countryOptions not set yet
		{
			return null;
		}
		else
		{
			return countryOptions.get(id); //get Name according to id
		}
	}

	@Override
	public void setGenreOptions(HashMap<Integer,String> selectOptions) {
		this.genreOptions = selectOptions; 

	}

	@Override
	public String getGenreName(int id) {
		if (genreOptions == null) //genreNames not set yet)
		{
			return null;
		}
		else
		{
			return genreOptions.get(id); //get Name according to id
		}
	}
	@Override
	public void setLanguageOptions(HashMap<Integer,String> selectOptions) {
		this.languageOptions = selectOptions;
	}

	@Override
	public String getLanguageName(int id) {
		if (languageOptions == null) //languageOptions not set yet
		{
			return null;
		}
		else
		{
			return languageOptions.get(id); //get Name according to id
		}
	}



}
