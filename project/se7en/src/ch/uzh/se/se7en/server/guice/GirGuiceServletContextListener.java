package ch.uzh.se.se7en.server.guice;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
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
				final JpaPersistModule persist = new JpaPersistModule("ch.uzh.se.se7en.hibernate");
				persist.properties(getProperties());
				
				install(persist);
				filter("/*").through(PersistFilter.class);
				
				serve("/se7en/filmListService").with(FilmListServiceImpl.class);
				serve("/se7en/filmListExportService").with(FilmListExportServiceImpl.class);
				serve("/se7en/triggerImportService").with(TriggerImportServiceImpl.class);
			}
		}, new GirGuiceModule());
	}
	
	private Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();

		// set the properties of the db connection depending on
		// production/development environment
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			// app engine production environment
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:mysql://se-team-se7en:db/se7en");
			properties.put("javax.persistence.jdbc.user", "root");
			properties.put("javax.persistence.jdbc.password", "");
		} else {
			// local development environment
			properties.put("javax.persistence.jdbc.driver", "com.google.appengine.api.rdbms.AppEngineDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:rdbms://173.194.250.0/se7en");
			properties.put("javax.persistence.jdbc.user", "se7en");
			properties.put("javax.persistence.jdbc.password", "k1vttuIYXqOPe5!");
		}
		
		return properties;
	}

}
