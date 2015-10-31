package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;

import ch.uzh.se.se7en.client.mvp.Boundaries;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public class MapPresenterImpl implements MapPresenter {
	
	private MapView mapView;
	private EventBus eventBus;
	private FilmListServiceAsync filmListService;
	private FilmDataModel filmDataModel;
	
	@Inject
	public MapPresenterImpl(MapView mapView, EventBus eventBus, FilmListServiceAsync filmListService,
			FilmDataModel filmDataModel) {
		this.mapView = mapView;
		this.eventBus = eventBus;
		this.filmListService = filmListService;
		this.filmDataModel = filmDataModel;
		bind();
		setupMapUpdate();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(mapView.asWidget());
		
	}

	@Override
	public void bind() {
		mapView.setPresenter(this);
	}

	@Override
	public void onRangeSliderChanged() {
		updateGeoChart();
	}

	@Override
	public void onCountrySelected() {
		// TODO Auto-generated method stub
		// 1. clear genreTable & genrePieChart
		// 2. get year range from view
		// 3. get Information which Country was selected (getGeoChartSelection() tbd if row index or what)
		// 4. getAppliedFilter Info from filmDataModel
		// 5. start rpc call 
		// 6. setGenretable, setGenrePieChart
	}
	
	/**
	Registers to the eventbus for FilterAppliedEvents and whenever one occurs, then the appliedFilter is adjusted for the map, 
	an rpc call gets the CountryList from the server, the result is saved in the filmDataModel and the converted datatable 
	is given to the geochart in the mapView
	@author Nicolas Küchler
	@pre	eventBus != null && filmListService != null && mapView != null && filmDataModel != null
	@post	filmDataModel contains updated countryList && mapView geochart displays updated countryList
	 */
	private void setupMapUpdate(){
		//initialize with empty map
		filmDataModel.setCountryList(new ArrayList<Country>());
		updateGeoChart();
		
		//listens to FilterAppliedEvents in the EventBus
		eventBus.addHandler(FilterAppliedEvent.getType(), new FilterAppliedHandler(){
			@Override
			public void onFilterAppliedEvent(FilterAppliedEvent event) {
				//update of the yearSlider in the mapView
				mapView.getYearSlider().setValue(new Range(filmDataModel.getAppliedFilter().getYearStart(), filmDataModel.getAppliedFilter().getYearEnd()));
				
				//as soon as new filter is applied, starts async call to server to get the new list of countries matching the adjusted filter
				filmListService.getCountryList(adjustedFilter(), new AsyncCallback<List<Country>>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO ERROR HANDLING NEEDS TO BE IMPLEMENTED
						// Maybe logging to console?
						// User needs to be informed aswell
						
						// displays empty chart for user
						filmDataModel.setCountryList(new ArrayList<Country>());
						updateGeoChart();
					}

					@Override
					public void onSuccess(List<Country> result) {
						//updates the list in the client side datamodel
						filmDataModel.setCountryList(result);
						
						//updates the geochart in the view
						updateGeoChart();
					}
				});
				//TODO finding position in code for displaying empty geochart as loading information
				filmDataModel.setCountryList(new ArrayList<Country>());
				updateGeoChart();
			}
		});
	}
	
	/**
	Helper method to update the geochart whenever new information needs to be displayed. 
	The method takes the countryList from the filmDataModel and the yearRange information
	from the yearRangeSlider in the mapView and creates a new datatable which is given to 
	the map view to be displayed.
	@author Nicolas Küchler
	@pre	filmDataModel != null && filmDataModel.getCountryList() != null && mapView != null
	@post	filmDataModel saved new dataTable && mapView displays new Geochart
	 */
	private void updateGeoChart()
	{	
		//Operations with the google chart need to be done within a ChartLoader
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable(){
			@Override
			public void run() {
				//Getting the current country list from the filmdDataModel
				List<Country> countries = filmDataModel.getCountryList();
				
				//Create new data table
				DataTable dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Productions");
				
				//add number of necessary rows
				dataTable.addRows(countries.size());
				
				//convert the current range information from the range slider in the view
				int startYear = (int)mapView.getYearSlider().getValue().getMinValue();
				int endYear = (int)mapView.getYearSlider().getValue().getMaxValue();
				
				//loop through the country list and fill the datatable with the information
				for(int i = 0; i < countries.size(); i++)
				{
					dataTable.setValue(i, 0, countries.get(i).getName());
					dataTable.setValue(i, 1, countries.get(i).getNumberOfFilms(startYear, endYear));
				}
				
				//save the information in the filmddatamodel
				filmDataModel.setCountryDataTable(dataTable);
				
				//give the view the dataTable to draw the geochart
				mapView.setGeoChart(dataTable);
			}
		});
	}
	
	/**
	Helper method to adjust the currently appliedFilter for the map. Because the filtering of the year 
	for the map is done on clientside and the country filter doesn't apply in the map
	@author Nicolas Küchler
	@pre 	filmDataModel != null && filmDataModel.getAppliedFilter()!=null
	@post	filmDataModel.getAppliedFilter() == filmDataModel.getAppliedFilter() @pre
	@return FilmFilter that contains the boundaries for the years (because filtering of year 
			is done on clientside for the map) and the filter for the countries is removed.
	 */
	private FilmFilter adjustedFilter()
	{
		//taking applied filter from filmDataModel
		FilmFilter filter = new FilmFilter();
		FilmFilter appliedFilter = filmDataModel.getAppliedFilter();
		
		//copying the filter fields that are considered for the map as well
		filter.setName(appliedFilter.getName());
		filter.setLengthStart(appliedFilter.getLengthStart());
		filter.setLengthEnd(appliedFilter.getLengthEnd());
		filter.setGenres(appliedFilter.getGenres());
		filter.setLanguages(appliedFilter.getLanguages());
		
		//adjusting year range because filtering of that is done in the map on client side
		filter.setYearStart(Boundaries.MIN_YEAR);
		filter.setYearEnd(Boundaries.MAX_YEAR);
		
		//removing the country filter because in the map always all the countries should be considered
		filter.setCountries(null);
		
		return filter;
	}

}
