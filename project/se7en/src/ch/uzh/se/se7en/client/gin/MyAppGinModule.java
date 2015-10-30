package ch.uzh.se.se7en.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModelImpl;
import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.client.mvp.presenters.impl.TablePresenterImpl;

public class MyAppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TablePresenter.class).to(TablePresenterImpl.class).in(Singleton.class);
		bind(FilmDataModel.class).to(FilmDataModelImpl.class).in(Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		
		
		//TODO Configure the rest
	}

}
