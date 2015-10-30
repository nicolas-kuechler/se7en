package ch.uzh.se.se7en.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;

@GinModules(MyAppGinModule.class)
public interface MyAppGinjector extends Ginjector {
	
	TablePresenter getTablePresenter();
	
	TableView getTableView();
	
	FilmDataModel getFilmDataModel();
	
	EventBus getEventBus();
	
	FilmListServiceAsync getFilmListServiceAsync();
	
	FilmListExportServiceAsync getFilmListExportServiceAsync();
	

}
