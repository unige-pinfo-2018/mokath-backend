/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.mokath.uniknowledgerestapi.dom.AuthInfos;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;

/**
 * @author tv0g
 * @author zue
 */
@Stateless
public class UsersServiceImpl implements UsersService {

	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();

	@Override
	public void createUser(@NotNull User u) throws CustomException {
		try{
            if(isEmailOrUsernameAlreadyUsed(u.getEmail(), u.getUsername())) {
                throw new CustomException("Email or username is already used");
            } else {
                em.persist(u);
            }
        } catch (ConstraintViolationException cve) {
            throw new CustomException("invalid input");
        }
	}

	@Override
	public Optional<String> login(AuthInfos a) throws NoSuchAlgorithmException, InvalidKeySpecException {

		Map<String, Object> wherePredicatesMapForUser = new HashMap<String, Object>();
		wherePredicatesMapForUser.put("email", a.getEmail());
		Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePredicatesMapForUser, User.class, em);

		// If no user matched, return an empty Optional object
		if (wrappedUser.isPresent()) {

			User u = wrappedUser.get();

			if (a.validatePassword(u.getPassword())) {

				// Create JWT with 24 hours expiration, return both the token and its signing
				// key
				List<String> JWTokenComponents = a.createJWT(u.getId(), 86400000);
				Token JWTObject = new Token(JWTokenComponents.get(0), JWTokenComponents.get(1), u);

				// Check if Token already exists for user, if yes revoke it and generate a new
				// token
				Map<String, Object> wherePredicatesMapForToken = new HashMap<String, Object>();
				wherePredicatesMapForToken.put("user", JWTObject.getUser());
				Optional<Token> wrappedToken = DBHelper.getEntityFromFields(wherePredicatesMapForToken, Token.class,
						em);

				if (wrappedToken.isPresent()) {
					revokeToken(wrappedToken.get());
				}

				em.persist(JWTObject);
				return Optional.of(JWTObject.getToken());
			}
		}

		return Optional.empty();
	}

	@Override
	public void logout(Token t) {
		revokeToken(t);
	}

	private void revokeToken(Token t) {
		em.remove(em.contains(t) ? t : em.merge(t));
	}

	@Override
	public User updateUser(@NotNull User u) throws CustomException {
        try{
            if(isEmailOrUsernameAlreadyUsed(u.getEmail(), u.getUsername(),u.getId())) {
                throw new CustomException("Email or username is already used");
            } else {
                User user=em.merge(u);
                em.flush();
                return user;
            }
        }catch (ConstraintViolationException cve){
            throw new CustomException("invalid input");
        }
	}

	@Override
	public void deleteUser(@NotNull User u) {
		em.remove(em.contains(u) ? u : em.merge(u));
	}

	private boolean isEmailOrUsernameAlreadyUsed(String email, String username) {
		// Check if email is already used
		Map<String, Object> wherePredicatesMapForUserEmail = new HashMap<String, Object>();
		wherePredicatesMapForUserEmail.put("email", email);
		
		// Check if username is already used
		Map<String, Object> wherePredicatesMapForUserUsername = new HashMap<String, Object>();
		wherePredicatesMapForUserUsername.put("username", username);
		
		Optional<User> wrappedUserForEmail = DBHelper.getEntityFromFields(wherePredicatesMapForUserEmail, User.class, em);
		Optional<User> wrappedUserForUsername = DBHelper.getEntityFromFields(wherePredicatesMapForUserUsername, User.class, em);

		// If user already exists with this email
		return wrappedUserForEmail.isPresent() || wrappedUserForUsername.isPresent();
	}
	
	/** We do not want 2 User with the same username or email
	* But omit to check on User with id (needed for update) (zue)
	*/
	private boolean isEmailOrUsernameAlreadyUsed(String email, String username, Long id) {
		// Check if email is already used
		Map<String, Object> wherePredicatesMapForUserEmail = new HashMap<String, Object>();
		wherePredicatesMapForUserEmail.put("email", email);
		
		// Check if institutionName is already used
		Map<String, Object> wherePredicatesMapForUserUsername = new HashMap<String, Object>();
		wherePredicatesMapForUserUsername.put("username", username);
		
		Map<String, Object> whereNotPMid = new HashMap<String, Object>();
		whereNotPMid.put("id", id);

		Optional<User> wrappedUserForEmail = DBHelper.getEntityFromFields(wherePredicatesMapForUserEmail,whereNotPMid,User.class, em);
		Optional<User> wrappedUserForUsername = DBHelper.getEntityFromFields(wherePredicatesMapForUserUsername,whereNotPMid,User.class, em);

		// If institution already exists with same contact email
		return wrappedUserForEmail.isPresent() || wrappedUserForUsername.isPresent();
	}
}
