package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Genre;

public class MapViewImpl extends Composite implements MapView{

	private static MapViewImplUiBinder uiBinder = GWT.create(MapViewImplUiBinder.class);

	interface MapViewImplUiBinder extends UiBinder<Widget, MapViewImpl> {
	}
	
	private MapPresenter mapPresenter;

	public MapViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(MapPresenter presenter) {
		this.mapPresenter = presenter;
	}

	@Override
	public void setGeoChart(List<Country> countries) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGenreTable(List<Genre> genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPieChart(List<Genre> significantGenres) {
		// TODO Auto-generated method stub
		
	}

}
