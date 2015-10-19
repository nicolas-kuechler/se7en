package ch.uzh.se.se7en.client.mvp.presenters;

public interface TablePresenter extends RootPresenter {
	/**
	Handles the update of the table when there is new film data available
	@author Nicolas Küchler
	@pre	filmDataModel != null && tableView != null
	@post	filmTable in the tableView received the new film data
	 */
	public void updateFilmTable();
	
	/**
	Provides the presenter with information about the current loading state
	@author Nicolas Küchler
	@pre	filmDataModel != null && tableView != null
	@post	tableView knows about the current state
	@param	state state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS 
	 */
	public void setLoadingState(String state);
}
