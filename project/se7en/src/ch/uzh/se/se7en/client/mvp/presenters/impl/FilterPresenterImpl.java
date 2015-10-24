
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.shared.model.FilmFilter;


public class FilterPresenterImpl implements FilterPresenter {

	private ClientFactory clientFactory  = GWT.create(ClientFactory.class);
	private EventBus eventBus;
	private FilterView filterView;
	private FilmDataModel filmDataModel;
	

	public FilterPresenterImpl(final FilterView filterView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		eventBus = clientFactory.getEventBus();
		this.filterView = filterView;
		bind();
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
		FilmFilter currentFilter = new FilmFilter();
		currentFilter.setName(filterView.getNameBox().getValue());
		currentFilter.setLengthStart((int)filterView.getLengthSlider().getValue().getMinValue()); //TODO test if minValue is right method, test if typecast does not matter
		currentFilter.setLengthEnd((int)filterView.getLengthSlider().getValue().getMaxValue()); //TODO test if maxValue is right method
		currentFilter.setYearStart((int)filterView.getYearSlider().getValue().getMinValue());
		currentFilter.setYearEnd((int)filterView.getYearSlider().getValue().getMaxValue());
		currentFilter.setCountries(filterView.getCountrySelect().getValue());
		currentFilter.setGenres(filterView.getGenreSelect().getValue());
		currentFilter.setLanguages(filterView.getLanguageSelect().getValue());
		
		filmDataModel.setAppliedFilter(currentFilter);
		eventBus.fireEvent(new FilterAppliedEvent());
		
		filterView.setAppliedFilter(convertFilmFilterToList(currentFilter));
		onClear();
	}

	@Override
	public void onClear() {
		// TODO reset Filter to default
	}

	@Override
	public void setMode(String mode) {
		filterView.setMode(mode);
	}
	
	private List<String> convertFilmFilterToList(FilmFilter filter)
	{
		List<String> filterList = new ArrayList<String>();
		//TODO Convert Filter into String List for AppliedFilterBox Widget
		return filterList;
	}

	@Override
	public void setFilter(String filterToken) {
		// TODO FilterParsing from Token
		// Tbd if the method just fills out the filterviewform or if it starts the search for data automatically.
		// --> could have a combination decided by "autoSearch" in filterToken
		
	}

}
