package ch.mokath.uniknowledgerestapi.dom;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

/**
* @author ornela
* @author zue
*/
public class AuthInfosTest {
	@Test
	public void AuthInfosTestGetEmail() {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		
		Assert.assertTrue(authInfos.getEmail()=="myEmail");
	}
	@Test
	public void AuthInfosTestSetEmail() {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		authInfos.setEmail("otherEmail");
		
		Assert.assertTrue(authInfos.getEmail()=="otherEmail");
	}
	@Test
	public void AuthInfosTestGetPassword() {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		
		Assert.assertTrue(authInfos.getPassword()=="myPassword");
	}
	@Test
	public void AuthInfosTestSetPassword() {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		authInfos.setPassword("otherPassword");
		
		Assert.assertTrue(authInfos.getPassword()=="otherPassword");
	}
	@Test
	public void AuthInfosTestHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		AuthInfos otherAuthInfos = new AuthInfos("myEmail","myPassword");
		
		
		Assert.assertFalse(authInfos.createHash("myPassword")==otherAuthInfos.createHash("myPassword"));//2 hash cant be the same
	}
	@Test
	public void AuthInfosTestValidatePass() throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		AuthInfos otherAuthInfos = new AuthInfos("myEmail","myPassword");
		authInfos.validatePassword(authInfos.createHash("myPassword"));
		
		
		Assert.assertTrue(authInfos.validatePassword(authInfos.createHash("myPassword")));
	}
	@Test
	public void AuthInfosTestCreateJwt() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		user.setID(2L);
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		AuthInfos otherAuthInfos = new AuthInfos("myEmail","myPassword");
		
		Assert.assertFalse(authInfos.createJWT(2L, 3L)==otherAuthInfos.createJWT(2L, 3L));
	}
	@Test
	public void AuthInfosTestCreateJwtAndTtlPositif() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		user.setID(2L);
		AuthInfos authInfos = new AuthInfos("myEmail","myPassword");
		AuthInfos otherAuthInfos = new AuthInfos("myEmail","myPassword");
		
		Assert.assertFalse(authInfos.createJWT(2L, -2L)==otherAuthInfos.createJWT(2L, -2L));
	}

}
