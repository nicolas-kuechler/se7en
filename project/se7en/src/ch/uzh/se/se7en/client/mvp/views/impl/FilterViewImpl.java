package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.TooltipType;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideEvent;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.GwtEvent;
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
	
	@UiField
	TextBox lengthTextBox;
	@UiField
	TextBox yearTextBox;
	
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
	Button openCloseFilter;
	@UiField
	Button shareFacebook;

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
		lengthSlider.setTooltip(TooltipType.HIDE);
		yearSlider = new RangeSlider();
		yearSlider.setWidth("70%");
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		yearSlider.setTooltip(TooltipType.HIDE);
		initWidget(uiBinder.createAndBindUi(this));
		// Setting Up Listening for Enter Pressed Events to start the search
		collapseBox.setIn(true);
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					filterPresenter.onSearch();
				}
			}
		});
		lengthTextBox.setText(Boundaries.MIN_LENGTH + ":" + Boundaries.MAX_LENGTH);
		yearTextBox.setText(Boundaries.MIN_YEAR + ":" + Boundaries.MAX_YEAR);
		
	}

	@Override
	public void setPresenter(FilterPresenter presenter) {
		this.filterPresenter = presenter;
	}

	/**
	 * Sends a message to the presenter when the shareButton is clicked
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param event
	 */
	@UiHandler("shareFacebook")
	public void onFacebookBtnClicked(final ClickEvent event) {
		filterPresenter.onFacebook();
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
		filterPresenter.onSearch();
	}
	
	@UiHandler("lengthSlider")
	public void onRangeSlideValueChange(ValueChangeEvent<Range> event) {
		String range = getLengthStart()+":"+getLengthEnd();
		if(!lengthTextBox.getText().equals(range))
		{
			lengthTextBox.setText(range);
		}
	}
	
	@UiHandler("lengthTextBox")
	public void onLengthTextBoxChange(ChangeEvent event)
	{
		String range = lengthTextBox.getText();
		if(!range.contains(":"))
		{
			lengthTextBox.setText(Boundaries.MIN_LENGTH+":"+Boundaries.MAX_LENGTH);
			setLengthSlider(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH);
		}
		else
		{
			try
			{
				//parse min max from textbox input
				int start = Integer.parseInt(range.split(":")[0]);
				int end = Integer.parseInt(range.split(":")[1]);
				
				//only set the length slider if its current value is not already the new
				if(start!=getLengthStart() || end != getLengthEnd())
				{
					setLengthSlider(start, end);
				}
			}
			catch (NumberFormatException e) //when textbox input is not valid
			{
				lengthTextBox.setText(Boundaries.MIN_LENGTH+":"+Boundaries.MAX_LENGTH);
				setLengthSlider(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH);
			}
		}
	}
	
	@UiHandler("yearSlider")
	public void onYearRangeSlideValueChange(ValueChangeEvent<Range> event) {
		String range = getYearStart()+":"+getYearEnd();
		if(!yearTextBox.getText().equals(range))
		{
			yearTextBox.setText(range);
		}
	}
	
	@UiHandler("yearTextBox")
	public void onYearTextBoxChange(ChangeEvent event)
	{
		String range = yearTextBox.getText();
		if(!range.contains(":"))
		{
			yearTextBox.setText(Boundaries.MIN_YEAR+":"+Boundaries.MAX_YEAR);
			setYearSlider(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR);
		}
		else
		{
			try
			{
				//parse min max from textbox input
				int start = Integer.parseInt(range.split(":")[0]);
				int end = Integer.parseInt(range.split(":")[1]);
				
				//only set the length slider if its current value is not already the new
				if(start!=getYearStart() || end != getYearEnd())
				{
					setYearSlider(start, end);
				}
			}
			catch (NumberFormatException e) //when textbox input is not valid
			{
				yearTextBox.setText(Boundaries.MIN_YEAR+":"+Boundaries.MAX_YEAR);
				setYearSlider(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR);
			}
		}
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
		lengthSlider.setValue(new Range(startLength, endLength),true);
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