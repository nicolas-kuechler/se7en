package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.mvp.LoadingStates;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.mvp.views.widgets.AppliedFilterBox;
import ch.uzh.se.se7en.client.mvp.views.widgets.MultiSelect;
import ch.uzh.se.se7en.shared.model.FilmFilter;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;

public class FilterViewImpl extends Composite implements FilterView{

	private static FilterViewImplUiBinder uiBinder = GWT.create(FilterViewImplUiBinder.class);

	interface FilterViewImplUiBinder extends UiBinder<Widget, FilterViewImpl> {
	}
	
	private FilterPresenter filterPresenter;
	//private TextBox nameBox;
	private RangeSlider lengthSlider;
	private MultiSelect countrySelect;
	private MultiSelect languageSelect;
	private MultiSelect genreSelect;
	private RangeSlider yearSlider;
	
	//private Button searchBtn;
	private Button clearBtn;
	private AppliedFilterBox appliedFilter;
	
	
	@UiField Button searchBtn; //DEMO
	@UiField TextBox nameBox; //DEMO

	public FilterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		searchBtn.setDataLoadingText("Searching...");
		
	}

	@Override
	public void setPresenter(FilterPresenter presenter) {
		this.filterPresenter = presenter;
	}
	
	//DEMO PURPOSE
	@UiHandler("searchBtn")
	public void onButtonClick(final ClickEvent event)
	{
		filterPresenter.onSearch();
	}

	@Override
	public void setLoadingState(String state) {
		//DEMO Purpose
		if (state.equals(LoadingStates.ERROR))
		{
			searchBtn.state().reset();
			searchBtn.setType(ButtonType.DANGER);
	
		}
		else if(state.equals(LoadingStates.LOADING))
		{
			searchBtn.state().loading();
			searchBtn.setType(ButtonType.PRIMARY);

		}
		else if(state.equals(LoadingStates.SUCCESS))
		{
			searchBtn.state().reset();
			searchBtn.setType(ButtonType.SUCCESS);
		}		
		else if(state.equals(LoadingStates.DEFAULT))
		{
			searchBtn.state().reset();
			searchBtn.setType(ButtonType.PRIMARY);
		}
		//DEMO end
	}
	
//	@UiHandler("searchBtn")
//	public void onSearchBtnClick(final ClickEvent event)
//	{
//		filterPresenter.onSearch();
//	}
	
//	@UiHandler("clearBtn")
//	public void onClearBtnClick(final ClickEvent event)
//	{
//		filterPresenter.onClear();
//	}
	

	@Override
	public HasValue<String> getNameBox() {
		return nameBox;
	}

	@Override
	public HasValue<Range> getLengthSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<Range> getYearSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getCountrySelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getLanguageSelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getGenreSelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAppliedFilter(FilmFilter filter) {
		// TODO Auto-generated method stub
		
	}

}
