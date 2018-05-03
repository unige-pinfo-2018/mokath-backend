package ch.mokath.uniknowledgerestapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRUDOperator {

	private Logger log = LoggerFactory.getLogger(CRUDOperator.class);

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

		// Execute SELECT request on previous defined query predicates
		try {
			T matchedObject = finalQuery.getSingleResult();
			return Optional.of(matchedObject);
		} catch (Exception e) {
			log.error("Exception thrown while querying object of type " + entityClass.getName()
					+ " with query criterias : " + criteriaQuery.getParameters().toString() + " : " + e.getMessage());
			return Optional.empty();
		}
	}
}
