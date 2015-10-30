package ch.uzh.se.se7en.server.guice;

import com.google.inject.AbstractModule;

import ch.uzh.se.se7en.server.ServerUtil;
import ch.uzh.se.se7en.server.model.FilmListExportServiceImpl;
import ch.uzh.se.se7en.server.model.FilmListServiceImpl;
import ch.uzh.se.se7en.server.model.TriggerImportServiceImpl;

/**
 * 
 * @author Roland Schläfli
 *
 */
public class GirGuiceModule extends AbstractModule {
	
	  @Override 
	  protected void configure() {
		  bind(FilmListServiceImpl.class);
		  bind(FilmListExportServiceImpl.class);
		  bind(TriggerImportServiceImpl.class);
	  }

}
