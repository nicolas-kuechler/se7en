package ch.uzh.se.se7en.client.mvp.presenters;

public interface TablePresenter extends RootPresenter {
	/**
	Handles the export of films after the user started the download by clicking on the button in the ui.
	@author Nicolas Küchler
	@pre	tableView != null
	@post	Download of the csv started
	 */
	public void onDownloadStarted();
	
	/**
	
	@author Nicolas Küchler
	@pre	tableView != null
	@post	rpc started to get table data
	@param start start of visible range
	@param numberOfResults the number of results
	 */
	public void onTableRangeChanged(int startRange, int numberOfResults);
	
	/**
	Method to give the tablePresenter the information about the ordering that is applied in the table
	@author Nicolas Küchler
	@pre	tableView!=null
	@post	filmDataModel.getAppliedFilter().getOrderBy() == orderBy
	@param	orderBy in form: f.name desc
	 */
	public void orderFilmListBy(String orderBy);
}
