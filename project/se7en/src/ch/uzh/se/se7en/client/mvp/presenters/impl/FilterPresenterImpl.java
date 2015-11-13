
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.presenters.impl.util.UrlToken;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;


public class FilterPresenterImpl implements FilterPresenter {

	private EventBus eventBus;
	private FilterView filterView;
	private FilmDataModel filmDataModel;
	private String mode ="";
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
		
		//TODO NK adjust test
		String filterToken = "";
		if(mode.equals(Tokens.MAP))
		{
			filterToken = Tokens.MAP + UrlToken.createUrlToken(filmDataModel.getAppliedMapFilter(), false);
		}
		else if(mode.equals(Tokens.TABLE))
		{
			filterToken = Tokens.TABLE + UrlToken.createUrlToken(filmDataModel.getAppliedFilter(), false);
		}
		History.newItem(filterToken, false);
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
				filmDataModel.setGenreOptions(result);
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
				filmDataModel.setCountryOptions(result);
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
				filmDataModel.setLanguageOptions(result);
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
		if (filterView.getSelectedCountryIds() == null || filterView.getSelectedCountryIds().size()==0)
		{
			currentFilter.setCountryIds(null);
		}
		else
		{
			currentFilter.setCountryIds(filterView.getSelectedCountryIds());
		}

		//setting value to null if no genre filter is applied
		if (filterView.getSelectedGenreIds() == null || filterView.getSelectedGenreIds().size()==0)
		{
			currentFilter.setGenreIds(null);
		}
		else
		{
			currentFilter.setGenreIds(filterView.getSelectedGenreIds());
		}

		//setting value to null if no language filter is applied
		if (filterView.getSelectedLanguageIds() == null || filterView.getSelectedLanguageIds().size()==0)
		{
			currentFilter.setLanguageIds(null);
		}
		else
		{
			currentFilter.setLanguageIds(filterView.getSelectedLanguageIds());
		}


		filmDataModel.setAppliedFilter(currentFilter);
		filmDataModel.setAppliedMapFilter(adjustedMapFilter(currentFilter));
	}

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

		//length (if length is at boundary values, then it is not converted)
		if(filter.getLengthStart()==Boundaries.MIN_LENGTH && filter.getLengthEnd()==Boundaries.MAX_LENGTH)
		{
			filterList.add("Film Length = " +filter.getLengthStart() + "-" + filter.getLengthEnd());
		}
		

		//year (if year is at boundary values, then it is not converted)
		if(filter.getYearStart()==Boundaries.MIN_YEAR && filter.getYearEnd()==Boundaries.MAX_YEAR)
		{
			filterList.add("Production Year = " +filter.getYearStart() + "-" + filter.getYearEnd());
		}

		//country
		//TODO NK Check if problem when filter.getCountryIds() == null
		Set<Integer> countries = filter.getCountryIds();
		for(Integer id : countries)
		{
			filterList.add("Production Country = " + filmDataModel.getCountryName(id));
		}
		
		//language
		//TODO NK Check if problem when filter.getLanguageIds() == null	
		Set<Integer> languages = filter.getLanguageIds();
		for(Integer id : languages)
		{
			filterList.add("Film Language = " + filmDataModel.getLanguageName(id));
		}

		//genre
		//TODO NK Check if problem when filter.getGenreIds() == null	
		Set<Integer> genres = filter.getGenreIds();
		for(Integer id : genres)
		{
			filterList.add("Film Genre = " + filmDataModel.getGenreName(id));
		}

		return filterList;
	}

	@Override //TODO NK Test and Comments
	public void setFilter(String filterToken) {
		if(filterToken.equals("")) //Url doesn't contain filter information (option1 Tab Change, optio2 No filter set)
		{
			String historyToken;
			//Tab Change or Without Filter
			if(mode.equals(Tokens.MAP))
			{
				historyToken = Tokens.MAP + UrlToken.createUrlToken(filmDataModel.getAppliedMapFilter(), false);
			}
			else if(mode.equals(Tokens.TABLE))
			{
				historyToken = Tokens.TABLE + UrlToken.createUrlToken(filmDataModel.getAppliedFilter(), false);
			}
			else
			{
				ClientLog.writeErr("Filter setMode() was not called before setFilter() or unknown mode.");
				historyToken = History.getToken();
			}
			History.replaceItem(historyToken, false);
		}
		else //Url contains Filter information
		{		
			//Parse a filter object from the token
			FilmFilter filter = UrlToken.parseFilter(filterToken); //TODO NK Define ExceptionHandling
			
			//fill filterFields in view
			updateFilterFieldsInView(filter);
			
			//If AutoSearch flag is set, start search
			if(filterToken.startsWith("?sb=1"))
			{
				onSearch();
			}
		}
	}
	
	//TODO NK Comment & Test
	public void updateFilterFieldsInView(FilmFilter filter)
	{
		onClear(); //make sure all fields are set to default first
		if(filter.getName()!=null)
		{
			filterView.setName(filter.getName());
		}
		
		filterView.setLengthSlider(filter.getLengthStart(), filter.getLengthEnd());
		filterView.setYearSlider(filter.getYearStart(), filter.getYearEnd());
		
		//if filter.getXYZIds() == null then all options are deselected
		filterView.setSelectedCountryOptions(filter.getCountryIds()); 
		filterView.setSelectedLanguageOptions(filter.getLanguageIds());
		filterView.setSelectedGenreOptions(filter.getGenreIds());
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
		filter.setGenreIds(appliedFilter.getGenreIds());
		filter.setLanguageIds(appliedFilter.getLanguageIds());

		//adjusting year range because filtering of that is done in the map on client side
		filter.setYearStart(Boundaries.MIN_YEAR);
		filter.setYearEnd(Boundaries.MAX_YEAR);

		//removing the country filter because in the map always all the countries should be considered
		filter.setCountryIds(null);

		return filter;
	}
}
