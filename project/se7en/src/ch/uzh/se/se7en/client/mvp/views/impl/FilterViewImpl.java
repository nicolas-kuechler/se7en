package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.mvp.views.widgets.AppliedFilterBox;
import ch.uzh.se.se7en.client.mvp.views.widgets.MultiSelect;

public class FilterViewImpl extends Composite implements FilterView {

	private static FilterViewImplUiBinder uiBinder = GWT.create(FilterViewImplUiBinder.class);

	interface FilterViewImplUiBinder extends UiBinder<Widget, FilterViewImpl> {
	}

	FilterPresenter filterPresenter;

	@UiField
	TextBox nameBox;
	@UiField(provided = true)
	RangeSlider lengthSlider;
	@UiField(provided = true)
	RangeSlider yearSlider;
	@UiField
	MultiSelect countrySelect;
	@UiField
	MultiSelect languageSelect;
	@UiField
	MultiSelect genreSelect;

	@UiField
	Button clearBtn;
	@UiField
	Button searchBtn;
	@UiField
	AppliedFilterBox appliedFilter;
	@UiField
	FocusPanel focusPanel;
	@UiField
	PanelCollapse collapseBox;
	@UiField
	FormGroup yearColumn;
	@UiField
	FormGroup countryColumn;


	@Inject
	public FilterViewImpl() {
		// Setting the Range for the Sliders
		lengthSlider = new RangeSlider();
		lengthSlider.setMin(Boundaries.MIN_LENGTH);
		lengthSlider.setMax(Boundaries.MAX_LENGTH);
		lengthSlider.setWidth("70%");
		lengthSlider.setValue(new Range(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH));
		yearSlider = new RangeSlider();
		yearSlider.setWidth("70%");
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		initWidget(uiBinder.createAndBindUi(this));
		collapseBox.setIn(true);
		// Setting Up Listening for Enter Pressed Events to start the search
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					filterPresenter.onSearch();
				}
			}
		});
	}

	@Override
	public void setPresenter(FilterPresenter presenter) {
		this.filterPresenter = presenter;
	}

	/**
	 * Sends a message to the presenter if the search button is clicked
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param event
	 */
	@UiHandler("searchBtn")
	public void onSearchBtnClick(final ClickEvent event) {
		collapseBox.setIn(false);
		filterPresenter.onSearch();
	}

	/**
	 * Sends a message to the presenter if the clear button is clicked
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param event
	 */
	@UiHandler("clearBtn")
	public void onClearBtnClick(final ClickEvent event) {
		filterPresenter.onClear();
	}

	@Override
	public void setAppliedFilterBox(List<String> appliedFilter) {
		this.appliedFilter.setValue(appliedFilter);
	}

	@Override
	public void setMode(String mode) {
		if (mode.equals(Tokens.MAP)) {
			countryColumn.setVisible(false);
			yearColumn.setVisible(false);
		} else {
			countryColumn.setVisible(true);
			yearColumn.setVisible(true);
		}
	}

	@Override
	public String getName() {
		return nameBox.getValue();
	}

	@Override
	public void setName(String name) {
		nameBox.setValue(name);
	}

	@Override
	public void setLengthSlider(int startLength, int endLength) {
		lengthSlider.setValue(new Range(startLength, endLength));
	}

	@Override
	public int getLengthStart() {
		return (int)lengthSlider.getValue().getMinValue();
	}

	@Override
	public int getLengthEnd() {
		return (int)lengthSlider.getValue().getMaxValue();
	}

	@Override
	public void setYearSlider(int startYear, int endYear) {
		yearSlider.setValue(new Range(startYear, endYear));
	}

	@Override
	public int getYearStart() {
		return (int)yearSlider.getValue().getMinValue();
	}

	@Override
	public int getYearEnd() {
		return (int)yearSlider.getValue().getMaxValue();
	}

	@Override
	public void setCountryOptions(HashMap<Integer,String> selectOptions) {
		countrySelect.setOptions(selectOptions);
	}

	@Override
	public Set<Integer> getSelectedCountryIds() {
		return countrySelect.getAllSelectedIds();
	}

	@Override
	public void setSelectedCountryOptions(Set<Integer> selectedOptions) {
		if(selectedOptions==null)
		{
			countrySelect.deselectAll();
		}
		else
		{
			countrySelect.select(selectedOptions);
		}
	}

	@Override
	public void setLanguageOptions(HashMap<Integer,String> selectOptions) {
		languageSelect.setOptions(selectOptions);		
	}

	@Override
	public Set<Integer> getSelectedLanguageIds() {
		return languageSelect.getAllSelectedIds();
	}

	@Override
	public void setSelectedLanguageOptions(Set<Integer> selectedOptions) {
		if(selectedOptions==null)
		{
			languageSelect.deselectAll();
		}
		else
		{
			languageSelect.select(selectedOptions);
		}
		
	}

	@Override
	public void setGenreOptions(HashMap<Integer,String> selectOptions) {
		genreSelect.setOptions(selectOptions);
	}

	@Override
	public Set<Integer> getSelectedGenreIds() {
		return genreSelect.getAllSelectedIds();
	}

	@Override
	public void setSelectedGenreOptions(Set<Integer> selectedOptions) {
		if(selectedOptions==null)
		{
			genreSelect.deselectAll();
		}
		else
		{
			genreSelect.select(selectedOptions);
		}
	}
}