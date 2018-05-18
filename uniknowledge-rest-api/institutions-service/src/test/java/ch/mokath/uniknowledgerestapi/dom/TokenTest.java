package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class TokenTest {
	
	@Test
	public void tokengettersTestFunction() {
		User one = new User("me", "me","me","http:/me/picture","me@yahoo.fr",
				"me");
	   Token token = new Token.Builder().with($ -> {
		   $.token=  "babaToken";
		   $.signingKey = "babaKey";
		   $.user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
					"baba");

		}).build();

		Assert.assertEquals(token.getUser() , new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		Assert.assertEquals(token.getUser()==one, false);
		Assert.assertEquals(token.getToken(),"babaToken");
		Assert.assertEquals(token.getSigningKey(),"babaKey");
		Assert.assertFalse(token.getSigningKey()=="otherKey");
		
		
	}
	
	@Test
	public void tokensettersTestFunction() {
		User one = new User("me", "me","me","http:/me/picture","me@yahoo.fr",
				"me");
	   Token token = new Token.Builder().with($ -> {
		   $.token=  "babaToken";
		   $.signingKey = "babaKey";
		   $.user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
					"baba");

		}).build();
	   
	   token.setUser(one);
		Assert.assertEquals(token.getUser()==new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"),false);
		Assert.assertEquals(token.getUser()==one, true);
		
		Assert.assertEquals(token.getToken(),"babaToken");
		token.setToken("newBabaToken");
		Assert.assertEquals(token.getToken()=="babaToken",false);
		Assert.assertTrue(token.getToken()=="newBabaToken");
		
		token.setSigningKey("newBabaKey");
		Assert.assertEquals(token.getSigningKey()=="babaKey",false);
		Assert.assertTrue(token.getSigningKey()=="newBabaKey");
	}
	
	@Test
	public void tokenUniqueKeyTestFunction() {
		
	   Token token = new Token.Builder().with($ -> {
		   $.token=  "babaToken";
		   $.signingKey = "babaKey";
		   $.user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
					"baba");

		}).build();
	
	Token otherToken = new Token.Builder().with($ -> {
		   $.token=  "babaToken";
		   $.signingKey = "babaKey";
		   $.user = new User("me", "me","me","http:/me/picture","me@yahoo.fr",
					"me");

		}).build();
	
	Token anOtherToken = new Token.Builder().with($ -> {
		   $.token=  "otherBabaToken";
		   $.signingKey = "otherBabaKey";
		   $.user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
					"baba");

		}).build();
	
	Assert.assertEquals(token.getSigningKey(),otherToken.getSigningKey());//we can have 2 differents token with the same signingKey
	Assert.assertTrue(token.getSigningKey()==otherToken.getSigningKey() && token.getUser() != otherToken.getUser());
	Assert.assertTrue(token.getToken()==otherToken.getToken() && token.getUser() != otherToken.getUser());
	}
	
   
}
