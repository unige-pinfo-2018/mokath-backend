/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.mokath.uniknowledgerestapi.dom.AuthInfos;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;

/**
 * @author tv0g
 *
 */
@Stateless
public class UsersServiceImpl implements UsersService {

	@PersistenceContext
	private EntityManager em;
	
	private Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);


	@Override
	public void createUser(@NotNull User user) {
		em.persist(user);
	}

	@Override
	public Optional<String> login(AuthInfos a) throws NoSuchAlgorithmException, InvalidKeySpecException {

		Optional<List<User>> matchedUsers = getUsersFrom("email", a.getEmail());

		// If no user matched, return an empty Optional object
		if (matchedUsers.isPresent() == true) {
			User u = matchedUsers.get().get(0);
			if (a.validatePassword(u.getPassword())) {
				
				// Create JWT with 24 hours expiration, return both the token and its signing key
				List<String> JWTokenComponents = a.createJWT(u.getId().toString(), 86400000);
				Token JWTObject = new Token(JWTokenComponents.get(0), JWTokenComponents.get(1), u);
				
				// Store JWT in DB
				em.persist(JWTObject);
				return Optional.of(JWTObject.getToken());
			}
		}

		return Optional.empty();
	}

	@Override
	public Boolean logout(String JWToken) {
		// TODO Auto-generated method stub
		return null;
	}

	private <T> Optional<List<User>> getUsersFrom(String field, T value) {

		// Create the Critera Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to User Class
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> from = criteriaQuery.from(User.class);

		// Modify and create the query to match given field/value pairs entries
		criteriaQuery.where(criteriaBuilder.equal(from.get(field), value));
		TypedQuery<User> finalQuery = em.createQuery(criteriaQuery);

		// Execute SELECT request on previous defined query predicates
		List<User> matchedUsers = finalQuery.getResultList();
		log.info(matchedUsers.toString());
		// If users list is not empty, return list of users wrapped in Optional object
		// else, return an empty Optional object
		return matchedUsers.isEmpty() ? Optional.empty() : Optional.of(matchedUsers);
	}

}
