package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

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

}
