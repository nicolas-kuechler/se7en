package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.LoadingStates;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;
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
		filmDataModel.setPresenter(this); //needs to set itself as the presenter
		eventBus = clientFactory.getEventBus();
		this.tableView = tableView;
		bind();
		setLoadingState(LoadingStates.DEFAULT);
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
	public void onNewFilmDataNeeded() {
		//DEMO to show how the presenter asks for new data
		FilmFilter filter = new FilmFilter();
		filmDataModel.search(filter);
		//Demo code end
	}

	@Override
	public void setLoadingState(String state) {
		if (state.equals(LoadingStates.ERROR))
		{
			List<Film> error = new ArrayList<Film>(1);
			Film stub = new Film();
			stub.setName("Error Loading Data Please Try Again");
			error.add(stub);
			tableView.setTable(error);
			tableView.setLoadingState(LoadingStates.ERROR);
		}
		else if(state.equals(LoadingStates.LOADING))
		{
			List<Film> loading = new ArrayList<Film>(1);
			Film stub = new Film();
			stub.setName("Loading");
			loading.add(stub);
			tableView.setTable(loading);
			tableView.setLoadingState(LoadingStates.LOADING);
		}
		else if(state.equals(LoadingStates.SUCCESS))
		{
			tableView.setTable(filmDataModel.getFilms());
			tableView.setLoadingState(LoadingStates.SUCCESS);
		}
		else if (state.equals(LoadingStates.DEFAULT))
		{
			List<Film> defList = new ArrayList<Film>(1);
			Film stub = new Film();
			stub.setName("No Search Results");
			defList.add(stub);
			tableView.setTable(defList);
			tableView.setLoadingState(LoadingStates.DEFAULT);
		}
		
	}

}
