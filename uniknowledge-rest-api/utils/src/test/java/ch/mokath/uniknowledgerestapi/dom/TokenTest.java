package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

/**
* @author ornela
* @author zue
*/
public class TokenTest {
	
	@Test
	public void tokengettersUserTestFunction() {
	   User one = new User("me", "me","me","http:/me/picture","me@yahoo.fr",
				"me");
	   
	   Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		Assert.assertEquals(token.getUser() , new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		Assert.assertEquals(token.getUser()==one, false);		
	}
	
	@Test
	public void tokengettersTokenTestFunction() {
	   
	   Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
	   Assert.assertEquals(token.getToken(),"babaToken");
			
	}
	
	@Test
	public void tokengettersSigningkeyTokenTestFunction() {
	   
	   Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
	   Assert.assertEquals(token.getSigningKey(),"babaKey");
		Assert.assertFalse(token.getSigningKey()=="otherKey");
			
	}
	
	
	
	@Test
	public void tokensettersUserTestFunction() {
		User one = new User("me", "me","me","http:/me/picture","me@yahoo.fr",
				"me");
		Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		
		token.setUser(one);
		Assert.assertEquals(token.getUser()==new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"),false);
		Assert.assertEquals(token.getUser()==one, true);
		
	}
	
	
	@Test
	public void tokensettersTokenTestFunction() {
		Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		
		token.setToken("newBabaToken");
		Assert.assertEquals(token.getToken()=="babaToken",false);
		Assert.assertTrue(token.getToken()=="newBabaToken");
		
	}
	
	@Test
	public void tokensettersSigningkeyTokenTestFunction() {
		Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		
		token.setSigningKey("newBabaKey");
		Assert.assertEquals(token.getSigningKey()=="babaKey",false);
		Assert.assertTrue(token.getSigningKey()=="newBabaKey");
	}
	
	
	@Test
	public void tokenKeyTestFunction() {
		
		Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		Token otherToken = new Token("babaToken","babaKey",new User("me", "me","me","http:/me/picture","me@yahoo.fr",
				"me"));
	Assert.assertTrue(token.getSigningKey()==otherToken.getSigningKey() && token.getUser() != otherToken.getUser());//we can have 2 differents token with the same signingKey
	
	}

	@Test
	public void tokenNonUniqueKeyTestFunction() {
		
		Token token = new Token("babaToken","babaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
		
		Token otherToken = new Token("otherBabaToken","otherBabaKey",new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba"));
	Assert.assertFalse(token.getSigningKey()!=otherToken.getSigningKey() && token.getUser() == otherToken.getUser());//same use cant have 2 different keys
	
	}
	@Test
	public void TokenTestBuilderMethod() {
		
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		Token token = new Token( "babaToken","babaKey",user);		
			
		Token.Builder tokenBuilder = new Token.Builder();
		tokenBuilder.token = "babaToken";
		tokenBuilder.signingKey = "babaKey";
		tokenBuilder.user = user;
		Token tokenFromBuilder = tokenBuilder.build(); 		
		Assert.assertEquals(token.equals(tokenFromBuilder),false);//??
	}
	
   
}
