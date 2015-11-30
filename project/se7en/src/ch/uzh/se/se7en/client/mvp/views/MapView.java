package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.shared.model.Genre;

public interface MapView extends IsWidget{
	/**
	Allows the mapPresenter to set itself within the mapView
	@author Nicolas Küchler
	@pre -
	@post mapPresenter == presenter
	@param presenter presenter instance that comes from the clientFactory
	 */
	public void setPresenter(MapPresenter presenter);
	
	/**
	Provides access to the Ui Component GeoChart for the mapPresenter
	@author Nicolas Küchler
	@pre 	presenter != null
	@post 	geoChart draw with countries
	@param 	countries DataTable instance with two columns (countryName, numberOfProductions) and a row for each country.
			countryName must follow the conventions for the gwt geochart: https://developers.google.com/chart/interactive/docs/gallery/geochart
	 */
	public void setGeoChart(List<DataTableEntity> countries);
	
	/**
	Retreives the data url of the geochart currently being displayed
	@author Cyrill Halter
	@pre 	geoChart != null && presenter != null
	@post 	-
	@return	the data url of the geochart currently being displayed	
	 */
	public String getGeoChartDownloadURI();
	
	
	/**
	Allows the mapPresenter to get access to the current selection from the geoChart
	@author Nicolas Küchler
	@pre 	geoChart != null && presenter != null
	@post 	geoChart == geoChart @pre
	@return	the id of the country which is selected in the geochart	
	 */
	public int getGeoChartSelectionCountryId();
	

	/**
	Provides access to the year Slider start year in the mapView
	@author Nicolas Küchler
	@pre yearSlider != null
	@post yearSlider == yearSlider@Pre
	@return the start year of the range slider
	 */
	public int getMinYear();
	
	/**
	Provides access to the year Slider end year in the mapView
	@author Nicolas Küchler
	@pre yearSlider != null
	@post yearSlider == yearSlider@Pre
	@return the end year of the range slider
	 */
	public int getMaxYear();
	
	/**
	provides access to the year Slider in the mapView to set a Range
	@author Nicolas Küchler
	@pre yearSlider != null
	@post yearSlider has range from yearStart to yearEnd selected
	@param yearStart the startYear which should be selected
	@param yearEnd the endYear which should be selected
	 */
	public void setYearRange(int yearStart, int yearEnd);
	
	/**
	Provides access to the Ui Component GenreTable for the mapPresenter
	@author Dominik Bünzli
	@pre 	presenter != null && genreTable != null
	@post	genreTable with genres is displayed
	@param	genres list of genre objects
	 */
	public void setGenreTable(List<Genre> genres);
	
	/**
	Provides access to the Ui Component GenrePieChart for the mapPresenter
	@author Nicolas Küchler
	@pre 	presenter != null && genrePieChart != null
	@post	genrePieChart with genres is displayed
	@param	genres datatable with two columns(genreName, percentage) and a row for each genre.
	 */
	public void setGenrePieChart(List<DataTableEntity> genres);
	
	/**
	Shows/Hides the GenreTable and the GenrePieChart
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param visible determines if the components are visible or not.
	 */
	public void setGenreVisible(boolean visible);

	/**
	When the download Url is ready, the download can be started from the Ui
	@author Cyrill Halter
	@pre	-
	@post	Download Window with zip File opened
	@param 	downloadUrl 
	 */
	public void startDownload(String result);
	
}
