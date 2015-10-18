package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;

public interface FilterView extends IsWidget {
	public void setPresenter(FilterPresenter presenter);
	public HasValue<String> getNameBox();
	public HasValue<Range> getLengthSlider();
	public HasValue<Range> getYearSlider();
	public HasValue<List<String>> getCountrySelect();
	public HasValue<List<String>> getLanguageSelect();
	public HasValue<List<String>> getGenreSelect();
	
	//provide access for setting options in MultiSelects
	
	/**
	@author Nicolas Küchler
	@pre	-
	@post	filterView UI is in the state mode and display
	@param 	state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS
	 */
	public void setLoadingState(String state);
}
