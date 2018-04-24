/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Local;

/**
 * @author tv0g
 *
 */
@Local
public interface AuthService {

	/**
	 * Return a session token iff provided credentials are correct
	 * 
	 * @param email
	 *            User email
	 * @param password
	 *            User password
	 * @return A JSON Web Token iff provided credentials are correct
	 */
	public String login(String email, String password);

	/**
	 * Revoke provided JWToken. As an effect, the session associated with this token
	 * will be invalidated.
	 * 
	 * @param JWToken
	 *            Valid JSON Web Token
	 * @return true iff JWT has been revoked
	 */
	public Boolean logout(String JWToken);

}
