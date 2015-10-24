/**
 * 
 */
package ch.uzh.se.se7en.client.mvp;

import com.google.gwt.event.shared.EventBus;

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

/**
 Provides application wide access to the most resource consuming components, so that they have to be created only once.
 @author Nicolas Küchler
 
 */
public interface ClientFactory {
	public EventBus getEventBus();
	public FilmListServiceAsync getFilmListServiceAsync();
	public FilmListExportServiceAsync getFilmListExportServiceAsync();
	public MapView getMapView();
	public TableView getTableView();
	public FilterView getFilterView();
	public WelcomeView	getWelcomeView();
	public FilmDataModel getFilmDataModel();
	
	public TablePresenter getTablePresenter();
	public MapPresenter getMapPresenter();
	public WelcomePresenter getWelcomePresenter();
	public FilterPresenter getFilterPresenter();
	
}
