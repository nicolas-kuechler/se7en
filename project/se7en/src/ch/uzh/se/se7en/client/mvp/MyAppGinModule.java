package ch.uzh.se.se7en.client.mvp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

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

public class MyAppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TablePresenter.class).to(TablePresenterImpl.class).in(Singleton.class);
		bind(MapPresenter.class).to(MapPresenterImpl.class).in(Singleton.class);
		bind(FilterPresenter.class).to(FilterPresenterImpl.class).in(Singleton.class);
		bind(WelcomePresenter.class).to(WelcomePresenterImpl.class).in(Singleton.class);
		
		bind(TableView.class).to(TableViewImpl.class).in(Singleton.class);
		bind(MapView.class).to(MapViewImpl.class).in(Singleton.class);
		bind(FilterView.class).to(FilterViewImpl.class).in(Singleton.class);
		bind(WelcomeView.class).to(WelcomeViewImpl.class).in(Singleton.class);
		
		
		bind(FilmDataModel.class).to(FilmDataModelImpl.class).in(Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
	}

}
