
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;


public class FilterPresenterImpl implements FilterPresenter {

	private EventBus eventBus;
	private FilterView filterView;
	private FilmDataModel filmDataModel;
	private String mode;
	private FilmListServiceAsync filmListService;

	@Inject
	public FilterPresenterImpl(EventBus eventBus, final FilterView filterView, FilmDataModel filmDataModel, 
			FilmListServiceAsync filmListService) {
		this.filmListService = filmListService;
		this.eventBus = eventBus;
		this.filterView = filterView;
		this.filmDataModel = filmDataModel;
		bind();
		updateFilterFromView();
		setupMultiSelects();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(filterView.asWidget());
	}

	@Override
	public void bind() {
		filterView.setPresenter(this);
	}

	@Override
	public void onSearch() {
		updateFilterFromView();
		eventBus.fireEvent(new FilterAppliedEvent());
		updateAppliedFilterBox();
	}

	@Override
	public void onClear() {
		filterView.setName("");
		filterView.setLengthSlider(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH);
		filterView.setYearSlider(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR);
		filterView.setSelectedCountryOptions(null); //deselect all with parameter == null
		filterView.setSelectedLanguageOptions(null);
		filterView.setSelectedGenreOptions(null);
	}
	
	@Override
	public void setMode(String mode) {
		this.mode = mode;			// information about current mode is given to filterPresenter
		updateAppliedFilterBox();	// appliedFilterBox is updated according to mode
		filterView.setMode(mode); 	// countrySelect & yearSlider of filterView are setVisible according to mode
	}
	
	@Override
	public void setupMultiSelects()
	{
		//fill genre multiselect box with options
		filmListService.getGenreSelectOption(new AsyncCallback<List<SelectOption>>(){
			@Override
			public void onFailure(Throwable caught) {
				ClientLog.writeErr("Failed to get genre list...");
				
			}
			@Override
			public void onSuccess(List<SelectOption> result) {
				filterView.setGenreOptions(result);
			}
		});
		
		//fill country multiselect box with options
		filmListService.getCountrySelectOption(new AsyncCallback<List<SelectOption>>(){

			@Override
			public void onFailure(Throwable caught) {
				ClientLog.writeErr("Failed to get country list...");
				
			}

			@Override
			public void onSuccess(List<SelectOption> result) {
				filterView.setCountryOptions(result);
			}
			
		});
		
		//fill language multiselect box with options
		filmListService.getLanguageSelectOption(new AsyncCallback<List<SelectOption>>(){

			@Override
			public void onFailure(Throwable caught) {
				ClientLog.writeErr("Failed to get language list...");
				
			}

			@Override
			public void onSuccess(List<SelectOption> result) {
				filterView.setLanguageOptions(result);
			}
			
		});
	}

	/**
	Helper Method to update the applied FilterBox according to the currently set mode of the filterPresenter
	@author Nicolas Küchler
	@pre 	mode != null && filterView != null && filmDataModel != null & filmDataModel.getAppliedMapFilter !=null
	@post	filterView.setAppliedFilterBox according to mode
	 */
	public void updateAppliedFilterBox()
	{
		if (mode.equals(Tokens.MAP))
		{
			FilmFilter filter = filmDataModel.getAppliedMapFilter();
			List<String> list = convertFilmFilterToList(filter);
			filterView.setAppliedFilterBox(list);
		}
		else
		{
			filterView.setAppliedFilterBox(convertFilmFilterToList(filmDataModel.getAppliedFilter()));
		}
	}

