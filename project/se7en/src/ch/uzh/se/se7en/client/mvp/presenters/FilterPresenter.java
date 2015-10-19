package ch.uzh.se.se7en.client.mvp.presenters;

public interface FilterPresenter extends RootPresenter {
	/**
	Handles the information from the filterView that the user clicked on the search button.
	@author Nicolas Küchler
	@pre 	filmDataModel != null
	@post	database search is started
	 */
	public void onSearch(); 
	
	/**
	Handles the information from the filterView that the user clicked on the clear button.
	@author Nicolas Küchler
	@pre 	-
	@post	Filter Boxes from the view are empty
	 */
	public void onClear();
	
	/**
	Is responsible for enabling/disabling the filter boxes for map/table view
	Changes the appliedFilter Box for the map/table view
	@author Nicolas Küchler
	@pre	-
	@post	filterBoxes are enabled/disabled according to the mode, appliedFilter Box is set according to the mode
	@param 	mode mode.equals(Tokens.MAP) || mode.equals(Tokens.TABLE) 
	 */
	public void setMode(String mode);
	
	/**
	Provides the filterPresenter with the information of the current data loading state
	@author Nicolas Küchler
	@pre	filmDataModel != null
	@post	presenter handled the change of the loading state
	@param state state.equals(LoadingStates.DEFAULT) || state.equals(LoadingStates.LOADING) || state.equals(LoadingStates.ERROR) || state.equals(LoadingStates.SUCCESS)
	 */
	public void setLoadingState(String state);
}
