package ch.uzh.se.se7en.junit.server;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Class for static test utility methods for server side testing
 * 
 * @author Roland Schläfli
 */
public class TestUtil {

	/**
	 * A generic test util for mocking typed database queries
	 *
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param List<T>
	 *            returnValue A list of generic type which will be returned by
	 *            the query
	 * @return TypedQuery<T> mockjedQuery A mock of a typed query, returning the
	 *         value specified on getResultList() call
	 */
	public static <T> TypedQuery<T> mockQuery(List<T> returnValue) {
		TypedQuery<T> mockedQuery = (TypedQuery<T>) mock(TypedQuery.class);

		when(mockedQuery.getResultList()).thenReturn(returnValue);

		return mockedQuery;
	}
	
	/**
	 * A generic test util for mocking untyped database queries
	 *
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param List<T>
	 *            returnValue A list of generic type which will be returned by
	 *            the query
	 * @return Query mockjedQuery A mock of a query, returning the
	 *         value specified on getResultList() call
	 */
	public static <T> Query mockUntypedQuery(List<T> returnValue) {
		Query mockedQuery = mock(Query.class);

		when(mockedQuery.getResultList()).thenReturn(returnValue);

		return mockedQuery;
	}

	/**
	 * A generic test util for verifying that certain objects of a map were
	 * successfully persisted
	 * 
	 * @author Roland Schläfli
	 * @pre An entity manager has been initialized
	 * @post -
	 * @param EntityManager
	 *            manager A mocked EntityManager
	 * @param int
	 *            numEntities How many new test entities need to be checked for
	 *            persistence (not including already existing entities)
	 * @param int
	 *            times How many times each entity should have been persisted
	 * @param Map<String,
	 *            T> entityMap A map of entities to check for persistence
	 * @param String
	 *            entityBaseName The base name to which an identifying integer
	 *            will be appended
	 */
	public static <T> void verifyPersisted(EntityManager manager, int numEntities, int times, Map<String, T> entityMap,
			String entityBaseName) {
		for (int i = 1; i <= numEntities; i++) {
			verify(manager, times(times)).persist(entityMap.get(entityBaseName + i));
		}
	}
}
