package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public interface FilterView extends IsWidget {
	/**
	Allows the filterPresenter to set itself within the filterView
	@author Nicolas Küchler
	@pre -
	@post filterPresenter == presenter
	@param presenter presenter instance that comes from the clientFactory
	 */
	public void setPresenter(FilterPresenter presenter);
	
	/**
	Provides access to the UI Component nameBox for the filterPresenter
	@author Nicolas Küchler
	@pre 	nameBox != null && filterPresenter != null
	@post	nameBox == nameBox @pre
	@return	Objects that allows syntax to get access: getNameBox().setValue(text) or getNameBox().getValue()
	 */
	public HasValue<String> getNameBox();
	
	/**
	Provides access to the UI Component lengthSlider for the filterPresenter
	@author Nicolas Küchler
	@pre 	lengthSlider != null && filterPresenter != null
	@post	lengthSlider == lengthSlider @pre
	@return	Objects that allows syntax to get access: getLengthSlider().setValue(range) or getLengthSlider().getValue()
	 */
	public HasValue<Range> getLengthSlider();
	
	/**
	Provides access to the UI Component yearSlider for the filterPresenter
	@author Nicolas Küchler
	@pre 	yearSlider != null && filterPresenter != null
	@post	yearSlider == yearSlider @pre
	@return	Objects that allows syntax to get access: getYearSlider().setValue(range) or getYearSlider().getValue()
	 */
	public HasValue<Range> getYearSlider();
	
	/**
	Provides access to the UI Component countrySelect for the filterPresenter
	@author Nicolas Küchler
	@pre 	countrySelect != null && filterPresenter != null
	@post	countrySelect == countrySelect @pre
	@return	Objects that allows syntax to get access: getCountrySelect().setValue(countryList) or getCountrySelect().getValue()
	 */
	public HasValue<List<String>> getCountrySelect();
	
	/**
	Provides access to the UI Component languageSelect for the filterPresenter
	@author Nicolas Küchler
	@pre 	languageSelect != null && filterPresenter != null
	@post	languageSelect == languageSelect @pre
	@return	Objects that allows syntax to get access: getLanguageSelect().setValue(languageList) or getLanguageSelect().getValue()
	 */
	public HasValue<List<String>> getLanguageSelect();
	
	/**
	Provides access to the UI Component genreSelect for the filterPresenter
	@author Nicolas Küchler
	@pre 	genreSelect != null && filterPresenter != null
	@post	genreSelect == genreSelect @pre
	@return	Objects that allows syntax to get access: getGenreSelect().setValue(languageList) or getGenreSelect().getValue()
	 */
	public HasValue<List<String>> getGenreSelect();
	
	/**
	Provides access to the UI Component AppliedFilter for the filterPresenter
	@author Nicolas Küchler
	@pre 	appliedFilter != null && filterPresenter != null
	@post	appliedFilter == filter
	@param	filter valid FilmFilter instance
	 */
	public void setAppliedFilter(List<String> appliedFilter);
	
	/**
	Is responsible for enabling/disabling the filter boxes for map/table view
	Changes the appliedFilter Box for the map/table view
	@author Nicolas Küchler
	@pre	-
	@post	filterBoxes are enabled/disabled according to the mode, appliedFilter Box is set according to the mode
	@param 	mode mode.equals(Tokens.MAP) || mode.equals(Tokens.TABLE) 
	 */
	public void setMode(String mode);
	
}
