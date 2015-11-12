package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;
import java.util.Set;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.widgets.MultiSelect;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;

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
	Provides access to the input in the filter field name
	@author Nicolas Küchler
	@pre 	nameBox != null && filterPresenter != null
	@post	nameBox == nameBox @pre
	@return input of the nameBox
	 */
	public String getName();
	
	/**
	Provides access to set the input in the filter field name
	@author Nicolas Küchler
	@pre 	nameBox != null && filterPresenter != null
	@post	nameBox.getValue() == name
	@param  name the name that should be displayed in the filter field 
	 */
	public void setName(String name);
	
	/**
	Provides access to set the Range in the RangeSlider Length
	@author Nicolas Küchler
	@pre 	lengthSlider != null && filterPresenter != null
	@post	lengthSlider range == startLength:endLength
	@param  startLength the starting length (from)
	@param 	endLength the ending length (too)
	 */
	public void setLengthSlider(int startLength, int endLength);
	
	/**
	Provides access to the RangeSlider Length starting position
	@author Nicolas Küchler
	@pre 	lengthSlider != null && filterPresenter != null
	@post	lengthSlider == lengthSlider @pre
	@return starting position of the lengthSlider
	 */
	public int getLengthStart();
	
	/**
	Provides access to the RangeSlider Length ending position
	@author Nicolas Küchler
	@pre 	lengthSlider != null && filterPresenter != null
	@post	lengthSlider == lengthSlider @pre
	@return ending position of the lengthSlider
	 */
	public int getLengthEnd();
	
	/**
	Provides access to set the Range in the RangeSlider Year
	@author Nicolas Küchler
	@pre 	yearSlider != null && filterPresenter != null
	@post	yearSlider range == startYear:endYear
	@param  startYear the starting year (from)
	@param 	endYear the ending year (too)
	 */
	public void setYearSlider(int startYear, int endYear);
	
	/**
	Provides access to the RangeSlider Year starting position
	@author Nicolas Küchler
	@pre 	yearSlider != null && filterPresenter != null
	@post	yearSlider == yearSlider @pre
	@return starting position of the yearSlider
	 */
	public int getYearStart();
	
	/**
	Provides access to the RangeSlider Year ending position
	@author Nicolas Küchler
	@pre 	yearSlider != null && filterPresenter != null
	@post	yearSlider == yearSlider @pre
	@return ending position of the yearSlider
	 */
	public int getYearEnd();
	
	/**
	Sets the available options to choose in the MultiSelect Country
	@author Nicolas Küchler
	@pre 	countrySelect != null && filterPresenter != null
	@post	countrySelect available Options == selectOptions
	@param selectOptions a list of SelectOption
	 */
	public void setCountryOptions(List<SelectOption> selectOptions);
	
	/**
	Gets the information about the currently chosen (selected) options in the MultiSelect Country
	@author Nicolas Küchler
	@pre 	countrySelect != null && filterPresenter != null
	@post	countrySelect selected options == countrySelect selected options @pre
	@return	A List of SelectOption which include the currently selected options. 
	 */
	public List<SelectOption> getSelectedCountryOptions();
	
	/**
	Sets currently chosen (selected) options in the MultiSelect Country
	@author Nicolas Küchler
	@pre 	countrySelect != null && filterPresenter != null
	@post	countrySelect selected options == selectedOptions
	@param	selectedOptions Options that are available in the MultiSelect and should be selected
	 */
	public void setSelectedCountryOptions(Set<Integer> selectedOptions);
	
	/**
	Sets the available options to choose in the MultiSelect Language
	@author Nicolas Küchler
	@pre 	languageSelect != null && filterPresenter != null
	@post	languageSelect available Options == selectOptions
	@param 	selectOptions a list of SelectOption
	 */
	public void setLanguageOptions(List<SelectOption> selectOptions);
	
	/**
	Gets the information about the currently chosen (selected) options in the MultiSelect Language
	@author Nicolas Küchler
	@pre 	languageSelect != null && filterPresenter != null
	@post	languageSelect selected options == languageSelect selected options @pre
	@return	A List of SelectOption which include the currently selected options. 
	 */
	public List<SelectOption> getSelectedLanguageOptions();
	
	/**
	Sets currently chosen (selected) options in the MultiSelect Language
	@author Nicolas Küchler
	@pre 	languageSelect != null && filterPresenter != null
	@post	languageSelect selected options == selectedOptions
	@param	selectedOptions Options that are available in the MultiSelect and should be selected
	 */
	public void setSelectedLanguageOptions(Set<Integer> selectedOptions);
	
	/**
	Sets the available options to choose in the MultiSelect Genre
	@author Nicolas Küchler
	@pre 	genreSelect != null && filterPresenter != null
	@post	genreSelect available Options == selectOptions
	@param selectOptions a list of SelectOption
	 */
	public void setGenreOptions(List<SelectOption> selectOptions);
	
	/**
	Gets the information about the currently chosen (selected) options in the MultiSelect Genre
	@author Nicolas Küchler
	@pre 	genreSelect != null && filterPresenter != null
	@post	genreSelect selected options == genreSelect selected options @pre
	@return	A List of SelectOption which include the currently selected options. 
	 */
	public List<SelectOption> getSelectedGenreOptions();
	
	/**
	Sets currently chosen (selected) options in the MultiSelect Genre
	@author Nicolas Küchler
	@pre 	genreSelect != null && filterPresenter != null
	@post	genreSelect selected options == selectedOptions
	@param	selectedOptions Options that are available in the MultiSelect and should be selected
	 */
	public void setSelectedGenreOptions(Set<Integer> selectedOptions);
	
	/**
	Provides access to the UI Component AppliedFilter for the filterPresenter
	@author Nicolas Küchler
	@pre 	appliedFilter != null && filterPresenter != null
	@post	appliedFilter == filter
	@param	filter valid FilmFilter instance
	 */
	public void setAppliedFilterBox(List<String> appliedFilter);
	
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
