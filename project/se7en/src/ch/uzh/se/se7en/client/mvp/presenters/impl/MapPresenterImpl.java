package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;

public class MapPresenterImpl implements MapPresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private EventBus eventBus;
	private MapView mapView;
	private FilmDataModel filmDataModel;

	public MapPresenterImpl(final MapView mapView)
	{
		//filmDataModel needs to be set up
		this.mapView = mapView;
		eventBus = clientFactory.getEventBus();
		bind();
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(mapView.asWidget());
	}

	@Override
	public void bind() {
		mapView.setPresenter(this);
	}

	@Override
	public void onNewMapDataNeeded() {
		// TODO Auto-generated method stub

	}

}
