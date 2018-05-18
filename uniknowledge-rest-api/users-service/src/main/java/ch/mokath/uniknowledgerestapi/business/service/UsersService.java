/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotNull;

import ch.mokath.uniknowledgerestapi.dom.AuthInfos;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;

/**
 * @author tv0g
 * @author zue
 */
@Local
public interface UsersService {

	/**
	 * Return a session token iff provided credentials are correct
	 * 
	 * @param a
	 *            Provided Autentication Credentials
	 * @return JWToken iff login is successful
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	Optional<String> login(@NotNull AuthInfos a) throws NoSuchAlgorithmException, InvalidKeySpecException;

	/**
	 * Revoke provided JWToken. As an effect, the session associated with this token
	 * will be invalidated.
	 * 
	 * @param JWToken
	 *            Valid JSON Web Token
	 * @return true iff JWT has been revoked
	 */
	void logout(@NotNull Token t);

	/**
	 * Stores a user in database
	 * 
	 * @param user
	 *            User to store
	 */
	void createUser(@NotNull final User u) throws EntityExistsException;

	/**
	 * Delete user from database
	 * 
	 * @param u
	 *            User to delete
	 */
	void deleteUser(@NotNull final User u);

	/**
	 * Update user informations except password and id due to security reason
	 * 
	 * @param u
	 *            New user informations
	 */
	User updateUser(@NotNull final User u);

}
