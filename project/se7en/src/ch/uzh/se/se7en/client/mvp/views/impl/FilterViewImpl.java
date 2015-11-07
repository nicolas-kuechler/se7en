package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.mvp.views.widgets.AppliedFilterBox;
import ch.uzh.se.se7en.client.mvp.views.widgets.MultiSelect;
import ch.uzh.se.se7en.shared.model.SelectOption;

import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Collapse;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;

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
	FormGroup yearColumn;
	@UiField
	FormGroup countryColumn;

	/**
	 * Initialize the FilterView and set default settings of the sliders
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return -
	 */
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

	/**
	 * Set the presenter of the FilterView
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param presenter
	 * @return -
	 */
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
	 * @return -
	 */
	@UiHandler("searchBtn")
	public void onSearchBtnClick(final ClickEvent event) {
		filterPresenter.onSearch();
	}

	/**
	 * Sends a message to the presenter if the clear button is clicked
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param event
	 * @return -
	 */
	@UiHandler("clearBtn")
	public void onClearBtnClick(final ClickEvent event) {
		filterPresenter.onClear();
	}

	/**
	 * Returns the value of the nameBox
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return nameBox
	 */
	@Override
	public HasValue<String> getNameBox() {
		return nameBox;
	}

	/**
	 * Returns the value of the lengthSlider
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return lengthSlider
	 */
	@Override
	public HasValue<Range> getLengthSlider() {
		return lengthSlider;
	}

	/**
	 * Returns the value of the yearSlider
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return yearSlider
	 */
	@Override
	public HasValue<Range> getYearSlider() {
		return yearSlider;
	}

	/**
	 * Returns the value of the countrySelect
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return countrySelect
	 */
	@Override
	public MultiSelect getCountrySelect() {
		return countrySelect;
	}

	/**
	 * Returns the value of the languageSelect
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return languageSelect
	 */
	@Override
	public MultiSelect getLanguageSelect() {
		return languageSelect;
	}

	/**
	 * Returns the value of the genreSelect
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param -
	 * @return genreSelect
	 */
	@Override
	public MultiSelect getGenreSelect() {
		return genreSelect;
	}

	/**
	 * Sets the filters in the appliedFilterBox
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param appliedFilter
	 * @return -
	 */
	@Override
	public void setAppliedFilterBox(List<String> appliedFilter) {
		this.appliedFilter.setValue(appliedFilter);
	}

	/**
	 * Disables the year and country input fields if the user is on the MapView
	 * 
	 * @author Nicolas Küchler
	 * @pre container != null
	 * @post -
	 * @param mode
	 * @return -
	 */
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
}