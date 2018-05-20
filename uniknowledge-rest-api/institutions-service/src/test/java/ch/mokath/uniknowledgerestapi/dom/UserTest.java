package ch.mokath.uniknowledgerestapi.dom;


import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {
	@Test
	public void UserTestOfEqualsMethodObjectNull() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(null), false);
	}
	@Test
	public void UserTestOfEqualsMethodSameObject() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), true);
	}
	@Test
	public void UserTestOfEqualsMethodByEmail() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "otherEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
	}
	@Test
	public void UserTestOfEqualsMethodByUserName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("otherUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
	}
	@Test
	public void UserTestOfEqualsMethodByFirstName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "otherFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
	}
	@Test
	public void UserTestOfEqualsMethodByLastName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "otherLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
	}
	@Test
	public void UserTestOfEqualsMethodByProfile() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/other/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), true);//2 users can have same profile's picture
	}
	@Test
	public void UserTestOfEqualsMethodByPassword() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"otherPassword");
		Assert.assertEquals(user.equals(otherUser), false);//2 users can't have same password
	}
	@Test
	public void UserTestOfSettersMethodOfId() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setID(10L); ;
		Assert.assertTrue(user.getId()==10L);
	}
	@Test
	public void UserTestOfSettersMethodOfUserName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setUsername("otherUserName") ;
		Assert.assertTrue(user.getUsername()== "otherUserName");
	}
	@Test
	public void UserTestOfSettersMethodOfFirstName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setFirstName("otherFirstName") ;
		Assert.assertTrue(user.getFirstName()== "otherFirstName");
	}
	@Test
	public void UserTestOfSettersMethodOfLastName() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setLastName("otherLastName") ;
		Assert.assertTrue(user.getLastName()== "otherLastName");
	}
	@Test
	public void UserTestOfSettersMethodOfProfileUrl() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setProfilePictureURL("http:/other/profile/Picture"); ;
		Assert.assertTrue(user.getProfilePictureURL()=="http:/other/profile/Picture");
	}
	@Test
	public void UserTestOfSettersMethodOfEmail() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setEmail("otherEmail"); 
		Assert.assertTrue(user.getEmail()=="otherEmail");
	}
	@Test
	public void UserTestOfSettersMethodOfPassword() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		user.setPassword("otherPassword");
		Assert.assertTrue(user.getPassword()=="otherPassword");
	}
	
	@Test
	public void UserTestOfgettersMethodOfQuestion() {
		
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
		question.setAuthor(user);
		
		
		Assert.assertTrue(user.getQuestions().size()==0);//??
	}
	
public void UserTestOfgettersMethodOfAnswer() {
		
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		answer.setAuthor(user);
		
		Assert.assertTrue(user.getAnswers().size()==0);//??
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
