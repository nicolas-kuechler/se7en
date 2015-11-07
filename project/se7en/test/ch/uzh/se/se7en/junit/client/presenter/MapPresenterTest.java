package ch.uzh.se.se7en.junit.client.presenter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.ChartLoader;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.MapPresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.MapView;

@RunWith(JukitoRunner.class)
public class MapPresenterTest {

	@Inject 
	MapPresenterImpl mapPresenter;	
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	MapView mapView;
	@Inject
	HasWidgets container;
	
	@Inject
	ChartLoader chartLoader;
	
	
//	@Before
//	public void setup()
//	{
//		 when(mapPresenter.updateGeoChart()).thenReturn(1);
//		 when(mapPresenter.setupMapUpdate()).thenReturn(1);;
//	}
	
	@Test
	public void testBind() {
		verify(mapView).setPresenter(mapPresenter);
	}
	
	@Test
	public void testGo(){
		mapPresenter.go(container);
		verify(container).clear();
		verify(container).add(mapView.asWidget());
	}
	
	@Test
	public void testOnRangeSliderChanged()
	{
		mapPresenter.onRangeSliderChanged();
		verify(mapPresenter).updateGeoChart();
	}
	
	@Test
	public void testUpdateGeoChart()
	{
		
	}

}
