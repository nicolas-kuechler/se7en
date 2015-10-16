package ch.uzh.se.se7en.client.mvp.presenters;

public interface MapPresenter extends RootPresenter {
	//For whenever the mapview needs data, there must be a method in this interface
	public void onNewMapDataNeeded();
	
	//to implement: when a country on the geochart is selected, make a server call and load the genreData to put it in the table and piechart

}
