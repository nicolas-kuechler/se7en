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
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.DataView;
import com.googlecode.gwt.charts.client.Selection;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.util.ArrayHelper;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.Genre;

public class MapViewImpl extends Composite implements MapView {

	private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

	interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
	}

	private MapPresenter mapPresenter;
	private ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
	private GeoChart geoChart;
	private GeoChartOptions geoChartOptions;
	private DataView dataView;
	private DataTable dataTable;
	private DataGrid genreTable;
	private PieChart genrePieChart;

	@UiField(provided = true)
	RangeSlider yearSlider;
	@UiField
	PanelBody panel;
	@UiField
	DataGrid<Film> dataGrid;

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

	@Override
	public void setPresenter(MapPresenter presenter) {
		this.mapPresenter = presenter;
	}


	@UiHandler("yearSlider")
	public void onRangeSlideStop(SlideStopEvent<Range> event) {
		mapPresenter.onRangeSliderChanged();
	}

	@Override
	public void setGeoChart(final List<DataTableEntity> countries) {
		chartLoader.loadApi(new Runnable() {

			public void run() {
				if (geoChart == null) {
					geoChart = new GeoChart();
					geoChartOptions = GeoChartOptions.create();
					geoChartOptions.setHeight(500);
					geoChartOptions.setWidth(900);
					panel.add(geoChart);
					GeoChartColorAxis colorAxis = GeoChartColorAxis.create();
					colorAxis.setColors("#8598C4", "#566EA4", "#39538D", "#243E79", "#122960");
					geoChartOptions.setColorAxis(colorAxis);
					// TODO Define GeoChart Colors
				}
				
				//Create new DataTable
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Productions");
				dataTable.addColumn(ColumnType.NUMBER, "Id");
				//add number of necessary rows
				dataTable.addRows(countries.size());
				
				for(int i = 0; i < countries.size(); i++)
				{
					dataTable.setValue(i, 0, countries.get(i).getName());
					dataTable.setValue(i, 1, countries.get(i).getValue());
					dataTable.setValue(i, 2, countries.get(i).getId());
				}
				//create dataView from dataTable
				dataView = DataView.create(dataTable);
				
				//hide id information in dataView
				dataView.hideColumns(ArrayHelper.createArray(new int[]{2}));
				
				geoChart.draw(dataView, geoChartOptions);
				
				//dataView.setColumns(ArrayHelper.createArray(new int[]{2}));
				geoChart.addSelectHandler(new SelectHandler(){

					@Override
					public void onSelect(SelectEvent event) {
							Selection selection =  geoChart.getSelection().get(0);
							ClientLog.writeMsg(dataTable.getValueString(selection.getRow(), 0)+" Id: "+dataTable.getValueString(selection.getRow(), 2));								
	
					}
				});

			}
		});
	}

	@Override
	public int getGeoChartSelection() {
		
		//TODO
		
		return 1;
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		// TODO refresh genreTable with new List
	}

	@Override
	public void setGenrePieChart(DataTable genres) {
		// TODO refresh genrePieChart with new DataTable
	}

	@Override
	public int getMinYear() {
		return (int)yearSlider.getValue().getMinValue();
	}

	@Override
	public int getMaxYear() {
		return (int)yearSlider.getValue().getMaxValue();
	}

	@Override
	public void setYearRange(int yearStart, int yearEnd) {
		yearSlider.setValue(new Range(yearStart, yearEnd));
	}

}