	/**
	Helper method to take all the filter fields from the filterView and create two FilmFilter Object which are stored in the filmDataModel.
	@author Nicolas Küchler
	@pre	filterView != null && filterView filterFields != null
	@post	updated appliedFilters in filmDataModel && clear filterFields in filterView 
	 */
	public void updateFilterFromView()
	{
		FilmFilter currentFilter = new FilmFilter();

		//setting value to null if no name filter is applied 
		String name = filterView.getName();
		if (name.equals("") || name == null)
		{
			name = null;
		}
		currentFilter.setName(name);

		currentFilter.setLengthStart(filterView.getLengthStart());
		currentFilter.setLengthEnd(filterView.getLengthEnd());
		currentFilter.setYearStart(filterView.getYearStart());
		currentFilter.setYearEnd(filterView.getYearEnd());

		//setting value to null if no country filter is applied
		if (filterView.getSelectedCountryOptions() == null || filterView.getSelectedCountryOptions().size()==0)
		{
			currentFilter.setCountries(null);
			currentFilter.setCountryIds(null);
		}
		else
		{
			currentFilter.setCountryOptions(filterView.getSelectedCountryOptions());
		}

		//setting value to null if no genre filter is applied
		if (filterView.getSelectedGenreOptions() == null || filterView.getSelectedGenreOptions().size()==0)
		{
			currentFilter.setGenres(null);
			currentFilter.setGenreIds(null);
		}
		else
		{
			currentFilter.setGenreOptions(filterView.getSelectedGenreOptions());
		}

		//setting value to null if no language filter is applied
		if (filterView.getSelectedLanguageOptions() == null || filterView.getSelectedLanguageOptions().size()==0)
		{
			currentFilter.setLanguages(null);
			currentFilter.setLanguageIds(null);
		}
		else
		{
			currentFilter.setLanguageOptions(filterView.getSelectedLanguageOptions());
		}


		filmDataModel.setAppliedFilter(currentFilter);
		filmDataModel.setAppliedMapFilter(adjustedMapFilter(currentFilter));
	}

	//TODO Decide if not better part of class FilmFilter
	/**
	Helper method to convert a FilmFilter Object to a List<String> Object.
	@author Nicolas Küchler
	@pre- 
	@post -
	@return List<String> with the applied FilterFields in a List
	 */
	public List<String> convertFilmFilterToList(FilmFilter filter)
	{
		List<String> filterList = new ArrayList<String>();

		//name
		if(filter.getName()!= null && !filter.getName().equals(""))
		{
			filterList.add("Film Name = " + filter.getName());
		}

		//length
		filterList.add("Film Length = " +filter.getLengthStart() + "-" + filter.getLengthEnd());
		

		//year
		filterList.add("Production Year = " +filter.getYearStart() + "-" + filter.getYearEnd());
		

		//country
		for(int i = 0; filter.getCountries() != null && i < filter.getCountries().size(); i++)
		{
			filterList.add("Production Country = " + filter.getCountries().get(i));
		}
		

		//language
		for(int i = 0; filter.getLanguages() != null &&  i < filter.getLanguages().size(); i++)
		{
			filterList.add("Film Language = " + filter.getLanguages().get(i));
		}

		//genre
		for(int i = 0; filter.getGenres() != null && i < filter.getGenres().size(); i++)
		{
			filterList.add("Film Genre = " + filter.getGenres().get(i));
		}

		return filterList;
	}

	@Override
	public void setFilter(String filterToken) {
		// TODO FilterParsing from Token
		// Tbd if the method just fills out the filterviewform or if it starts the search for data automatically.
		// --> could have a combination decided by "autoSearch" in filterToken

	}
	
	/**
	
	@author Nicolas Küchler
	@pre
	@post
	
	@return
	 */ //TODO NK Test, Comments
	public String createUrlToken(FilmFilter filter, boolean autoSearch)
	{
		String token = "?sb=";
		
		//defines if the search is automatically started or if just the filterfields are filled
		if(autoSearch)
		{
			token += "1";
		}
		else
		{
			token += "0";
		}
		
		//Name --> need to encode 
		if(filter.getName()!=null)
		{
			token+= "&na="+ URL.encode(filter.getName());
		}
		
		//Length
		token+= "&le="+filter.getLengthStart()+"+"+filter.getLengthEnd();
		
		//Year
		token+= "&ye="+filter.getYearStart()+"+"+filter.getYearEnd();
		
		//Genre
		Set<Integer> genres = filter.getGenreIds();
		if(genres!=null && genres.size()>0)
		{
			Integer[] ids = new Integer[genres.size()];
			ids = genres.toArray(ids);
			token = "&ge="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += "+"+ids[i];
			}
		}
		
