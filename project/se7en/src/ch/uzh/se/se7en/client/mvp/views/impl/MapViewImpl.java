package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;
import org.gwtbootstrap3.extras.slider.client.ui.Range;
import org.gwtbootstrap3.extras.slider.client.ui.RangeSlider;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.TooltipType;
import org.gwtbootstrap3.extras.slider.client.ui.base.event.SlideStopEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataColumn;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.DataView;
import com.googlecode.gwt.charts.client.RoleType;
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

import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Genre;

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
	private boolean widthIsSet = false;
	private boolean placeholderIsSet = false;
	
	private Panel placeholderPieChart;
	private Panel placeholderGenreTable;
	private Label placeholderLabelChart;
	private Label placeholderLabelPie;
	
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
		setDimensions();
		yearSlider = new RangeSlider();
		genreTable = new DataGrid<Genre>();
		yearSlider.setMin(Boundaries.MIN_YEAR);
		yearSlider.setMax(Boundaries.MAX_YEAR);
		yearSlider.setValue(new Range(Boundaries.MIN_YEAR, Boundaries.MAX_YEAR));
		yearSlider.setWidth("60%");
		yearSlider.setTooltip(TooltipType.ALWAYS);

		initWidget(uiBinder.createAndBindUi(this));

		buildTable();
		
		
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
					geoChartOptions.setWidth((panelWidth*4)/10);
					geoChartOptions.setHeight((panelHeight*6)/10);
					geoChartOptions.hideLegend();
					panel.add(geoChart);
					
					if(placeholderIsSet){
						//ClientLog.writeMsg("placeholderIsSet");
					}else{
						placeholderPieChart= new Panel();
						placeholderGenreTable = new Panel();
						placeholderLabelChart= new Label();
						placeholderLabelPie= new Label();
						
						placeholderGenreTable.setWidth((panelWidth*2)/10+"px");
						placeholderGenreTable.setHeight((panelHeight*3)/10+"px");
						
						placeholderPieChart.setWidth((panelWidth*2)/10+"px");
						placeholderPieChart.setHeight((panelHeight*3)/10+"px");
						
						placeholderGenreTable.setStyleName("placeholderGenreTable");
						placeholderPieChart.setStyleName("placeholderPieChart");
						placeholderLabelChart.setStyleName("placeholderLabelChart");
						placeholderLabelPie.setStyleName("placeholderLabelPie");

						placeholderLabelPie.setText("Please select a country to show more details");

						placeholderPieChart.add(placeholderLabelPie);
						
						panel.add(placeholderGenreTable);
						panel.add(placeholderPieChart);
						placeholderIsSet = true;
					}
					
					GeoChartColorAxis colorAxis = GeoChartColorAxis.create();
					colorAxis.setColors("#8598C4", "#566EA4", "#39538D", "#243E79", "#122960");
					geoChartOptions.setColorAxis(colorAxis);
				}
				
				//Create new DataTable
				dataTableGeoChart = DataTable.create();
				dataTableGeoChart.addColumn(ColumnType.STRING, "Country");
				//Add Productions Colum which holds log(realNumberOfProduction)
				dataTableGeoChart.addColumn(ColumnType.NUMBER, "Productions");
				dataTableGeoChart.addColumn(ColumnType.NUMBER, "Id");
				//Add Tooltip role column to display real number of productions in tooltip
				dataTableGeoChart.addColumn(DataColumn.create(ColumnType.STRING, RoleType.TOOLTIP));
				
				//add number of necessary rows
				dataTableGeoChart.addRows(countries.size());
				
				for(int i = 0; i < countries.size(); i++)
				{
					dataTableGeoChart.setValue(i, 0, countries.get(i).getName());
					//TODO NK log function at the right place
					dataTableGeoChart.setValue(i, 1, Math.log(countries.get(i).getValue()));
					dataTableGeoChart.setValue(i, 2, countries.get(i).getId());
					dataTableGeoChart.setValue(i, 3, "Productions: "+countries.get(i).getValue());
				}
				//create dataView from dataTable
				dataViewGeoChart = DataView.create(dataTableGeoChart);
				
				//hide id information in dataView
				dataViewGeoChart.hideColumns(ArrayHelper.createArray(new int[]{2}));
				
				geoChart.draw(dataViewGeoChart, geoChartOptions);
				
				geoChart.removeAllHandlers();
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
	
	public void setDimensions(){
		if(widthIsSet){
			//ClientLog.writeMsg("WIDTH: "+panelWidth + "- HEIGHT: " + panelHeight);
		}else{
			panelWidth = Window.getClientWidth();
			panelHeight= Window.getClientHeight();
			widthIsSet=true;
			//ClientLog.writeMsg("WIDTH: "+panelWidth + "- HEIGHT: " + panelHeight);
		}
	}
	
	@Override
	public void setGenreVisible(boolean visible) {
		//TODO DB/NK Fading Genre Information?
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
				placeholderPieChart.setStyleName("placeholderPieChart");
				placeholderGenreTable.setStyleName("placeholderGenreTable");


			}
		}
	}
	
	/**
	Builds the Table for the GenreTable
	@author Dominik Bünzli
	@pre	
	@post	genreTableColumns are initialized and their width is set
	 */
	public void buildTable(){		
		
		genreProvider.addDataDisplay(genreTable);
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

		genreTable.setColumnWidth(rankColumn, 20, Unit.PCT);
		genreTable.addColumn(rankColumn, "Rank");
		genreTable.setColumnWidth(nameColumn, 45, Unit.PCT);
		genreTable.addColumn(nameColumn, "Name");
		genreTable.setColumnWidth(productionColumn, 35, Unit.PCT);
		genreTable.addColumn(productionColumn, "Productions");
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		placeholderGenreTable.setStyleName("placeholderHidden");
		genreTable.setWidth((panelWidth*2)/10+ "px");
		genreTable.setHeight((panelHeight*3)/10 + "px");
		genreProvider.setList(genres);
	}

	@Override
	public void setGenrePieChart(final List<DataTableEntity> genres) {
		placeholderPieChart.setStyleName("placeholderHidden");
		chartLoaderPieChart.loadApi(new Runnable(){
			@Override
			public void run() {
				if (pieChart == null) {
					pieChart = new PieChart();
					pieChart.setStyleName("pieChart");
					pieChartOptions = PieChartOptions.create();
					pieChartOptions.setWidth((panelWidth*2)/10);
					pieChartOptions.setHeight((panelHeight*3)/10);
					//hide legend
					pieChartOptions.setLegend(Legend.create(LegendPosition.RIGHT));
					//all slices under 3% are grouped together under "others"
					pieChartOptions.setSliceVisibilityThreshold(0.03);
					//TODO NK Need to define way more piechart colors (at least max depending on threshold in line above)
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
