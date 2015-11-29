package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Panel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.ClientLog;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.mvp.views.widgets.AdPanel;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * This class connects the TableView (UI of the Table) and the Model of Films.
 * Whenever a filter is applied, this class makes sure that the new filmlist is represented in the tableview.
 * @author Nicolas Küchler
 *
 */
public class TablePresenterImpl implements TablePresenter {

	private EventBus eventBus;
	private TableView tableView;
	private FilmDataModel filmDataModel;

	private FilmListServiceAsync filmListService;
	private FilmListExportServiceAsync filmListExportService;
	private AdPanel adPanelRight;
	private AdPanel adPanelLeft;
	private Panel dataContainer;
	

	@Inject
	public TablePresenterImpl(EventBus eventBus, TableView tableView, FilmDataModel filmDataModel,
			FilmListServiceAsync filmListService, FilmListExportServiceAsync filmListExportService) {
		this.eventBus = eventBus;
		this.tableView = tableView;
		this.filmDataModel = filmDataModel;
		this.filmListService = filmListService;
		this.filmListExportService = filmListExportService;
		adPanelLeft = new AdPanel();
		adPanelRight = new AdPanel();
		dataContainer = new Panel();
		dataContainer.setStyleName("dataContainer");
		adPanelLeft.setStyleName("adPanelLeft");
		adPanelRight.setStyleName("adPanelRight");
		bind();
		setupTableUpdate();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(dataContainer);
		dataContainer.add(adPanelLeft);
		dataContainer.add(tableView.asWidget());
		dataContainer.add(adPanelRight);
	}

	@Override
	public void bind() {
		tableView.setPresenter(this);
	}
	
	/**
	 * Make RPC with applied filter to trigger export to csv and retrieve download url
	 * 
	 * @author Cyrill Halter
	 * @pre applied filter set in filmDataModel
	 * @post -
	 * @param -
	 */
	@Override
	public void onDownloadStarted() {
		//trigger export to CSV and get download URL 
		filmListExportService.getFilmListDownloadUrl(filmDataModel.getAppliedFilter(), new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				ClientLog.writeErr("RPC failed");
			}
			@Override
			public void onSuccess(String result) {
				tableView.startDownload(result);
			}
		});
	}

	/**
	Sets up the listening to FilterAppliedEvents and whenever a FilterAppliedEvent is fired, the user is informed about the loading,
	an rpc call to the server gets the filmdata and when they are ready, they are given to the tableView to display them in the table.
	@author Nicolas Küchler
	@pre 	eventBus != null && filmListService != null &&filmDataModel != null
	@post	eventhandling is setup
	 */
	public void setupTableUpdate()
	{
		//initialize table with message
		updateTable(createPseudoFilmList("No Search Results Found"), 0);

		eventBus.addHandler(FilterAppliedEvent.getType(), new FilterAppliedHandler(){
			@Override
			public void onFilterAppliedEvent(FilterAppliedEvent event) {
				//get the data in the default range 0-50
				fetchData(0, 50);
			}
		});
	}

	/**
	Helper method that updates the table and saves the new list in the filmDataModel
	@author Nicolas Küchler
	@pre	filmDataModel != null && tableView != null
	@post	table is updated
	@param films films.size()>0
	 */
	public void updateTable(List<Film> films, int start)
	{
		if(films.get(0).getName().equals("GIR_QUERY_COUNT"))
		{
			//set number of rows
			tableView.setResultSize(films.get(0).getLength());			
			//delete pseudo film object from list
			films.remove(0);
		}
		tableView.setTable(films, start);
	}

	/**
	Helper Method to create a pseudo film list to display a message to the user (no results found, error, loading)
	@author Nicolas Küchler
	@pre	-
	@post	-
	@param 	message String with the information which should be displayed to the user
	@return List<Film> containing 1 film with the message from the parameter
	 */
	public List<Film> createPseudoFilmList(String message)
	{
		List<Film> pseudoFilm = new ArrayList<Film>();
		//Add the query count to the pseudo filmlist
		Film count = new Film("GIR_QUERY_COUNT");
		count.setLength(1);
		pseudoFilm.add(count);
		pseudoFilm.add(new Film(message));
		return pseudoFilm;
	}

	/**
	 Method to fetch new filmdata for the table from the server
	@author Nicolas Küchler
	@pre -
	@post tableView is updated and server response is saved in filmdatamodel
	 */
	public void fetchData(final int startRange, int numberOfResults) {

		filmListService.getFilmList(filmDataModel.getAppliedFilter(), startRange, numberOfResults, new AsyncCallback<List<Film>>(){
			@Override
			public void onFailure(Throwable caught) {
				//rpc did not get back to client -> display error to the user
				updateTable(createPseudoFilmList("Error while loading films, please try again"), 0);
			}
			@Override
			public void onSuccess(List<Film> result) {
				if(result.size()==0)
				{
					//no films match the filter, so the result is empty --> inform user with pseudoFilmList
					updateTable(createPseudoFilmList("No Search Results Found"), 0);
				}
				else
				{
					//result is back and contains films --> display films and save them to the FilmDataModel
					updateTable(result, startRange);
				}
			}
		});
	}

	@Override
	public void onTableRangeChanged(int startRange, int numberOfResults) {
		fetchData(startRange, numberOfResults);
	}

	@Override
	public void orderFilmListBy(String orderBy) {
		filmDataModel.getAppliedFilter().setOrderBy(orderBy);
	}
}