		//Language
		Set<Integer> languages = filter.getLanguageIds();
		if(languages!=null && languages.size()>0)
		{
			Integer[] ids = new Integer[languages.size()];
			ids = languages.toArray(ids);
			token = "&la="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += "+"+ids[i];
			}
		}
		
		//Country
		Set<Integer> countries = filter.getGenreIds();
		if(countries!=null && countries.size()>0)
		{
			Integer[] ids = new Integer[countries.size()];
			ids = countries.toArray(ids);
			token = "&co="+ids[0];
			for (int i = 1; i < ids.length; i++)
			{
				token += "+"+ids[i];
			}
		}

		return token;
	}
	
	/**
	TODO: NK Test and Comments. Probably better to put into a Utils class
	@author Nicolas Küchler
	@pre
	@post
	
	@return
	 */
	public FilmFilter parseFilter(String urlToken)
	{
		//TODO Define Exception Handling
		FilmFilter filter = new FilmFilter();
		String[] parts = urlToken.split("&");
		String fieldId ="";
		String value ="";
		for(String part : parts)
		{
			fieldId = part.substring(0, 3);
			value = part.substring(3);
			
			switch(fieldId){
			case "na":
				filter.setName(URL.decode(value));
				break;
				
			case "le":
				String[] length = value.split("+");
				filter.setLengthStart(Integer.parseInt(length[0]));
				filter.setLengthEnd(Integer.parseInt(length[1]));
				break;
				
			case "ye":
				String[] year = value.split("+");
				filter.setLengthStart(Integer.parseInt(year[0]));
				filter.setLengthEnd(Integer.parseInt(year[1]));
				break;
			
			case "ge":
				String[] genre = value.split("+");
				Set<Integer> genreIds = new HashSet<Integer>();
				for(String g : genre)
				{
					genreIds.add(Integer.parseInt(g));
				}
				filter.setGenreIds(genreIds);
				break;
				
			case "la":
				String[] language = value.split("+");
				Set<Integer> languageIds = new HashSet<Integer>();
				for(String l : language)
				{
					languageIds.add(Integer.parseInt(l));
				}
				filter.setLanguageIds(languageIds);
				break;
				
			case "co":
				String[] country = value.split("+");
				Set<Integer> countryIds = new HashSet<Integer>();
				for(String c : country)
				{
					countryIds.add(Integer.parseInt(c));
				}
				filter.setLanguageIds(countryIds);
				break;
			}
		}
		return filter;
	}

	/**
	Helper method to adjust the currently appliedFilter for the map. Because the filtering of the year 
	for the map is done on clientside and the country filter doesn't apply in the map
	@author Nicolas Küchler
	@pre 	filmDataModel != null && filmDataModel.getAppliedFilter()!=null
	@post	filmDataModel.getAppliedFilter() == filmDataModel.getAppliedFilter() @pre
	@return FilmFilter that contains the boundaries for the years (because filtering of year 
			is done on clientside for the map) and the filter for the countries is removed.
	 */
	public FilmFilter adjustedMapFilter(FilmFilter appliedFilter)
	{
		//taking applied filter from filmDataModel
		FilmFilter filter = new FilmFilter();

		//copying the filter fields that are considered for the map as well
		filter.setName(appliedFilter.getName());
		filter.setLengthStart(appliedFilter.getLengthStart());
		filter.setLengthEnd(appliedFilter.getLengthEnd());
		filter.setGenres(appliedFilter.getGenres());
		filter.setGenreIds(appliedFilter.getGenreIds());
		filter.setLanguages(appliedFilter.getLanguages());
		filter.setLanguageIds(appliedFilter.getLanguageIds());

		//adjusting year range because filtering of that is done in the map on client side
		filter.setYearStart(Boundaries.MIN_YEAR);
		filter.setYearEnd(Boundaries.MAX_YEAR);

		//removing the country filter because in the map always all the countries should be considered
		filter.setCountries(null);
		filter.setCountryIds(null);

		return filter;
	}


}
