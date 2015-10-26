package ch.uzh.se.se7en.server;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Utils for server side classes
 * 
 * @author Roland Schläfli
 */
public class ServerUtil {
	private static EntityManagerFactory entityManagerFactory;

	/**
	 * Creates an EntityManagerFactory for connection building in other classes
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param -
	 * @return EntityManagerFactory An EntityManagerFactory for spawning entity
	 *         managers
	 */
	public static EntityManagerFactory createFactory() {
		// if createFactory is called and no factory has been initialized yet,
		// create one
		if (entityManagerFactory != null) {
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

			// create an entity manager factory
			entityManagerFactory = Persistence.createEntityManagerFactory("ch.uzh.se.se7en.hibernate", properties);
		}

		// return the entity manager factory that is saved in this class
		return entityManagerFactory;
	}
}
