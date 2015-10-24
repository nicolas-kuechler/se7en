/**
 * 
 */
package ch.uzh.se.se7en.client.mvp;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModelImpl;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;
import ch.uzh.se.se7en.client.mvp.presenters.impl.FilterPresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.MapPresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.TablePresenterImpl;
import ch.uzh.se.se7en.client.mvp.presenters.impl.WelcomePresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;
import ch.uzh.se.se7en.client.mvp.views.impl.FilterViewImpl;
import ch.uzh.se.se7en.client.mvp.views.impl.MapViewImpl;
import ch.uzh.se.se7en.client.mvp.views.impl.TableViewImpl;
import ch.uzh.se.se7en.client.mvp.views.impl.WelcomeViewImpl;
import ch.uzh.se.se7en.client.rpc.FilmListExportService;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;

/**
Provides central access to all the application wide, resource intensive objects.
@author Nicolas Küchler
*/
public class ClientFactoryImpl implements ClientFactory {
	
	private static EventBus eventBus;
	private static FilmListServiceAsync filmListService;
	private static FilmListExportServiceAsync filmListExportService; 
	private static FilmDataModelImpl filmDataModel;
	private static int year;
	
	private static MapView mapView;
	private static TableView tableView;
	private static FilterView filterView;
	private static WelcomeView welcomeView;
	
	private static MapPresenter mapPresenter;
	private static TablePresenter tablePresenter;
	private static FilterPresenter filterPresenter;
	private static WelcomePresenter welcomePresenter;
	

	@Override
	public EventBus getEventBus() {
		if (eventBus == null)
		{
			eventBus = new SimpleEventBus();
		}
		return eventBus;
	}

	@Override
	public FilmListServiceAsync getFilmListServiceAsync() {
		if (filmListService == null) 
		{
			filmListService = GWT.create(FilmListService.class);
		}
		return filmListService;
	}
	
	@Override
	public FilmListExportServiceAsync getFilmListExportServiceAsync() {
		if (filmListExportService == null) 
		{
			filmListExportService = GWT.create(FilmListExportService.class);
		}
		return filmListExportService;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getCurrentYear() {
		if (year == 0)
		{
			Date date = new Date();
			year = date.getYear(); //should be fine because its just a placeholder for gwt, gets replaced by the compiler with a js function
		}
		return year; 
	}

	@Override
	public MapView getMapView() {
		if(mapView == null)
		{
			mapView = new MapViewImpl();
		}
		return mapView;
	}

	@Override
	public TableView getTableView() {
		if(tableView == null)
		{
			tableView = new TableViewImpl();
		}
		return tableView;
	}

	@Override
	public FilterView getFilterView() {
		if(filterView == null)
		{
			filterView = new FilterViewImpl();
		}
		return filterView;
	}

	@Override
	public WelcomeView getWelcomeView() {
		if(welcomeView == null)
		{
			welcomeView = new WelcomeViewImpl();
		}
		return welcomeView;
	}

	@Override
	public FilmDataModel getFilmDataModel() {
		if(filmDataModel == null)
		{
			filmDataModel = new FilmDataModelImpl();
		}
		return filmDataModel;
	}

	@Override
	public TablePresenter getTablePresenter() {
		if(tablePresenter == null)
		{
			tablePresenter = new TablePresenterImpl(getTableView());
		}
		return tablePresenter;
	}

	@Override
	public MapPresenter getMapPresenter() {
		if(mapPresenter == null)
		{
			mapPresenter = new MapPresenterImpl(getMapView());
		}
		return mapPresenter;
	}

	@Override
	public WelcomePresenter getWelcomePresenter() {
		if(welcomePresenter == null)
		{
			welcomePresenter = new WelcomePresenterImpl(getWelcomeView());
		}
		return welcomePresenter;
	}

	@Override
	public FilterPresenter getFilterPresenter() {
		if(filterPresenter == null)
		{
			filterPresenter = new FilterPresenterImpl(getFilterView());
		}
		return filterPresenter;
	}



}
