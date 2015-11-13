package ch.uzh.se.se7en.junit.client.presenter;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.MapPresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

@RunWith(JukitoRunner.class)
public class MapPresenterTest {

	
	MapPresenterImpl mapPresenter;
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	EventBus eventBus;
	@Inject
	MapView mapView;
	@Inject
	HasWidgets container;

	@Inject FilmListServiceAsync filmService;

	List<DataTableEntity> entities;
	List<Country> countries;
	int[] filmsInEachYear;

	@Before
	public void setup() {
		// Creating demo data to test the methods
		countries = new ArrayList<Country>();

		// filling the years array with 1 production in the first year, 2 in the
		// second, 3 in the third...
		filmsInEachYear = new int[126];
		for (int i = 0; i < filmsInEachYear.length; i++) {
			filmsInEachYear[i] = i + 1;
		}

		Country c1 = new Country("Switzerland");
		Country c2 = new Country("Germany");
		Country c3 = new Country("Austria");

		c1.setNumberOfFilms(filmsInEachYear);
		c2.setNumberOfFilms(filmsInEachYear);
		// c1 has no film productions -> empty array -> c1 should not be in the
		// entities array
		c3.setNumberOfFilms(new int[126]);

		countries.add(c1);
		countries.add(c2);
		countries.add(c3);

		// Creating the goal for the setGeoChart method
		entities = new ArrayList<DataTableEntity>(countries.size());
		entities.add(new DataTableEntity("Switzerland", 8001));
		entities.add(new DataTableEntity("Germany", 8001));

		// because filmDataModel is a mock,
		// when the method getCountryList of the mock is called, then the
		// predefined list countries is returned
		when(filmDataModel.getCountryList()).thenReturn(countries);
		when(filmDataModel.getAppliedFilter()).thenReturn(new FilmFilter());
		when(filmDataModel.getAppliedMapFilter()).thenReturn(new FilmFilter());
		
		// because mapView is a mock
		// when the method getMinYear or getMaxYear of the mock is called return
		// the sepcified years.
		when(mapView.getMinYear()).thenReturn(1890);
		when(mapView.getMaxYear()).thenReturn(2015);


		//imitate the onSuccess method of the rpc call getCountryList()
		doAnswer(new Answer<List<Country>>(){
			@Override
			public List<Country> answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<List<Country>> callback = (AsyncCallback) invocation.getArguments()[1];
				FilmFilter filter = (FilmFilter) invocation.getArguments()[0];
				callback.onSuccess(countries);
				return null;
			}
		}).when(filmService).getCountryList(Matchers.any(FilmFilter.class),(AsyncCallback<List<Country>>) Mockito.any());

		mapPresenter = new MapPresenterImpl(mapView, eventBus, filmService, filmDataModel);
	}

	@Test
	public void testBind() {
		verify(mapView).setPresenter(mapPresenter);
	}

	@Test
	public void testGo() {
		mapPresenter.go(container);
		verify(container).clear();
		verify(container).add(mapView.asWidget());
	}

	@Test
	public void testOnRangeSliderChanged() {
		// Test that when the range slider is changed, the geochart & the
		// filmdata model is updated
		mapPresenter.onRangeSliderChanged();
		// TTwo times because updateGeoChart is called 1 time in the
		// constructor
		verify(filmDataModel, times(2)).setCountryDataTable(Matchers.eq(entities));
		verify(mapView, times(2)).setGeoChart(Matchers.eq(entities));
	}
	
	@Test
	public void testSetupMapUpdate()
	{
		//verify that eventHandler is added
		verify(eventBus).addHandler(Matchers.eq(FilterAppliedEvent.getType()), Matchers.any(FilterAppliedHandler.class));
	}
	
	@Test
	public void testFetchData()
	{
		mapPresenter.fetchData();
		verify(filmDataModel).setCountryList(Matchers.eq(countries));
		
		//3 Times because: 1st from Constructor (empty), 2nd from start loading (empty), 3rd from updating the map with the result
		verify(filmDataModel, times(3)).setCountryDataTable(Matchers.eq(entities));
		verify(mapView, times(3)).setGeoChart(Matchers.eq(entities));
	}


	@Test
	public void testUpdateGeoChart() {
		// Test that when the method updateGeoChart is called, the geochart &
		// the filmdata model is updated
		mapPresenter.updateGeoChart();
		// Two times because updateGeoChart is called 1 time in the
		// constructor
		verify(filmDataModel, times(2)).setCountryDataTable(Matchers.eq(entities));
		verify(mapView, times(2)).setGeoChart(Matchers.eq(entities));
	}

}
