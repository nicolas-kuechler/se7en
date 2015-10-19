package ch.uzh.se.se7en.client.mvp.presenters.impl;

import org.gwtbootstrap3.extras.slider.client.ui.Range;

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
		//Call update GeoChart
	}

	@Override
	public void onCountrySelected() {
		// TODO Auto-generated method stub
		// 1. clear genreTable & genrePieChart (or from within filmDataModel)
		// 2. get year range from view
		// 3. get Information which Country was selected (getGeoChartSelection() tbd if row index or what)
		// 4. searchGenre(range.getMinValue, range.getMaxValue, row) from FilmDataModel
	}

	@Override
	public void updateGeoChart() {
		// TODO Auto-generated method stub
		// 1. clear Old geoChart ?
		// 2. get year range from view
		// 3. getCountries() from FilmDataModel tbd where conversion with year range list-> datatable takes places
		// 4. setGeoChart(dataTable) from view
	}

	@Override
	public void updateGenre() {
		// TODO Auto-generated method stub
		// ? clearOldGenreStuff
		// 1. is called from filmDataModel when rpc call from searchGenre is back
		// 2. setGenreTable(filmDataModel.getGenreList());
		// 3. setGenrePieChart(filmDataModel.getGenreDataTable());
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
