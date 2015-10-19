package ch.uzh.se.se7en.client.mvp.presenters;

public interface MapPresenter extends RootPresenter {
	/**
	Whenever the RangeSlider in the view changes the range, the view calls this method which handles the change
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	geoChart in view is redrawn according to the new year range, genrePieChart in view is set to empty, genreTable in view is set to empty
	 */
	public void onRangeSliderChanged();
	
	/**
	Whenever the user selects a country in the geoChart, the view calls this method which handles the selection and loads the genreTable & genrePieChart
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	genreTable and genrePiechart in the view display the genres from the selected country
	 */
	public void onCountrySelected();
	
	/**
	Whenever there is new country data available this method handles the update
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	geoChart in view is redrawn according to the current data
	 */
	public void updateGeoChart();
	
	/**
	Whenever there is new genre data available this method handles the update
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	genreTable and genrePiechart in view is redrawn according to the current data
	 */
	public void updateGenre();
	
	/**
	Provides the presenter with information about the current loading state of the geoChart data
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	mapView knows about the current state
	@param	state state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS 
	 */
	public void setLoadingStateGeoChart(String state);
	
	/**
	Provides the presenter with information about the current loading state of the genre data
	@author Nicolas Küchler
	@pre	filmDataModel != null && mapView != null
	@post	mapView knows about the current state
	@param	state state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS 
	 */
	public void setLoadingStateGenres(String state);
}
