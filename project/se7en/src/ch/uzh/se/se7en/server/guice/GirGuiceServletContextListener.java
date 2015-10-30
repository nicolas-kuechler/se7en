package ch.uzh.se.se7en.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import ch.uzh.se.se7en.server.model.FilmListExportServiceImpl;
import ch.uzh.se.se7en.server.model.FilmListServiceImpl;
import ch.uzh.se.se7en.server.model.TriggerImportServiceImpl;

/**
 * 
 * @author Roland Schläfli
 *
 */
public class GirGuiceServletContextListener extends GuiceServletContextListener {

	@Override 
	protected Injector getInjector() {
	    return Guice.createInjector(new ServletModule() {
	    	
	    	@Override
	    	protected void configureServlets() {
	    		serve("/se7en/filmListService").with(FilmListServiceImpl.class);
				serve("/se7en/filmListExportService").with(FilmListExportServiceImpl.class);
				serve("/se7en/triggerImportService").with(TriggerImportServiceImpl.class);
	    	}
	    }, new GirGuiceModule());
	  }

}
