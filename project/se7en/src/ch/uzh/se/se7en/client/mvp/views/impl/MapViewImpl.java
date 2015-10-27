package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopEvent;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;

import ch.uzh.se.se7en.client.mvp.Boundaries;
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
	@UiField (provided = true) RangeSlider yearSlider;

	public MapViewImpl() {
		yearSlider = new RangeSlider();
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
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
	public void setGeoChart(final DataTable countries) {
		// TODO refresh geoChart with new Datatable
	}

	@Override
	public int getGeoChartSelection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HasValue<Range> getYearSlider() {
		return yearSlider;
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		// TODO refresh genreTable with new List
		
	}

	@Override
	public void setGenrePieChart(DataTable genres) {
		// TODO refresh genrePieChart with new DataTable
	}

}
