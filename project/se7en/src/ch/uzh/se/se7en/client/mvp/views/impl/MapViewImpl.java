package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.gwtbootstrap3.extras.animate.client.ui.Animate;
import org.gwtbootstrap3.extras.animate.client.ui.constants.Animation;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.TooltipType;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopEvent;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Genre;

public class MapViewImpl extends Composite implements MapView{

	private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

	interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	private MapPresenter mapPresenter;
	private ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
	private GeoChart geoChart;
	private GeoChartOptions geoChartOptions;
	private DataGrid genreTable;
	private PieChart genrePieChart;
	
	@UiField (provided = true) RangeSlider yearSlider;
	@UiField PanelBody panel;

	@Inject
	public MapViewImpl() {
		yearSlider = new RangeSlider();
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		yearSlider.setWidth("900px");
		yearSlider.setTooltip(TooltipType.ALWAYS);
		initWidget(uiBinder.createAndBindUi(this));
		
		panel.setHeight("550px");
	}
	
	@UiHandler("yearSlider")
	public void onRangeSlideStop(SlideStopEvent<Range> event)
	{
		mapPresenter.onRangeSliderChanged();
	}

	@Override
	public void setPresenter(MapPresenter presenter) {
		this.mapPresenter = presenter;
	}

	@Override
	public void setGeoChart(final DataTable countries) {
		chartLoader.loadApi(new Runnable(){
			@Override
			public void run() {
				if(geoChart == null)
				{
					geoChart = new GeoChart();
					geoChartOptions = GeoChartOptions.create();
					geoChartOptions.setHeight(500);
					geoChartOptions.setWidth(900);
					panel.add(geoChart);
					//TODO Define GeoChart Colors
				}
				//TODO Fade Animation
				geoChart.draw(countries, geoChartOptions);
			}
		});
	}

	@Override
	public int getGeoChartSelection() {
		// TODO Return geoChartSelection
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
