
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.LoadingStates;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.shared.model.FilmFilter;


public class FilterPresenterImpl implements FilterPresenter {


	private ClientFactory clientFactory  = GWT.create(ClientFactory.class);
	private FilterView filterView;
	private FilmDataModel filmDataModel;

	public FilterPresenterImpl(final FilterView filterView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		filmDataModel.setPresenter(this);
		this.filterView = filterView;
		bind();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(filterView.asWidget());
	}


	@Override
	public void bind() {
		filterView.setPresenter(this);
	}

	@Override
	public void setLoadingState(String state) {
		//DEMO Code
		if (state.equals(LoadingStates.ERROR))
		{
			filterView.setLoadingState(LoadingStates.ERROR);
		}
		else if(state.equals(LoadingStates.LOADING))
		{
			filterView.setLoadingState(LoadingStates.LOADING);
		}
		else if(state.equals(LoadingStates.SUCCESS))
		{
			filterView.setLoadingState(LoadingStates.SUCCESS);
		}
		else if (state.equals(LoadingStates.DEFAULT))
		{
			filterView.setLoadingState(LoadingStates.DEFAULT);
		}
		//DEMO Code end
	}

	@Override
	public void onSearch() {
		// TODO Auto-generated method stub
		//DEMO to show how the presenter asks for new data
		FilmFilter filter = new FilmFilter();
		filter.setName(filterView.getNameBox().getValue());
		filmDataModel.search(filter);
		//Demo code end
	}

	@Override
	public void onClear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMode(String mode) {
		// TODO Auto-generated method stub
		
	}

}
