package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
  

/**
* @author ornela
* @author zue
*/
public class UserTest {
    /** HashCode Tests **/
	@Test
	public void UserHashcodeEqualsTest() {
		User user1 = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User user2 = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
        user1.setID(1L);
        user2.setID(1L);
		Assert.assertTrue(user1.hashCode() == user2.hashCode());
	}
	@Test
	public void UserHashcodeNotEqualsTest() {
		User user1 = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User user2 = new User("meUsername2", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertFalse(user1.hashCode() == user2.hashCode());
	}
	@Test
	public void NullUserHashcodeEqualsTest() {
		User user1 = new User(null,null,null,null,null,null);
		User user2 = new User(null,null,null,null,null,null);
		Assert.assertTrue(user1.hashCode() == user2.hashCode());
	}
	@Test
	public void NullUserHashcodeNotEqualsTest() {
		User user1 = new User(null,null,null,null,null,null);
		User user2 = new User(null,null,null,null,null,"0");
		Assert.assertFalse(user1.hashCode() == user2.hashCode());
	}

	
	//test of equal function
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
	/** complete equals **/
	@Test
	public void UserTestDifferentObjectClass() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		String otherUser = "blabla";
		Assert.assertEquals(user.equals(otherUser), false);
	}
	@Test
	public void UserTestOfEqualsMethodWithEmailNull() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", null,
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "otherEmail",
				"mePassword");
		User otherUserNull = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", null,
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
	}
	@Test
	public void UserTestOfEqualsMethodWithFirstNameNull() {
		User user = new User("meUsername", null, "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUserNull = new User("meUsername", null, "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
	}
	@Test
	public void UserTestOfEqualsMethodWithLastNameNull() {
		User user = new User("meUsername", "meFirstName",null, "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUserNull = new User("meUsername", "meFirstName",null, "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
	}
	@Test
	public void UserTestOfEqualsMethodWithUserNameNull() {
		User user = new User( null, "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		User otherUserNull = new User(null, "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
	}
	@Test
	public void UserTestOfEqualsMethodWithPasswordNull() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",null);
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
		User otherUserNull = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",null);
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
	}
	@Test
	public void UserTestOfEqualsMethodWithId() {
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
		User otherUser = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
		otherUser.setID(1L);
		User otherUserNull = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
		Assert.assertEquals(user.equals(otherUser), false);
		Assert.assertEquals(user.equals(otherUserNull), true);
		user.setID(2L);
		Assert.assertEquals(user.equals(otherUser), false);
		otherUser.setID(2L);
		Assert.assertEquals(user.equals(otherUser), true);
	}
	
	//test of setters and getters
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
	
	//test which do not works like and was waiting
	@Test
	public void UserTestOfgettersMethodOfQuestion() {
		
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		question.setAuthor(user);
		Assert.assertTrue(question.getAuthor()==user);
		
		Assert.assertTrue(user.getQuestions().size()==0);
	}
	
public void UserTestOfgettersMethodOfAnswer() {
		
		User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
				"mePassword");
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		answer.setAuthor(user);
		
		Assert.assertTrue(user.getAnswers().size()==0);
	}

//test of add and remove functions
@Test
public void UserTestOfAddQuestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	user.addQuestion(question);
	Assert.assertTrue(user.getQuestions().size()==1);
}
@Test
public void UserTestOfRemoveQuestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	user.addQuestion(question);
	user.removeQuestion(question);
	Assert.assertTrue(user.getQuestions().size()==0);
}
@Test
public void UserTestOfAddAnswerMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	Answer answer = new Answer("junit",question);
	
	user.addAnswer(answer);
	
	Assert.assertTrue(user.getAnswers().size()==1);
}
@Test
public void UserTestOfRemoveAnswerMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	Answer answer = new Answer("junit",question);
	
	user.addAnswer(answer);
	user.removeAnswer(answer);
	
	Assert.assertTrue(user.getAnswers().size()==0);
}
@Test
public void UserTestOfAddLikedQuestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	user.addLikedQuestion(question);
	
	Assert.assertTrue(user.getLikedQuestions().size()==1);
}
@Test
public void UserTestOfRemoveLikedQuestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	user.addLikedQuestion(question);
	user.removeLikedQuestion(question);

	Assert.assertTrue(user.getLikedQuestions().size()==0);
}

@Test
public void UserTestOfAddLikedAnswerMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	Answer answer = new Answer("junit",question);
	
	user.addLikedAnswer(answer);
	
	Assert.assertTrue(user.getLikedAnswers().size()==1);
}

@Test
public void UserTestOfRemoveLikedAnswerMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	
	Answer answer = new Answer("junit",question);
	
	user.addLikedAnswer(answer);
	user.removeLikedAnswer(answer);
	Assert.assertTrue(user.getLikedAnswers().size()==0);
}

@Test
public void UserTestOfAddFolowedQUestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	user.addFollowedQuestion(question);
	
	Assert.assertTrue(user.getFollowedQuestions().size()==1);
}
@Test
public void UserTestOfRemoveFolowedQUestionMethod() {
	
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	Question question = new Question("domain", "test","what is the framework of the test?");
	user.addFollowedQuestion(question);
	user.removeFollowedQuestion(question);
	Assert.assertTrue(user.getFollowedQuestions().size()==0);
}

/** INSTITUTIONS **/
@Test
public void UserTestOfInstitutionsMethods() {
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	

	HashSet<String> InstDomains = new HashSet<String>();
	Institution inst = new Institution("InsName", "InsLogo", "contact@institution.com", InstDomains);
	
	user.setInstitution(inst);
	Assert.assertTrue(user.getInstitution().getInstitutionName() == "InsName");
	user.removeInstitution();
	Assert.assertTrue(user.getInstitution() == null);
}

/** POINTS **/
@Test
public void UserTestSetGetPointsMethods() {
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	user.setPoints(2);
	Assert.assertTrue(user.getPoints() == 2);
}

@Test
public void UserTestAddGetPointsMethods() {
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail",
			"mePassword");
	
	
	user.addPoints(Points.QUESTION_CREATED);
	Points pointsFromQuestionCreated = Points.QUESTION_CREATED;
	Assert.assertTrue(user.getPoints() == pointsFromQuestionCreated.getPointValue());
}

/** BUILDER & toString() **/
@Test
public void UserTestEmptyUserToStringMethod() {
	User user = new User();
    Assert.assertTrue(user.toString().equals("{\"points\":0}"));
}
/*@Test
public void UserTestUserToFromGsonMethod() {
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
	

	String userToString = user.toString();
	User userFromJsonString = new Gson().fromJson(userToString, User.class);

	Assert.assertEquals(user.equals(userFromJsonString),true);
}*/

@Test
public void UserTestBuilderMethod() {
	User user = new User("meUsername", "meFirstName", "meLastName", "http:/me/profile/Picture", "meEmail","mePassword");
	

	User.Builder userBuilder = new User.Builder();
	userBuilder.username = "meUsername";
	userBuilder.firstName = "meFirstName";
	userBuilder.lastName = "meLastName";
	userBuilder.profilePictureURL = "http:/me/profile/Picture";
	userBuilder.email = "meEmail";
	userBuilder.password = "mePassword";
	User userFromBuilder = userBuilder.build(); //.with(User::,"").with(User::,"").with(User::,"").with(User::,"").with(User::password,"mePassword");
	Assert.assertEquals(user.equals(userFromBuilder),true);
}
	
}
