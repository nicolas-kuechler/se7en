package ch.uzh.se.se7en.junit.client.presenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.Tokens;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterOptionsLoadedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterOptionsLoadedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.FilterPresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.util.BrowserUtil;
import ch.uzh.se.se7en.client.mvp.presenters.impl.util.UrlToken;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.FilterOptions;

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
	
	@Mock
	BrowserUtil browserUtil;
	@Mock
	UrlToken urlToken;
	
	FilmFilter normalFilter;
	FilmFilter mapFilter;
	
	List<String> normalFilterList;
	List<String> mapFilterList;
	
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		
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
		
		//imitate the onSuccess method of the rpc call getSelectOptions
				doAnswer(new Answer<FilterOptions>(){
					@Override
					public FilterOptions answer(InvocationOnMock invocation) throws Throwable {
						AsyncCallback<FilterOptions> callback = (AsyncCallback) invocation.getArguments()[0];
						
						HashMap<Integer, String> genreOptions = new HashMap<Integer, String>();
						genreOptions.put(1, "Action");
						genreOptions.put(2, "Adventure");
						genreOptions.put(3, "Comedy");
						
						HashMap<Integer, String> languageOptions = new HashMap<Integer, String>();
						languageOptions.put(1, "German");
						languageOptions.put(2, "English");
						
						HashMap<Integer, String> countryOptions = new HashMap<Integer, String>();
						countryOptions.put(1, "Switzerland");
						countryOptions.put(2, "Germany");
						countryOptions.put(3, "Austria");
						
						FilterOptions options = new FilterOptions();
						options.setGenreSelectOptions(genreOptions);
						options.setLanguageSelectOptions(languageOptions);
						options.setCountrySelectOptions(countryOptions);
						
					     callback.onSuccess(options);
						return null;
					}
				}).when(filmService).getSelectOptions((AsyncCallback<FilterOptions>) Mockito.any());
		
		when(urlToken.createUrlToken(normalFilter, false)).thenReturn("demoToken");
		when(urlToken.createUrlToken(mapFilter, false)).thenReturn("demoToken");	
		when(urlToken.parseFilter("demoToken")).thenReturn(normalFilter);
		
		//After all mocks are setup, create the instance of the filterPresenter
		filterPresenter = new FilterPresenterImpl(eventBus, filterView, filmDataModel, filmService, browserUtil, urlToken);
		
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
	public void testUpdateFilterFieldsInView()
	{
		filterPresenter.updateFilterFieldsInView(normalFilter);
		verify(filterView).setName(normalFilter.getName());
		verify(filterView).setLengthSlider(normalFilter.getLengthStart(), normalFilter.getLengthEnd());
		verify(filterView).setYearSlider(normalFilter.getYearStart(), normalFilter.getYearEnd());
		verify(filterView).setSelectedCountryOptions(normalFilter.getCountryIds());
		verify(filterView).setSelectedLanguageOptions(normalFilter.getLanguageIds());
		verify(filterView, times(2)).setSelectedGenreOptions(normalFilter.getGenreIds());
	}
	
	
	@Test
	public void testOnSearch(){
		filterPresenter.onSearch();
		//times(2) because the first time is in the constructor
		verify(filmDataModel, times(2)).setAppliedFilter(Matchers.eq(normalFilter));
		verify(filmDataModel, times(2)).setAppliedMapFilter(Matchers.eq(mapFilter));
		
		//times(2) because matchers fails to make a difference between filterAppliedEvent and FilterOptionsLoadedEvent
		verify(eventBus, times(2)).fireEvent(Matchers.any(FilterAppliedEvent.class));
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSetupMultiSelects(){
		verify(filterView).setCountryOptions((HashMap<Integer, String>) Matchers.anyMap());
		verify(filterView).setGenreOptions((HashMap<Integer, String>) Matchers.anyMap());
		verify(filterView).setLanguageOptions((HashMap<Integer, String>) Matchers.anyMap());
		
		verify(filmDataModel).setCountryOptions((HashMap<Integer, String>) Matchers.anyMap());
		verify(filmDataModel).setGenreOptions((HashMap<Integer, String>) Matchers.anyMap());
		verify(filmDataModel).setLanguageOptions((HashMap<Integer, String>) Matchers.anyMap());
		
		verify(eventBus).fireEvent(Matchers.any(FilterOptionsLoadedEvent.class));
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
	public void testSetFilter()
	{
		filterPresenter.setFilter("");
		verify(browserUtil).replaceHistoryItem(Matchers.anyString(), Matchers.anyBoolean());
		
		
		filterPresenter.setFilter("demoToken");
		verify(eventBus).addHandler(Matchers.eq(FilterOptionsLoadedEvent.getType()), Matchers.any(FilterOptionsLoadedHandler.class));
		
		filterPresenter.setFilterOptionsLoaded(true);
		filterPresenter.setFilter("demoToken");
		
		verify(filterView).setName(normalFilter.getName());
		verify(filterView).setLengthSlider(normalFilter.getLengthStart(), normalFilter.getLengthEnd());
		verify(filterView).setYearSlider(normalFilter.getYearStart(), normalFilter.getYearEnd());
		verify(filterView, times(2)).setSelectedGenreOptions(normalFilter.getGenreIds());
		verify(filterView).setSelectedLanguageOptions(normalFilter.getLanguageIds());
		verify(filterView).setSelectedCountryOptions(normalFilter.getCountryIds());
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
	
	@Test
	public void testGenerateFbUrl() {
		String basicUrl = "http://sprint5-dot-se-team-se7en.appspot.com/#table?sb=0&le=0:600&ye=1890:2015";
		String fullUrl = "http://sprint5-dot-se-team-se7en.appspot.com/#table?sb=0&na=ads&le=262:494&ye=1917:1947&ge=20&la=104&co=114";
		
		// assert that both urls are correctly converted
		assertEquals(
			filterPresenter.generateFbUrl(basicUrl), 
			"https://www.facebook.com/dialog/share?app_id=1664502907095620&display=page&href=http%3A%2F%2Fse-team-se7en.appspot.com%2F%23table%3Fsb=1%26le=0%3A600%26ye=1890%3A2015&redirect_uri=http%3A%2F%2Fse-team-se7en.appspot.com%2F%23table%3Fsb=1%26le=0%3A600%26ye=1890%3A2015"
		);
		assertEquals(
			filterPresenter.generateFbUrl(fullUrl), 
			"https://www.facebook.com/dialog/share?app_id=1664502907095620&display=page&href=http%3A%2F%2Fse-team-se7en.appspot.com%2F%23table%3Fsb=1%26na=ads%26le=262%3A494%26ye=1917%3A1947%26ge=20%26la=104%26co=114&redirect_uri=http%3A%2F%2Fse-team-se7en.appspot.com%2F%23table%3Fsb=1%26na=ads%26le=262%3A494%26ye=1917%3A1947%26ge=20%26la=104%26co=114"
		);
	}
}
