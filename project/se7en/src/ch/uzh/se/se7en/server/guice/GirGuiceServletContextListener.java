package ch.uzh.se.se7en.server.guice;

import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import ch.uzh.se.se7en.server.FilmListExportServiceImpl;
import ch.uzh.se.se7en.server.FilmListServiceImpl;
import ch.uzh.se.se7en.server.TriggerImportServiceImpl;

/**
 * Creates the servlet context and maps guice servlets to their corresponding
 * url
 * 
 * @author Roland Schläfli
 */
public class GirGuiceServletContextListener extends GuiceServletContextListener {

	/**
	 * Initializes the JPA and GIR modules and maps servlets to urls
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 */
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {

			@Override
			protected void configureServlets() {
				// create the jpa module for hibernate and set the relevant
				// properties
				final JpaPersistModule persist = new JpaPersistModule("ch.uzh.se.se7en.hibernate")
						.properties(getProperties());
				install(persist);

				// filter all requests through the jpa module (create one
				// session per http request)
				filter("/*").through(PersistFilter.class);

				// servlet-to-url mapping
				serve("/se7en/filmListService").with(FilmListServiceImpl.class);
				serve("/se7en/filmListExportService").with(FilmListExportServiceImpl.class);
				serve("/se7en/triggerImportService").with(TriggerImportServiceImpl.class);
			}
		}, new GirGuiceModule());
	}

	/**
	 * Creates a map of properties for JPA, depending on the environment (local
	 * dev or app engine)
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @return Properties properties The map of properties
	 */
	private Properties getProperties() {
		Properties properties = new Properties();

		// check whether the app is deployed or in local development
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			// app engine production environment
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:mysql://se-team-se7en:db/se7en_v2");
			properties.put("javax.persistence.jdbc.user", "root");
			properties.put("javax.persistence.jdbc.password", "");
		} else {
			// local development environment
			properties.put("javax.persistence.jdbc.driver", "com.google.appengine.api.rdbms.AppEngineDriver");
			properties.put("javax.persistence.jdbc.url", "jdbc:google:rdbms://173.194.250.0/se7en_v2");
			properties.put("javax.persistence.jdbc.user", "se7en");
			properties.put("javax.persistence.jdbc.password", "k1vttuIYXqOPe5!");
		}

		return properties;
	}

}
