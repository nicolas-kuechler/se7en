package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

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
	@UiField RangeSlider lengthSlider;
	@UiField MultiSelect countrySelect;
	@UiField MultiSelect languageSelect;
	@UiField MultiSelect genreSelect;
	@UiField RangeSlider yearSlider;
	
	@UiField Button clearBtn;
	@UiField Button searchBtn;
	
	@UiField AppliedFilterBox appliedFilter;
	
	public FilterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));	
		
		//TODO Setup Enter Pressed for start search
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
	public void setAppliedFilter(List<String> appliedFilter) {
		this.appliedFilter.setValue(appliedFilter);
	}

	@Override
	public void setMode(String mode) {
		// TODO mode logic for change of filter view needs to be implemented
	}
}
