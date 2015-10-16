package ch.uzh.se.se7en.client.mvp.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.charts.client.DataTable;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.LoadingTableEvent;
import ch.uzh.se.se7en.client.mvp.events.TableDataReadyEvent;
import ch.uzh.se.se7en.client.rpc.FilmServiceAsync;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;
import ch.uzh.se.se7en.shared.model.Genre;


//searchGenreList(int yearStart, int yearEnd, int rowOfDataTable)
//NEEDS TO BE IMPLEMENTED ROW MATCHING IN DATATABLE --> GET COUNTRY NAME ACCORDING TO ROW

// convertListToDataTable(List<Country> countries)
// NEEDS TO BE IMPLEMENTED

//ERROR HANDLING IN search(..) & searchGenreList(...) NEEDS TO BE IMPLEMENTED

//EVENTBUS FOR GETTING INFO WHEN DATA IS LOADED NEEDS TO BE IMPLEMENTED

public class FilmDataModel {
	
	private List<Film> films;
	private DataTable countries;
	private FilmFilter appliedFilter;
	private List<Genre> genres;
	
	private FilmServiceAsync filmService;
	private EventBus eventBus;
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	
	
	public FilmDataModel()
	{
		filmService = clientFactory.getFilmServiceAsync();
		eventBus = clientFactory.getEventBus();
	}
	
	/**
	Starts a server search according to the applied filter and updates the client side data model with the result. 
	Always both data-models (films & countries) are updated at the same time.
	@author Nicolas Küchler
	@pre	-
	@post 	appliedFilter == filter && films==filmService.getFilmList(filter) && countries == filmService.getCountryList(filter)
	@param	filter FilmFilter object which is applied for the search for new map and table data
	 */
	public void search(FilmFilter filter)
	{
		//1. change applied filter
		appliedFilter = filter;
		
		
		//2. call rpc Service getFilmList()
		
		//2.1 Fire Loading Table Event
		eventBus.fireEvent(new LoadingTableEvent()); //ATM it is not implemented, but within the table there could be an information about the loading process
		filmService.getFilmList(filter, new AsyncCallback<List<Film>>(){
			@Override
			public void onFailure(Throwable caught) {
				//ERROR HANDLING NEEDS TO BE DEFINED
				Window.alert("Error getFilmList: ERROR HANDLING NEEDS TO BE DEFINED");
			}
			@Override
			public void onSuccess(List<Film> result) {
				//3. setFilm List here
				films = result;
				eventBus.fireEvent(new TableDataReadyEvent());
				
			}
		});
		
		//4. Adjust Filter
		FilmFilter countryFilter = adjustFilter(filter, Country.YEAR_OFFSET, clientFactory.getCurrentYear(), filter.getCountries()); //TBD IF COUNTRY FILTER APPLIES FOR MAP ASWELL
		
		//5. call rpc Service getCountryList() with new Filter													
		filmService.getCountryList(countryFilter, new AsyncCallback<List<Country>>(){
			@Override
			public void onFailure(Throwable caught) {
				//ERROR HANDLING NEEDS TO BE DEFINED
				Window.alert("Error getCountryList: ERROR HANDLING NEEDS TO BE DEFINED");
			}
			@Override
			public void onSuccess(List<Country> result) {
				//6. convert List to DataTable
				//7. set DataTable here
				countries = convertListToDataTable(result);
			}
		});		
	}
	
	/**
	Provides access to the Country DataTable for the Google GeoChart
	@author Nicolas Küchler
	@pre countries != null
	@post -
	@return DataTable Object which can be used in the gwt google geochart
	 */
	public DataTable getCountries()
	{
		return countries;
	}
	
	/**
	Provides access to the FilmList for the table
	@author Nicolas Küchler
	@pre films != null
	@post -
	@return List<Film> which can be used in the table
	 */
	public List<Film> getFilms()
	{
		return films;
	}
	
	/**
	Provides access to the applied Filters
	@author Nicolas Küchler
	@pre appliedFilter != null
	@post -
	@return FilmFilter objects with all the informations about the currently applied filters.
	 */
	public FilmFilter getAppliedFilter()
	{
		return appliedFilter;
	}
	
	/**
	Provides access to the GenreList for the genretable
	@author Nicolas Küchler
	@pre genres != null
	@post -
	@return List<Genre> with all the Genres matching the current applied filter and the selected Country on the map and the years from the map range slider
	 */
	public List<Genre> getGenreList()
	{
		return genres;
	}
	
	/**
	Starts a server search according to the applied filter, the two years, and the selectedCountry info on the geoChart
	@author Nicolas Küchler
	@pre	-
	@post 	genres = filmService.getGenreList(...)
	@param	yearStart lower border for calculating numberOfFilms in GenreList
	@param	yearEnd		upper border for calculating numberOfFilms in GenreList
	@param	rowOfDataTable 	row information from the datatable (which country was clicked on), is going to matched to the country name
	 */
	public void searchGenreList(int yearStart, int yearEnd, int rowOfDataTable)
	{
		genres = null;
		List<String> countryList = new ArrayList<String>();
		//countryList.add()
		//
		// 	NEEDS TO BE IMPLEMENTED ROW MATCHING IN DATATABLE --> GET COUNTRY NAME ACCORDING TO ROW
		//
		FilmFilter filter = adjustFilter(appliedFilter, yearStart, yearEnd, countryList);
		filmService.getGenreList(filter, new AsyncCallback<List<Genre>>(){
			@Override
			public void onFailure(Throwable caught) {
				//ERROR HANDLING NEEDS TO BE DEFINED
				Window.alert("Error searchGenreList: ERROR HANDLING NEEDS TO BE DEFINED");
			}
			@Override
			public void onSuccess(List<Genre> result) {
				genres = result;
			}
		});
	}
	
	
	/**
	Converts List<Country> to a DataTable object for the Google GeoChart
	@author Nicolas Küchler
	@pre 	-
	@post 	-
	@param countries List of Country Objects which belong in the datatable
	@return DataTable created from the countries in the parameter
	 */
	private DataTable convertListToDataTable(List<Country> countries)
	{
		DataTable table = DataTable.create();
		//NEEDS TO BE IMPLEMENTED
		return table;
	}
	
	/**
	Adjusts the attributes yearStart, yearEnd and countries from a given FilmFilter Object.
	@author Nicolas Küchler
	@pre -
	@post -
	@param filter base FilmFilter which should be adjusted
	@param yearStart new year start value 
	@param yearEnd  new year end value
	@param countries  new countries value
	@return the adjusted FilmFilter
	 */
	private FilmFilter adjustFilter(FilmFilter filter, int yearStart, int yearEnd, List<String> countries)
	{
		FilmFilter adjustedFilter = new FilmFilter();
		
		adjustedFilter.setName(filter.getName());
		adjustedFilter.setLengthStart(filter.getLengthStart());
		adjustedFilter.setLengthEnd(filter.getLengthEnd());
		adjustedFilter.setYearStart(yearStart); //special
		adjustedFilter.setYearEnd(yearEnd); //special
		adjustedFilter.setCountries(countries); //special
		adjustedFilter.setLanguages(filter.getLanguages());
		adjustedFilter.setGenres(filter.getGenres());
		
		return adjustedFilter;
	}
	
	

}
