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
	Handles the export of geochart after the user started the download by clicking on the button in the ui.
	@author Cyrill Halter
	@pre	mapView != null
	@post	Download of the zip started
	 */
	public void onDownloadStarted();
	
	/**
	@author Dominik Bünzli
	@pre	
	@post	if numberOfFilms is the same as the film before, no new rank is created. Else rank is iterated by one
	 */
	public int getRank(int numberOfFilms);
	
}
