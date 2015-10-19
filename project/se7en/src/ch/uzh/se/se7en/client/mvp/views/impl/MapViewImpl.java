package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.geochart.GeoChart;

import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Genre;

public class MapViewImpl extends Composite implements MapView{

	private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

	interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	private MapPresenter mapPresenter;
	private GeoChart geoChart;
	private DataGrid genreTable;
	private PieChart genrePieChart;
	private RangeSlider yearSlider;

	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	//@UiHandler
	public void onRangeSlideStop(SlideStopEvent<Range> event)
	{
		// TODO method stub that reacts when yearSlider changes the range. 
		// (try out different options, on which event it should react. (see at bootstrap gwt 3 showcase) 
		// other option SlideChangeEvent, but testing if range changed to before because it fires a lot of events that are not necessary
	}

	@Override
	public void setPresenter(MapPresenter presenter) {
		this.mapPresenter = presenter;
	}

	@Override
	public void setGeoChart(DataTable countries) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGeoChartSelection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HasValue<Range> getYearSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGenrePieChart(DataTable genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoadingStateGeoChart(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoadingStateGenres(String state) {
		// TODO Auto-generated method stub
		
	}
}
