package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwt.charts.client.DataTable;

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
	Allows the mapPresenter to get access to the current selection from the geoChart
	@author Nicolas Küchler
	@pre 	geoChart != null && presenter != null
	@post 	geoChart == geoChart @pre
	@return	row index of selected country from datatable  	
	 */
	public int getGeoChartSelection();
	
	/**
	Allows the mapPresenter to get and set the value of the yearSlider
	@author Nicolas Küchler
	@pre	yearSlider != null && presenter != null
	@post	yearSlider == yearSlider @pre
	@return	Objects that allows syntax to get access: getYearSlider().setValue(range) or getYearSlider().getValue()
	 */
	public HasValue<Range> getYearSlider();
	
	/**
	Provides access to the Ui Component GenreTable for the mapPresenter
	@author Nicolas Küchler
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
	public void setGenrePieChart(DataTable genres);
	
}
