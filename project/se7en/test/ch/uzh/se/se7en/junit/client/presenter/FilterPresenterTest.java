package ch.uzh.se.se7en.junit.client.presenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwtmockito.AsyncAnswers;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.FilterPresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.SelectOption;

@RunWith(JukitoRunner.class)
public class FilterPresenterTest {

	
	FilterPresenterImpl filterPresenter;
	
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	EventBus eventBus;
	@Inject
	FilterView filterView;
	@Inject
	HasWidgets container;
	@Inject FilmListServiceAsync filmService;
	
	FilmFilter normalFilter;
	FilmFilter mapFilter;
	
	List<String> normalFilterList;
	List<String> mapFilterList;
	
	
	@Before
	public void setup()
	{
		//demo values used to test
		String testName = "TestFilm";
		int testLengthStart = 10;
		int testLengthEnd = 300;
		int testYearStart = 1980;
		int testYearEnd = 2010;
		int testMinYear = 1890;
		int testMaxYear = 2015;
		
		// multiple entries from multiselect are selected
		Set<Integer> selectedCountry = new HashSet<Integer>();
		selectedCountry.add(1);
		selectedCountry.add(2);
		
		// only one entry from multiselect is selected
		Set<Integer> selectedLanguage = new HashSet<Integer>();
		selectedLanguage.add(5);
		
		// no entry from multiselect is selected
		Set<Integer> selectedGenre = new HashSet<Integer>();
		
		//mocking answers of the filterView Mock
		when(filterView.getName()).thenReturn(testName);
		when(filterView.getLengthStart()).thenReturn(testLengthStart);
		when(filterView.getLengthEnd()).thenReturn(testLengthEnd);
		when(filterView.getYearStart()).thenReturn(testYearStart);
		when(filterView.getYearEnd()).thenReturn(testYearEnd);
		when(filterView.getSelectedCountryIds()).thenReturn(selectedCountry);
		when(filterView.getSelectedLanguageIds()).thenReturn(selectedLanguage);
		when(filterView.getSelectedGenreIds()).thenReturn(selectedGenre);
		
		//creating a demo Filter object which is expected to be produced given the filterView fields configuration from above
		normalFilter = new FilmFilter();
		normalFilter.setName(testName);
		normalFilter.setLengthStart(testLengthStart);
		normalFilter.setLengthEnd(testLengthEnd);
		normalFilter.setYearStart(testYearStart);
		normalFilter.setYearEnd(testYearEnd);
		normalFilter.setCountryIds(selectedCountry);
		normalFilter.setLanguageIds(selectedLanguage);
		normalFilter.setGenreIds(null);
		
		//creating a filter object which should be produced when adjusting the normalFilter from above with setting the default values for country and year
		mapFilter = new FilmFilter();
		mapFilter.setName(testName);
		mapFilter.setLengthStart(testLengthStart);
		mapFilter.setLengthEnd(testLengthEnd);
		mapFilter.setYearStart(testMinYear);
		mapFilter.setYearEnd(testMaxYear);
		mapFilter.setCountryIds(null);
		mapFilter.setLanguageIds(selectedLanguage);
		mapFilter.setGenreIds(null);
		
		//creating an expected List<String> which should be produced when the normalFilter from above is converted
		normalFilterList = new ArrayList<String>();
		normalFilterList.add("Film Name = "+testName);
		normalFilterList.add("Film Length = "+testLengthStart +"-"+testLengthEnd);
		normalFilterList.add("Production Year = "+testYearStart +"-"+testYearEnd);
		normalFilterList.add("Production Country = Switzerland");
		normalFilterList.add("Production Country = Germany");
		normalFilterList.add("Film Language = German");
		
		//creating an expected List<String> which should be produced when the mapFilter from above is converted
		mapFilterList = new ArrayList<String>();
		mapFilterList.add("Film Name = "+testName);
		mapFilterList.add("Film Length = "+testLengthStart +"-"+testLengthEnd);
		mapFilterList.add("Film Language = German");
	
		//mocking answer from filmDataModel mock
		when(filmDataModel.getAppliedFilter()).thenReturn(normalFilter);
		when(filmDataModel.getAppliedMapFilter()).thenReturn(mapFilter);
		
		
		when(filmDataModel.getCountryName(1)).thenReturn("Switzerland");
		when(filmDataModel.getCountryName(2)).thenReturn("Germany");
		when(filmDataModel.getLanguageName(5)).thenReturn("German");
		
		//imitate the onSuccess method of the rpc call getCountrySelectOptions
		doAnswer(new Answer<List<SelectOption>>(){
			@Override
			public List<SelectOption> answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<List<SelectOption>> callback = (AsyncCallback) invocation.getArguments()[0];
				
				List<SelectOption> options = new ArrayList<SelectOption>();
				options.add(new SelectOption(1, "Switzerland"));
				options.add(new SelectOption(2, "Germany"));
				options.add(new SelectOption(3, "Austria"));
			     callback.onSuccess(options);
				return null;
			}
		}).when(filmService).getCountrySelectOption((AsyncCallback<List<SelectOption>>) Mockito.any());
		
