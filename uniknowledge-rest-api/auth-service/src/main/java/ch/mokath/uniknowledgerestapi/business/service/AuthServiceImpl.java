/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Stateless;

/**
 * @author tv0g
 *
 */
@Stateless
public class AuthServiceImpl implements AuthService {

	@Override
	public String login(String email, String password) {

		if (email.equals("test@mail.com") && password.equals("123456")) {
			return "token";
		}
		return "badAuth";
	}

	@Override
	public Boolean logout(String JWToken) {

		return true;
	}

}
