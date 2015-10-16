package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.TableDataReadyEvent;
import ch.uzh.se.se7en.client.mvp.events.TableDataReadyHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.FilmFilter;

public class TablePresenterImpl implements TablePresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private EventBus eventBus;
	private TableView tableView;
	private FilmDataModel filmDataModel;

	public TablePresenterImpl(final TableView tableView)
	{
		//FilmdataModel not setup
		filmDataModel = clientFactory.getFilmDataModel();
		eventBus = clientFactory.getEventBus();
		this.tableView = tableView;
		bind();
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(tableView.asWidget());
	}

	@Override
	public void bind() {
		tableView.setPresenter(this);
		
		//DEMO setup event handling, as soon as new table data is ready, this handler catches the event and updates the view.
		eventBus.addHandler(TableDataReadyEvent.getType(), new TableDataReadyHandler(){
			@Override
			public void onTableDataReadyEvent(TableDataReadyEvent event) {
				tableView.setTable(filmDataModel.getFilms());
			}
		});

	}

	@Override
	public void onNewFilmDataNeeded() {
		//DEMO to show how the presenter asks for new data
		FilmFilter filter = new FilmFilter();
		filmDataModel.search(filter);
		//Demo code end
	}

}