		//imitate the onSuccess method of the rpc call getLanguageSelectOptions
		doAnswer(new Answer<List<SelectOption>>(){
			@Override
			public List<SelectOption> answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<List<SelectOption>> callback = (AsyncCallback) invocation.getArguments()[0];
				
				List<SelectOption> options = new ArrayList<SelectOption>();
				options.add(new SelectOption(1, "German"));
				options.add(new SelectOption(2, "English"));
			     callback.onSuccess(options);
				return null;
			}
		}).when(filmService).getLanguageSelectOption((AsyncCallback<List<SelectOption>>) Mockito.any());
		
		//imitate the onSuccess method of the rpc call getGenreSelectOptions
		doAnswer(new Answer<List<SelectOption>>(){
			@Override
			public List<SelectOption> answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<List<SelectOption>> callback = (AsyncCallback) invocation.getArguments()[0];
				
				List<SelectOption> options = new ArrayList<SelectOption>();
				options.add(new SelectOption(1, "Action"));
				options.add(new SelectOption(2, "Adventure"));
				options.add(new SelectOption(3, "Comedy"));
			     callback.onSuccess(options);
				return null;
			}
		}).when(filmService).getGenreSelectOption((AsyncCallback<List<SelectOption>>) Mockito.any());
		
		//After all mocks are setup, create the instance of the filterPresenter
		filterPresenter = new FilterPresenterImpl(eventBus, filterView, filmDataModel, filmService);
		
		//set a mode
		filterPresenter.setMode(Tokens.TABLE);
	}
	
		
	@Test
	public void testBind() {
		verify(filterView).setPresenter(filterPresenter);
	}
	
	@Test
	public void testGo(){
		filterPresenter.go(container);
		verify(container).clear();
		verify(container).add(filterView.asWidget());
	}
	
	@Test
	public void testOnSearch(){
		//TODO NK Testing static native methods
//		filterPresenter.onSearch();
//		//times(2) because the first time is in the constructor
//		verify(filmDataModel, times(2)).setAppliedFilter(Matchers.eq(normalFilter));
//		verify(filmDataModel, times(2)).setAppliedMapFilter(Matchers.eq(mapFilter));
//		verify(eventBus).fireEvent(Matchers.any(FilterAppliedEvent.class));
	}
	
	@Test
	public void testOnClear(){
		filterPresenter.onClear();
		//testing that all filter fields are resetted to their default values
		verify(filterView).setName("");
		verify(filterView).setLengthSlider(0, 600);
		verify(filterView).setYearSlider(1890, 2015);
		verify(filterView).setSelectedCountryOptions(null);
		verify(filterView).setSelectedLanguageOptions(null);
		verify(filterView).setSelectedGenreOptions(null);
	}
	
	@Test
	public void testSetMode(){
		filterPresenter.setMode(Tokens.MAP);
		verify(filterView, times(1)).setMode(Tokens.MAP);
		verify(filterView, times(2)).setAppliedFilterBox(Matchers.anyListOf(String.class));
		
		
		filterPresenter.setMode(Tokens.TABLE);
		verify(filterView, times(2)).setMode(Tokens.TABLE);
		verify(filterView, times(3)).setAppliedFilterBox(Matchers.anyListOf(String.class));
	}
	
	@Test
	public void testSetupMultiSelects(){
		verify(filterView).setCountryOptions(Matchers.anyListOf(SelectOption.class));
		verify(filterView).setGenreOptions(Matchers.anyListOf(SelectOption.class));
		verify(filterView).setLanguageOptions(Matchers.anyListOf(SelectOption.class));
		
	}
	
	@Test
	public void testUpdateAppliedFilterBox()
	{
		filterPresenter.setMode(Tokens.MAP);
		filterPresenter.updateAppliedFilterBox();
		//2 times because 1st time in setMode method and 2nd time in updateAppliedFilter
		verify(filterView, times(2)).setAppliedFilterBox(Matchers.eq(filterPresenter.convertFilmFilterToList(mapFilter)));
		
		filterPresenter.setMode(Tokens.TABLE);
		filterPresenter.updateAppliedFilterBox();
		//3 times because 1st time in Constructor, 2nd time in setMode method and 3rd time in updateAppliedFilter
		verify(filterView, times(3)).setAppliedFilterBox(Matchers.eq(filterPresenter.convertFilmFilterToList(normalFilter)));
	}
	
	@Test
	public void testUpdateFilterFromView()
	{
		filterPresenter.updateFilterFromView();
		//times(2) because the first time is in the constructor
		verify(filmDataModel, times(2)).setAppliedFilter(Matchers.eq(normalFilter));
		verify(filmDataModel, times(2)).setAppliedMapFilter(Matchers.eq(mapFilter));
	}
	
	@Test
	public void testConvertFilmFilterToList(){
		List<String> result = filterPresenter.convertFilmFilterToList(normalFilter);
		assertThat(normalFilterList, is(result));
		
		result = filterPresenter.convertFilmFilterToList(mapFilter);
		assertThat(mapFilterList, is(result));		
	}
	
	@Test
	public void testAdjustedMapFilter(){
		FilmFilter adjustedFilter = filterPresenter.adjustedMapFilter(normalFilter);
		assertThat(mapFilter, is(adjustedFilter));
	}
	

}
