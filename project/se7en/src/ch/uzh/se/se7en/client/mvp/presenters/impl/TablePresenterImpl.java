package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * THIS CLASS CONTAINS DEMO CODE WHICH CANNOT BE USED FOR THE FINAL VERSION
 * @author Nicolas Küchler
 *
 */
public class TablePresenterImpl implements TablePresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private EventBus eventBus;
	private TableView tableView;
	private FilmDataModel filmDataModel;
	
	private FilmListServiceAsync filmListService;
	private FilmListExportServiceAsync filmListExportService;
	

	public TablePresenterImpl(final TableView tableView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		this.tableView = tableView;
		eventBus = clientFactory.getEventBus();
		filmListService = clientFactory.getFilmListServiceAsync();
		filmListExportService = clientFactory.getFilmListExportServiceAsync();
		bind();
		setupTableUpdate();
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(tableView.asWidget());
	}

	@Override
	public void bind() {
		tableView.setPresenter(this);
	}

	@Override
	public void onDownloadStarted() {
		// TODO Handle CSV Download 
		
		filmListExportService.getFilmListDownloadUrl(filmDataModel.getAppliedFilter(), new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(String result) {
				tableView.startDownload(result);
			}
		});
	}
	
	private void setupTableUpdate()
	{
		eventBus.addHandler(FilterAppliedEvent.getType(), new FilterAppliedHandler(){
			@Override
			public void onFilterAppliedEvent(FilterAppliedEvent event) {
				filmListService.getFilmList(filmDataModel.getAppliedFilter(), new AsyncCallback<List<Film>>(){
					@Override
					public void onFailure(Throwable caught) {
						// TODO ERROR HANDLING NEEDS TO BE DEFINED (e.g. --> tableView.setTable(new List with info that reload necessary)
					}
					@Override
					public void onSuccess(List<Film> result) {
						filmDataModel.setFilmList(result);
						tableView.setTable(result);
					}
				});
			}
		});
	}

}
