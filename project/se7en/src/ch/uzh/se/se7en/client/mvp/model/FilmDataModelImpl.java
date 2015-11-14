package ch.uzh.se.se7en.client.mvp.model;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;


public class FilmDataModelImpl implements FilmDataModel{
	
	private List<Country> countries;		//used to adjust GeoChart with YearRangeSlider
	private FilmFilter appliedFilter;
	private FilmFilter appliedMapFilter;

	private String[] genreNames;
	private String[] countryNames;
	private String[] languageNames;


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
	public void setCountryOptions(List<SelectOption> selectOptions) {
		// Find maxValue to assert that gap in id's doesn't shut down application
		int maxId = getMaxId(selectOptions);

		// Initialize new String array with the size that allows each id = index-1
		countryNames = new String[maxId];

		//Loop through list and assign each option to the String Array at the id positions
		for(SelectOption opt : selectOptions)
		{
			countryNames[opt.getId()-1] = opt.getName();
		}
	}

	@Override
	public String getCountryName(int id) {
		if (countryNames == null || id>countryNames.length || id < 1) //countryNames not set yet || not valid id
		{
			return null;
		}
		else
		{
			return countryNames[id-1]; //get Name according to id
		}
	}

	@Override
	public void setGenreOptions(List<SelectOption> selectOptions) {
		// Find maxValue to assert that gap in id's doesn't shut down application
		int maxId = getMaxId(selectOptions);

		// Initialize new String array with the size that allows each id = index-1
		genreNames = new String[maxId];

		//Loop through list and assign each option to the String Array at the id positions
		for(SelectOption opt : selectOptions)
		{
			genreNames[opt.getId()-1] = opt.getName();
		}

	}

	@Override
	public String getGenreName(int id) {
		if (genreNames == null || id>genreNames.length || id < 1) //genreNames not set yet || not valid id)
		{
			return null;
		}
		else
		{
			return genreNames[id-1]; //get Name according to id
		}
	}
	@Override
	public void setLanguageOptions(List<SelectOption> selectOptions) {
		// Find maxValue to assert that gap in id's doesn't shut down application
		int maxId = getMaxId(selectOptions);

		// Initialize new String array with the size that allows each id = index-1
		languageNames = new String[maxId];

		//Loop through list and assign each option to the String Array at the id positions
		for(SelectOption opt : selectOptions)
		{
			languageNames[opt.getId()-1] = opt.getName();
		}
	}

	@Override
	public String getLanguageName(int id) {
		if (languageNames == null || id>languageNames.length || id < 1) //languageNames not set yet || not valid id) 
		{
			return null;
		}
		else
		{
			return languageNames[id-1]; //get Name according to id
		}
	}

	/**
	Finds the highest id within a list of selectOptions.
	@author Nicolas Küchler
	@pre 	-
	@post 	-
	@return highest Id within a list of selectOptions
	 */
	public int getMaxId(List<SelectOption> selectOptions)
	{
		int maxId = 0;
		for(SelectOption opt : selectOptions)
		{
			if(opt.getId()>maxId)
			{
				maxId = opt.getId();
			}
		}
		return maxId;
	}

}
