package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gwtbootstrap3.client.ui.Panel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.mvp.views.widgets.AdPanel;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;

public class MapPresenterImpl implements MapPresenter {

	private MapView mapView;
	private EventBus eventBus;
	private FilmListServiceAsync filmListService;
	private FilmDataModel filmDataModel;
	private int rank =0;
	private int lastNumberOfFilms =0;
	private AdPanel adPanelRight;
	private AdPanel adPanelLeft;
	private Panel dataContainer;

	@Inject
	public MapPresenterImpl(MapView mapView, EventBus eventBus, FilmListServiceAsync filmListService,
			FilmDataModel filmDataModel) {
		this.mapView = mapView;
		this.eventBus = eventBus;
		this.filmListService = filmListService;
		this.filmDataModel = filmDataModel;
		adPanelLeft = new AdPanel();
		adPanelRight = new AdPanel();
		dataContainer = new Panel();
		dataContainer.setStyleName("dataContainer");
		adPanelLeft.setStyleName("adPanelLeft");
		adPanelRight.setStyleName("adPanelRight");
		bind();
		setupMapUpdate();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(dataContainer);
		dataContainer.add(adPanelLeft);
		dataContainer.add(mapView.asWidget());
		dataContainer.add(adPanelRight);
//		container.add(adPanelLeft);
//		container.add(mapView.asWidget());
//		container.add(adPanelRight);
		mapView.setGenreVisible(false);

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
		
		//makes sure genre info is not visible
		mapView.setGenreVisible(false);
		
		
		//Creating Filter (base comes from filmDataModel)
				FilmFilter filter = filmDataModel.getAppliedMapFilter();
		
		//add the id of the country which was selected to the filter
		Set<Integer> countryId = new HashSet<Integer>();
		countryId.add(mapView.getGeoChartSelectionCountryId());
		
		//Creating a copy of the appliedMapFilter with the id from the selected Country and the year information from the map yearRangeSlider
		FilmFilter genreFilter = new FilmFilter(filter.getName(), filter.getLengthStart(), filter.getLengthEnd(), 
						mapView.getMinYear(), mapView.getMaxYear(), countryId, filter.getLanguageIds(), filter.getGenreIds());
		
		
		//start rpc to get Genre List
		filmListService.getGenreList(genreFilter, new AsyncCallback<List<Genre>>(){
			@Override
			public void onFailure(Throwable caught) {
				ClientLog.writeErr("getGenreList failed");
			}
			@Override
			public void onSuccess(List<Genre> result) {
				updateGenre(result);
			}
		});
	}
	
	/**
	Updates the Genre Information (piechart & genreTable) in the view with a new list of genres
	@author Nicolas Küchler
	@pre	mapView != null
	@post	mapView.genreTable && mapView.pieChart display genres
	@param 	genres List of Genre that should be displayed
	 */
	public void updateGenre(List<Genre> genres) {
		//Creating and filling a DataTableEntity List which represents a DataTable in vanilla Java code
		List<DataTableEntity> entities = new ArrayList<DataTableEntity>(genres.size());
		for(Genre g : genres)
		{
			entities.add(new DataTableEntity(g.getName(), g.getNumberOfFilms()));
		}
		//makes sure genre info is visible
		mapView.setGenreVisible(true);
		
		//giving the piechart in the view a new entity list to display
		mapView.setGenrePieChart(entities);
		//giving the genreTable a new genre list to display
		mapView.setGenreTable(genres);
		
		lastNumberOfFilms = 0;
		rank = 0;
	}

	/**
	Registers to the eventbus for FilterAppliedEvents and whenever one occurs, then the appliedFilter is adjusted for the map, 
	an rpc call gets the CountryList from the server, the result is saved in the filmDataModel and the converted datatable 
	is given to the geochart in the mapView
	@author Nicolas Küchler
	@pre	eventBus != null && filmListService != null && mapView != null && filmDataModel != null
	@post	filmDataModel contains updated countryList && mapView geochart displays updated countryList
	 */
	public void setupMapUpdate(){
		//initialize with empty map
		filmDataModel.setCountryList(new ArrayList<Country>());
		updateGeoChart();

		//listens to FilterAppliedEvents in the EventBus
		eventBus.addHandler(FilterAppliedEvent.getType(), new FilterAppliedHandler(){
			@Override
			public void onFilterAppliedEvent(FilterAppliedEvent event) {
				fetchData();
			}
		});
	}

	/**
	Helper method to update the geochart whenever new information needs to be displayed. 
	The method takes the countryList from the filmDataModel and the yearRange information
	from the yearRangeSlider in the mapView and creates a new datatableentity list which is given to 
	the map view to be displayed.
	@author Nicolas Küchler
	@pre	filmDataModel != null && filmDataModel.getCountryList() != null && mapView != null
	@post	filmDataModel saved new dataTableentity list && mapView displays new Geochart
	 */
	public void updateGeoChart()
	{	
		//whenever the geochart is updated, the genre info needs to be hidden.
		mapView.setGenreVisible(false);
		
		//get country list according to currently applied filter from client side data model
		List<Country> countries = filmDataModel.getCountryList();

		//create new list of entities  which imitates a DataTable in vanilla java
		List<DataTableEntity> entities = new ArrayList<DataTableEntity>(countries.size());

		//get current year range slider information from the mapView
		int startYear = mapView.getMinYear();
		int endYear = mapView.getMaxYear();

		//represents the number of films produced in a country between startYear and endYear
		int numberOfProductions;

		for(Country c : countries)
		{
			//calculate the number of films produced between startYear and endYear
			numberOfProductions = c.getNumberOfFilms(startYear, endYear);

			if(numberOfProductions>0)
			{	//if at least one film was produced, add country to entity list
				entities.add(new DataTableEntity(c.getName(), numberOfProductions, c.getId()));
			}
		}
		//set the geochart with the new list
		mapView.setGeoChart(entities);
	}

	/**
	Method to fetch new filmdata for the map from the server
	@author Nicolas Küchler
	@pre 	-
	@post	mapView map is updated and server response is saved in filmdatamodel
	 */
	public void fetchData() {
		//update of the yearSlider in the mapView
		mapView.setYearRange(filmDataModel.getAppliedFilter().getYearStart(), filmDataModel.getAppliedFilter().getYearEnd());
		filmDataModel.setCountryList(new ArrayList<Country>());
		updateGeoChart();


		//as soon as new filter is applied, starts async call to server to get the new list of countries matching the adjusted filter
		filmListService.getCountryList(filmDataModel.getAppliedMapFilter(), new AsyncCallback<List<Country>>(){

			@Override
			public void onFailure(Throwable caught) {
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

	}

	@Override
	public int getRank(int numberOfFilms) {
		if(lastNumberOfFilms == numberOfFilms){
			return -1;
		}else{
			rank = rank +1;
			lastNumberOfFilms = numberOfFilms;
			return rank;
		}
	}
}
