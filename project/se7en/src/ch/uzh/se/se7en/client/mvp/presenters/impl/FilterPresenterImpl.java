
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.shared.model.Country;
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
		onClear();
		eventBus.fireEvent(new FilterAppliedEvent());
		filterView.setAppliedFilter(convertFilmFilterToList(currentFilter));
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
		filterView.setMode(mode);
	}
	
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
		for(int i = 0; filter != null && i < filter.getCountries().size(); i++)
		{
			filterList.add("Production Country = " + filter.getCountries().get(i));
		}
		
		//language
		for(int i = 0; filter != null &&  i < filter.getLanguages().size(); i++)
		{
			filterList.add("Film Language = " + filter.getLanguages().get(i));
		}
		
		//genre
		for(int i = 0; filter != null && i < filter.getGenres().size(); i++)
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

}
