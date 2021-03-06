package ch.mokath.uniknowledgerestapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author tv0g
* @author zue
* @author matteo113
*/

public class DBHelper {

	private Logger log = LoggerFactory.getLogger(DBHelper.class);

	/**
	 * Execute a SELECT request in entityClass associated table with defined
	 * key/value matchings in HashMap passed as parameter
	 * 
	 * @param wherePredicatesMap
	 *            Map of WHERE clause containing keys and expected values
	 * @param entityClass
	 *            Class of the queried Object
	 * @return Optional of desired object
	 */
	public <T> Optional<T> getEntityFromFields(Map<String, Object> wherePredicatesMap, Class<T> entityClass, EntityManager em) {

		final List<Predicate> wherePredicates = new ArrayList<Predicate>();

		// Create the Critera Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);

		// Add all WHERE predicates to the query's predicates
		for (final Map.Entry<String, Object> entry : wherePredicatesMap.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && value != null) {
				wherePredicates.add(criteriaBuilder.equal(from.get(key), value));
			}
		}

		criteriaQuery.where(criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()])));

		// Craft the final query
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);

		List<T> matchedObject = finalQuery.getResultList();

		if(matchedObject.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(matchedObject.get(0));
		}
	}
	
	
	
	/**
	 * Execute a SELECT request in entityClass associated table with defined
	 * key/value matchings in HashMap passed as parameter
	 * 
	 * @param wherePredicatesMap
	 *            Map of WHERE clause containing keys and expected values
	 * @param whereNotPredicatesMap
	 *            Map of notEqual WHERE clause containing keys and expected values
	 * @param entityClass
	 *            Class of the queried Object
	 * @return Optional of desired object
	 */
	public <T> Optional<T> getEntityFromFields(Map<String, Object> wherePredicatesMap, Map<String, Object> whereNotPredicatesMap, Class<T> entityClass, EntityManager em) {

		final List<Predicate> wherePredicates = new ArrayList<Predicate>();

		// Create the Critera Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);

		// Add all WHERE predicates to the query's predicates
		for (final Map.Entry<String, Object> entry : wherePredicatesMap.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && value != null) {
				wherePredicates.add(criteriaBuilder.equal(from.get(key), value));
			}
		}
		// Add all WHERE NOT predicates to the query's predicates
		for (final Map.Entry<String, Object> entry : whereNotPredicatesMap.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && value != null) {
				wherePredicates.add(criteriaBuilder.notEqual(from.get(key), value));
			}
		}

		criteriaQuery.where(criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()])));

		// Craft the final query
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);

		List<T> matchedObject = finalQuery.getResultList();

		if(matchedObject.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(matchedObject.get(0));
		}
	}
	
	
	
	/**
	 * Execute a SELECT request in entityClass associated table with defined
	 * key/value matchings in HashMap passed as parameter
	 * 
	 * @param wherePredicatesMap
	 *            Map of WHERE clause containing keys and expected values
	 * @param entityClass
	 *            Class of the queried Object
	 * @param em
	 *            Entity Manager
	 * @return List of desired objects
	 */
	public <T> List<T> getEntitiesFromFields(Map<String, Object> wherePredicatesMap, Class<T> entityClass, EntityManager em) {

		final List<Predicate> wherePredicates = new ArrayList<Predicate>();

		// Create the Criteria Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);

		// Add all WHERE predicates to the query's predicates
		for (final Map.Entry<String, Object> entry : wherePredicatesMap.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && value != null) {
				wherePredicates.add(criteriaBuilder.equal(from.get(key), value));
			}
		}

		criteriaQuery.where(criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()])));

		// Craft the final query
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);

		List<T> matchedObjects = finalQuery.getResultList();
		return matchedObjects;
	}
	
	public <T> List<T> getEntitiesFromFieldsOr(Map<String, Object> wherePredicatesMap, Class<T> entityClass, EntityManager em) {

		final List<Predicate> wherePredicates = new ArrayList<Predicate>();

		// Create the Criteria Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);

		// Add all WHERE predicates to the query's predicates
		for (final Map.Entry<String, Object> entry : wherePredicatesMap.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();

			if (key != null && value != null) {
				wherePredicates.add(criteriaBuilder.equal(from.get(key), value));
			}
		}

		criteriaQuery.where(criteriaBuilder.or(wherePredicates.toArray(new Predicate[wherePredicates.size()])));

		// Craft the final query
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);

		List<T> matchedObjects = finalQuery.getResultList();
		return matchedObjects;
	}
	
	public <T> List<T> getAllEntities(Class<T> entityClass, EntityManager em){
		
		// Create the Criteria Builder
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
				
		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);
		
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);
		
		List<T> matchedObjects = finalQuery.getResultList();
		return matchedObjects;
	}
	
	public <T> List<T> getAllEntitiesDescOrder(Class<T> entityClass, EntityManager em, String order){
		
		// Create the Criteria Builder
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
				
		// Link Query to Entity Class
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);
		criteriaQuery.orderBy(criteriaBuilder.desc(from.get(order)));
		
		TypedQuery<T> finalQuery = em.createQuery(criteriaQuery);
		
		List<T> matchedObjects = finalQuery.getResultList();
		return matchedObjects;
	}
	
}


