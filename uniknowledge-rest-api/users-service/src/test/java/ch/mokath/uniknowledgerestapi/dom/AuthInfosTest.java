package ch.mokath.uniknowledgerestapi.dom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tv0g
 *
 */
public class AuthInfosTest {

	private Logger log = LoggerFactory.getLogger(AuthInfosTest.class);
	User u = new User.Builder().with($ -> {
		$.username = "realDonaldTrump";
		$.firstName = "Donald";
		$.lastName = "Trump";
		$.profilePictureURL = "https://pbs.twimg.com/profile_images/874276197357596672/kUuht00m_400x400.jpg";
		$.email = "ddtrump@mail.com";
		$.password = "covfefe";
	}).build();
	
	@Test
	public void passwordValidationShouldSucceed() {

		AuthInfos a = new AuthInfos("ddtrump@mail.com", "covfefe");

		try {
			
			String fullHashedPassword = a.createHash(u.getPassword());
			assertTrue(a.validatePassword(fullHashedPassword));
			
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void passwordValidationWithWrongPasswordShouldFail() {

		AuthInfos a = new AuthInfos("ddtrump@mail.com", "covfeefe");

		try {
			String fullHashedPassword = a.createHash(u.getPassword());
			assertFalse(a.validatePassword(fullHashedPassword));
			
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
