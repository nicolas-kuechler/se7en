package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.DataView;
import com.googlecode.gwt.charts.client.Selection;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.util.ArrayHelper;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.Genre;

//TODO Dominik Bünzli positioning of the pieChart in the UI
public class MapViewImpl extends Composite implements MapView {

	private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

	interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
	}

	private MapPresenter mapPresenter;
	
	private ChartLoader chartLoaderGeoChart = new ChartLoader(ChartPackage.GEOCHART);
	private GeoChart geoChart;
	private GeoChartOptions geoChartOptions;
	private DataView dataViewGeoChart;
	private DataTable dataTableGeoChart;
	
	private ChartLoader chartLoaderPieChart = new ChartLoader(ChartPackage.CORECHART);
	private PieChart pieChart;
	private PieChartOptions pieChartOptions;
	private int panelWidth;
	private int panelHeight;


	@UiField(provided = true)
	RangeSlider yearSlider;
	@UiField
	PanelBody panel;
	@UiField (provided = true)
	DataGrid<Genre> genreTable;

	
	ListDataProvider<Genre> genreProvider = new ListDataProvider<Genre>();

	TextColumn<Genre> rankColumn;
	TextColumn<Genre> nameColumn;
	TextColumn<Genre> productionColumn;


	@Inject
	public MapViewImpl() {

		
		yearSlider = new RangeSlider();
		genreTable = new DataGrid<Genre>();
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		yearSlider.setWidth("80%");
		yearSlider.setTooltip(TooltipType.ALWAYS);
		buildTable();
		initWidget(uiBinder.createAndBindUi(this));
		



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
	
		chartLoaderGeoChart.loadApi(new Runnable() {

			public void run() {
				if (geoChart == null) {
					geoChart = new GeoChart();
					geoChart.setStyleName("geoChart");
					geoChartOptions = GeoChartOptions.create();
					panelWidth = panel.getOffsetWidth();
					panelHeight = panel.getOffsetHeight();
					
					geoChartOptions.setWidth((panelWidth*6)/10);
					geoChartOptions.setHeight((panelHeight*3)/10);

					panel.add(geoChart);
					GeoChartColorAxis colorAxis = GeoChartColorAxis.create();
					colorAxis.setColors("#8598C4", "#566EA4", "#39538D", "#243E79", "#122960");
					geoChartOptions.setColorAxis(colorAxis);
					// TODO Define GeoChart Colors
				}
				
				//Create new DataTable
				dataTableGeoChart = DataTable.create();
				dataTableGeoChart.addColumn(ColumnType.STRING, "Country");
				dataTableGeoChart.addColumn(ColumnType.NUMBER, "Productions");
				dataTableGeoChart.addColumn(ColumnType.NUMBER, "Id");
				//add number of necessary rows
				dataTableGeoChart.addRows(countries.size());
				
				for(int i = 0; i < countries.size(); i++)
				{
					dataTableGeoChart.setValue(i, 0, countries.get(i).getName());
					dataTableGeoChart.setValue(i, 1, countries.get(i).getValue());
					dataTableGeoChart.setValue(i, 2, countries.get(i).getId());
				}
				//create dataView from dataTable
				dataViewGeoChart = DataView.create(dataTableGeoChart);
				
				//hide id information in dataView
				dataViewGeoChart.hideColumns(ArrayHelper.createArray(new int[]{2}));
				
				geoChart.draw(dataViewGeoChart, geoChartOptions);
				
				//add a selectHandler to the map to detect users selecting a country on the map
				geoChart.addSelectHandler(new SelectHandler(){
					@Override
					public void onSelect(SelectEvent event) {
							mapPresenter.onCountrySelected();	 //inform the mapPresenter that a country was selected
					}
				});
			}
		});
	}

	@Override
	public int getGeoChartSelectionCountryId() {
		//get information from selection which row in datatable was selected
		int row = geoChart.getSelection().get(0).getRow();
		//get the country id information at the selected row
		return (int) dataTableGeoChart.getValueNumber(row, 2);	
	}
	
	@Override
	public void setGenreVisible(boolean visible) {
		//TODO Maybe doing it with fade animation
		if(visible)
		{
			if(genreTable!=null)
			{
				genreTable.setVisible(true);
			}	
			if(pieChart!=null)
			{
				pieChart.setVisible(true);
			}
		}
		else
		{
			if(genreTable!=null)
			{
				genreTable.setVisible(false);
			}
			if(pieChart!=null)
			{
				pieChart.setVisible(false);
			}
		}
	}
	
	public void buildTable(){		
		
		genreProvider.addDataDisplay(genreTable);
		genreTable.setWidth("30%");
		genreTable.setHeight("200px");
		genreTable.setAutoHeaderRefreshDisabled(true);
		
		rankColumn = new TextColumn<Genre>() {
			@Override
			public String getValue(Genre genreObject) {

				return Integer.toString(genreProvider.getList().indexOf(genreObject) + 1);
			}
		};
		

		nameColumn = new TextColumn<Genre>() {
			@Override
			public String getValue(Genre genreObject) {

				String value;
				if (genreObject.getName() != null) {
					value = genreObject.getName();
				} else {
					value = "";
				}
				return value;
			}
		};

		productionColumn = new TextColumn<Genre>() {
			@Override
			public String getValue(Genre genreObject) {

				String value;
				if (genreObject.getNumberOfFilms() != 0) {
					value = Integer.toString(genreObject.getNumberOfFilms());
				} else {
					value = "";
				}
				return value;
			}
		};

		genreTable.setColumnWidth(rankColumn, 9, Unit.PCT);
		genreTable.addColumn(rankColumn, "Rank");
		genreTable.setColumnWidth(nameColumn, 45, Unit.PCT);
		genreTable.addColumn(nameColumn, "Name");
		genreTable.setColumnWidth(productionColumn, 45, Unit.PCT);
		genreTable.addColumn(productionColumn, "Productions");
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		genreProvider.setList(genres);
	}

	@Override
	public void setGenrePieChart(final List<DataTableEntity> genres) {
		chartLoaderPieChart.loadApi(new Runnable(){
			@Override
			public void run() {
				if (pieChart == null) {
					pieChart = new PieChart();
					pieChart.setStyleName("pieChart");
					pieChartOptions = PieChartOptions.create();
					pieChartOptions.setHeight((panelHeight*5)/10);
					pieChartOptions.setWidth((panelWidth*3)/10);
					//hide legend
					pieChartOptions.setLegend(Legend.create(LegendPosition.RIGHT));
					//all slices under 10% are grouped together under "others"
					pieChartOptions.setSliceVisibilityThreshold(0.1);
					//TODO Need to define way more piechart colors (at least max depending on threshold in line above)
					pieChartOptions.setColors("#8598C4", "#566EA4", "#39538D", "#243E79", "#122960");
					pieChartOptions.setTitle("Genre Chart");
					panel.add(pieChart);
					
				}
				
				//build DataTable
				DataTable dataTablePieChart = DataTable.create();
				dataTablePieChart.addColumn(ColumnType.STRING, "Genre");
				dataTablePieChart.addColumn(ColumnType.NUMBER, "Productions");
				dataTablePieChart.addRows(genres.size());
				
				//Copy all elements in DataTable
				for(int i = 0; i < genres.size(); i++)
				{
					dataTablePieChart.setValue(i, 0, genres.get(i).getName());
					dataTablePieChart.setValue(i, 1, genres.get(i).getValue());
				}
				//Draw the piechart using the dataTable and the specified options
				pieChart.draw(dataTablePieChart, pieChartOptions);	
			}
		});
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
