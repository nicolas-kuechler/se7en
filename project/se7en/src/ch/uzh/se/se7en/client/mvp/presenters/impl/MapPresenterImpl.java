package ch.uzh.se.se7en.client.mvp.presenters.impl;

import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Country;

public class MapPresenterImpl implements MapPresenter {
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private MapView mapView;
	private EventBus eventBus;
	private FilmListServiceAsync filmListService;
	private FilmDataModel filmDataModel;
	

	public MapPresenterImpl(final MapView mapView)
	{
		filmDataModel = clientFactory.getFilmDataModel();
		eventBus = clientFactory.getEventBus();
		filmListService = clientFactory.getFilmListServiceAsync();
		this.mapView = mapView;
		bind();
		setupMapUpdate();
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
		// 1. get RangeSlider Info
		// 2. Create new DataTable from List<Country> FilmDataModel with RangeSliderYear Info
		// 3. mapView.setgeoChart(DataTable)
	}

	@Override
	public void onCountrySelected() {
		// TODO Auto-generated method stub
		// 1. clear genreTable & genrePieChart
		// 2. get year range from view
		// 3. get Information which Country was selected (getGeoChartSelection() tbd if row index or what)
		// 4. getAppliedFilter Info from filmDataModel
		// 5. start rpc call 
		// 6. setGenretable, setGenrePieChart
	}
	
	private void setupMapUpdate(){
		//listens to FilterAppliedEvents in the EventBus
		eventBus.addHandler(FilterAppliedEvent.getType(), new FilterAppliedHandler(){
			@Override
			public void onFilterAppliedEvent(FilterAppliedEvent event) {
				//as soon as new filter is applied, starts async call to server to get the new list of countries matching the filter
				filmListService.getCountryList(filmDataModel.getAppliedFilter(), new AsyncCallback<List<Country>>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO ERROR HANDLING NEEDS TO BE IMPLEMENTED
						
					}

					@Override
					public void onSuccess(List<Country> result) {
						//updates the list in the client side datamodel
						filmDataModel.setCountryList(result);
						
						//TODO Convert List to DataTable
						//TODO mapView.setGeochart(DataTable)
						//TODO Map Range Slider according to Slider from Filter 				
						//mapView.getYearSlider().setValue(Range.fromString(filmDataModel.getAppliedFilter().getYearStart() + ":" 
						//T														+ filmDataModel.getAppliedFilter().getYearEnd()));
					}
				});
			}
		});
	}

}
