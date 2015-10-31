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

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;

public class FilterViewImpl extends Composite implements FilterView{

	private static FilterViewImplUiBinder uiBinder = GWT.create(FilterViewImplUiBinder.class);

	interface FilterViewImplUiBinder extends UiBinder<Widget, FilterViewImpl> {
	}
	
	FilterPresenter filterPresenter;
	
	@UiField TextBox nameBox;
	@UiField (provided = true) RangeSlider lengthSlider;
	@UiField (provided = true) RangeSlider yearSlider;
	@UiField MultiSelect countrySelect;
	@UiField MultiSelect languageSelect;
	@UiField MultiSelect genreSelect;
	
	
	@UiField Button clearBtn;
	@UiField Button searchBtn;
	
	@UiField AppliedFilterBox appliedFilter;
	@UiField FocusPanel focusPanel;
	
	@Inject
	public FilterViewImpl() {
		//Setting the Range for the Sliders
		lengthSlider = new RangeSlider();
		lengthSlider.setMin(Boundaries.MIN_LENGTH);
		lengthSlider.setMax(Boundaries.MAX_LENGTH);
		lengthSlider.setValue(new Range(Boundaries.MIN_LENGTH, Boundaries.MAX_LENGTH));
		yearSlider = new RangeSlider();
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		initWidget(uiBinder.createAndBindUi(this));	
		
		//Setting Up Listening for Enter Pressed Events to start the search
		focusPanel.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				 if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
				 {
					 filterPresenter.onSearch();
				 }
			}
		});		
	}

	@Override
	public void setPresenter(FilterPresenter presenter) {
		this.filterPresenter = presenter;
	}
	
	
	@UiHandler("searchBtn")
	public void onSearchBtnClick(final ClickEvent event)
	{
		filterPresenter.onSearch();
	}
	
	@UiHandler("clearBtn")
	public void onClearBtnClick(final ClickEvent event)
	{
		filterPresenter.onClear();
	}
	
	
	@Override
	public HasValue<String> getNameBox() {
		return nameBox;
	}

	@Override
	public HasValue<Range> getLengthSlider() {
		return lengthSlider;
	}

	@Override
	public HasValue<Range> getYearSlider() {
		return yearSlider;
	}

	@Override
	public HasValue<List<String>> getCountrySelect() {
		return countrySelect;
	}

	@Override
	public HasValue<List<String>> getLanguageSelect() {
		return languageSelect;
	}

	@Override
	public HasValue<List<String>> getGenreSelect() {
		return genreSelect;
	}

	@Override
	public void setAppliedFilterBox(List<String> appliedFilter) {
		this.appliedFilter.setValue(appliedFilter);
	}

	@Override
	public void setMode(String mode) {
		if (mode.equals(Tokens.MAP))
		{
			countrySelect.setVisible(false);
			yearSlider.setVisible(false);
		}
		else
		{
			countrySelect.setVisible(true);
			yearSlider.setVisible(true);
		}
	}
}
