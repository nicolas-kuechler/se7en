package ch.uzh.se.se7en.server.guice;

import com.google.inject.AbstractModule;

import ch.uzh.se.se7en.server.model.FilmListExportServiceImpl;
import ch.uzh.se.se7en.server.model.FilmListServiceImpl;
import ch.uzh.se.se7en.server.model.TriggerImportServiceImpl;

/**
 * Implements the module for server-side dependency injection via Guice
 * 
 * @author Roland Schläfli
 */
public class GirGuiceModule extends AbstractModule {

	/**
	 * Configures the bindings for guice (which classes are injected on which
	 * calls)
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 */
	@Override
	protected void configure() {
		bind(FilmListServiceImpl.class);
		bind(FilmListExportServiceImpl.class);
		bind(TriggerImportServiceImpl.class);
	}
}
