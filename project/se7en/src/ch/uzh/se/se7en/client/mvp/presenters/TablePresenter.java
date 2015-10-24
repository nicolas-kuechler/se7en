package ch.uzh.se.se7en.client.mvp.presenters;

public interface TablePresenter extends RootPresenter {
	/**
	Handles the export of films after the user started the download by clicking on the button in the ui.
	@author Nicolas Küchler
	@pre	tableView != null
	@post	Download of the csv started
	 */
	public void onDownloadStarted();
	

}
