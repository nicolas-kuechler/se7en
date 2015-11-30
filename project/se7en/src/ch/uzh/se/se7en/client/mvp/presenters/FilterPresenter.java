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
	Gives the AppControl the possibility to set a Filter. (Used for Filter in UrlToken parsing)
	@author Nicolas Küchler
	@pre	-
	@post	FilterViewImpl FilterFields have the values from the parsed filterToken
	
	@param filterToken matches the defined parsing pattern
	 */
	public void setFilter(String filterToken);
	
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
	Calling this method reloads the selectOptions from the server.
	@author Cyrill Halter
	@pre -
	@post Multiselects are reloaded
	 */
	public void setupMultiSelects();
	
	/**
	Opens a new Tab where the user can share his filters to facebook
	@author Dominik Bünzli
	@pre -
	@post Filter is shared
	 */
	public void onFacebook();
	
}
