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
	public void setTable(List<Film> films);
	
	/**
	@author Nicolas Küchler
	@pre	-
	@post	tableView UI is in the state mode and display
	@param 	state == LoadingStates.DEFAULT || state == LoadingStates.LOADING || state == LoadingStates.ERROR || state == LoadingStates.SUCCESS
	 */
	public void setLoadingState(String state);

}
