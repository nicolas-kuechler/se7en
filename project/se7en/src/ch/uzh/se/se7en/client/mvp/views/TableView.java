package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.shared.model.Film;

public interface TableView extends IsWidget {
	/**
	Binds the tableView and the tablePresenter together.
	@author Nicolas Küchler
	@pre -
	@post this.tablePresenter == presenter
	@param presenter valid presenter instance
	 */
	public void setPresenter(TablePresenter presenter);
	
	/**
	Sets new films to the film table
	@author Nicolas Küchler
	@pre	-
	@post 	film table displays films
	@param 	films films != null && films.size() > 0
	 */
	public void setTable(List<Film> films, int start);
	
	/**
	Gives the table the information about how many entities it potentially contains.
	(It actually contains less due to the async nature of the table)
	@author Nicolas Küchler
	@pre	tablePresenter != null, dataGrid != null
	@post	row count of datagrid is updated
	@param	size the number of results which match a given search (without range limitations)
	 */
	public void setResultSize(int size);
	
	/**
	When the download Url is ready, the download can be started from the Ui
	@author Nicolas Küchler
	@pre	-
	@post	Download Window with Csv File opened
	@param 	downloadUrl 
	 */
	public void startDownload(String downloadUrl);
}
