package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.LoadingStates;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;

/**
 * THIS CLASS CONTAINS DEMO CODE WHICH CANNOT BE USED FOR THE FINAL VERSION
 * @author Nicolas Küchler
 *
 */
public class TablePresenterImpl implements TablePresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private TableView tableView;
	private FilmDataModel filmDataModel;

	public TablePresenterImpl(final TableView tableView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		filmDataModel.setPresenter(this); //needs to set itself as the presenter
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
	public void setLoadingState(String state) {
		//DEMO CODE
		if (state.equals(LoadingStates.ERROR))
		{
			tableView.setTable(filmDataModel.getFilms());
			tableView.setLoadingState(LoadingStates.ERROR);
		}
		else if(state.equals(LoadingStates.LOADING))
		{
			tableView.setTable(filmDataModel.getFilms());
			tableView.setLoadingState(LoadingStates.LOADING);
		}
		else if(state.equals(LoadingStates.SUCCESS))
		{
			tableView.setTable(filmDataModel.getFilms());
			tableView.setLoadingState(LoadingStates.SUCCESS);
		}
		else if (state.equals(LoadingStates.DEFAULT))
		{
			tableView.setTable(filmDataModel.getFilms());
			tableView.setLoadingState(LoadingStates.DEFAULT);
		}
		//DEMO CODE END
	}

	@Override
	public void updateFilmTable() {
		//DEMO CODE
		tableView.setTable(filmDataModel.getFilms());
		//DEMO CODE END
	}

}
