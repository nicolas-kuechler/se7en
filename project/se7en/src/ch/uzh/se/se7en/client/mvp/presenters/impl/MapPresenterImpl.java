package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.LoadingStates;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;

public class MapPresenterImpl implements MapPresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private MapView mapView;
	private FilmDataModel filmDataModel;

	public MapPresenterImpl(final MapView mapView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		filmDataModel.setPresenter(this);
		this.mapView = mapView;
		bind();
		setLoadingStateGeoChart(LoadingStates.DEFAULT);
		setLoadingStateGenres(LoadingStates.DEFAULT);
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
	public void onRangeSliderChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCountrySelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGeoChart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGenre() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoadingStateGeoChart(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoadingStateGenres(String state) {
		// TODO Auto-generated method stub
		
	}

}
