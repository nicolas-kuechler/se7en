/**
 * 
 */
package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;

/**
 * @author vagrant
 *
 */
public class FilterPresenterImpl implements FilterPresenter {
	
	
	private ClientFactory clientFactory;
	private EventBus eventBus;
	private FilterView filterView;
	private FilmDataModel filmDataModel;
	
	public FilterPresenterImpl(final FilterView filterView)
	{
		//filmDataModel needs to be set up
		this.filterView = filterView;
		eventBus = clientFactory.getEventBus();
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
	public void onSendFilter() {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClearFilter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoadingState(String state) {
		// TODO Auto-generated method stub
		
	}

}
