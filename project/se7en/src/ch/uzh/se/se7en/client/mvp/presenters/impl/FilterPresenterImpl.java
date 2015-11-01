
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;


public class FilterPresenterImpl implements FilterPresenter {

	private EventBus eventBus;
	private FilterView filterView;
	private FilmDataModel filmDataModel;
	private String mode;

	@Inject
	public FilterPresenterImpl(EventBus eventBus, FilterView filterView, FilmDataModel filmDataModel) {
		super();
		this.eventBus = eventBus;
		this.filterView = filterView;
		this.filmDataModel = filmDataModel;
		bind();
		updateFilterFromView();
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
		filterView.getNameBox().setValue("");
		filterView.getLengthSlider().setValue(new Range(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH));
		filterView.getYearSlider().setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		filterView.getCountrySelect().setValue(new ArrayList<String>());
		filterView.getLanguageSelect().setValue(new ArrayList<String>());
		filterView.getGenreSelect().setValue(new ArrayList<String>());
	}
	
	@Override
	public void setMode(String mode) {
		this.mode = mode;			// information about current mode is given to filterPresenter
		updateAppliedFilterBox();	// appliedFilterBox is updated according to mode
		filterView.setMode(mode); 	// countrySelect & yearSlider of filterView are setVisible according to mode
	}

	/**
	Helper Method to update the applied FilterBox according to the currently set mode of the filterPresenter
	@author Nicolas Küchler
	@pre 	mode != null && filterView != null && filmDataModel != null & filmDataModel.getAppliedMapFilter !=null
	@post	filterView.setAppliedFilterBox according to mode
	 */
	private void updateAppliedFilterBox()
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
	private void updateFilterFromView()
	{
		FilmFilter currentFilter = new FilmFilter();

		//setting value to null if no name filter is applied 
		String name = filterView.getNameBox().getValue();
		if (name.equals("") || name == null)
		{
			name = null;
		}
		currentFilter.setName(name);

		currentFilter.setLengthStart((int)filterView.getLengthSlider().getValue().getMinValue());
		currentFilter.setLengthEnd((int)filterView.getLengthSlider().getValue().getMaxValue());
		currentFilter.setYearStart((int)filterView.getYearSlider().getValue().getMinValue());
		currentFilter.setYearEnd((int)filterView.getYearSlider().getValue().getMaxValue());

		//setting value to null if no country filter is applied
		if (filterView.getCountrySelect().getValue() == null || filterView.getCountrySelect().getValue().size()==0)
		{
			currentFilter.setCountries(null);
		}
		else
		{
			currentFilter.setCountries(filterView.getCountrySelect().getValue());
		}

		//setting value to null if no genre filter is applied
		if (filterView.getGenreSelect().getValue() == null || filterView.getGenreSelect().getValue().size()==0)
		{
			currentFilter.setGenres(null);
		}
		else
		{
			currentFilter.setGenres(filterView.getGenreSelect().getValue());
		}

		//setting value to null if no language filter is applied
		if (filterView.getLanguageSelect().getValue() == null || filterView.getLanguageSelect().getValue().size()==0)
		{
			currentFilter.setLanguages(null);
		}
		else
		{
			currentFilter.setLanguages(filterView.getLanguageSelect().getValue());
		}


		filmDataModel.setAppliedFilter(currentFilter);
		filmDataModel.setAppliedMapFilter(adjustedMapFilter(currentFilter));

		onClear();
	}

	//TODO Decide if not better part of class FilmFilter
	/**
	Helper method to convert a FilmFilter Object to a List<String> Object.
	@author Nicolas Küchler
	@pre- 
	@post -
	@return List<String> with the applied FilterFields in a List
	 */
	private List<String> convertFilmFilterToList(FilmFilter filter)
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
	Helper method to adjust the currently appliedFilter for the map. Because the filtering of the year 
	for the map is done on clientside and the country filter doesn't apply in the map
	@author Nicolas Küchler
	@pre 	filmDataModel != null && filmDataModel.getAppliedFilter()!=null
	@post	filmDataModel.getAppliedFilter() == filmDataModel.getAppliedFilter() @pre
	@return FilmFilter that contains the boundaries for the years (because filtering of year 
			is done on clientside for the map) and the filter for the countries is removed.
	 */
	private FilmFilter adjustedMapFilter(FilmFilter appliedFilter)
	{
		//taking applied filter from filmDataModel
		FilmFilter filter = new FilmFilter();

		//copying the filter fields that are considered for the map as well
		filter.setName(appliedFilter.getName());
		filter.setLengthStart(appliedFilter.getLengthStart());
		filter.setLengthEnd(appliedFilter.getLengthEnd());
		filter.setGenres(appliedFilter.getGenres());
		filter.setLanguages(appliedFilter.getLanguages());

		//adjusting year range because filtering of that is done in the map on client side
		filter.setYearStart(Boundaries.MIN_YEAR);
		filter.setYearEnd(Boundaries.MAX_YEAR);

		//removing the country filter because in the map always all the countries should be considered
		filter.setCountries(null);

		return filter;
	}


}
