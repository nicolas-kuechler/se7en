package ch.uzh.se.se7en.client.mvp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.googlecode.gwt.charts.client.ChartLoader;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import ch.uzh.se.se7en.client.mvp.views.MapView;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;

@GinModules(AppGinModule.class)
public interface AppGinjector extends Ginjector {
	
	TablePresenter getTablePresenter();
	MapPresenter getMapPresenter();
	FilterPresenter getFilterPresenter();
	WelcomePresenter getWelcomePresenter();
	
	TableView getTableView();
	MapView getMapView();
	FilterView getFilterView();
	WelcomeView getWelcomeView();
	
	FilmDataModel getFilmDataModel();
	
	EventBus getEventBus();
	
	FilmListServiceAsync getFilmListServiceAsync();
	
	FilmListExportServiceAsync getFilmListExportServiceAsync();
	

}
