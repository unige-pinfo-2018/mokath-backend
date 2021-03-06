package ch.mokath.uniknowledgerestapi.dom;

import java.util.Date;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import ch.mokath.uniknowledgerestapi.dom.Answer;

/**
* @author ornela
* @author zue
*/
public class AnswerTest {
	
	@Test
	public void answerEqualsTestFunction() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Answer otherAnswer = new Answer("junit",question);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), true);//the 2 answers are equal   
	}
	
	@Test
	public void answerEqualsTestFunctionByAuthor() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer otherAnswer = new Answer("junit",question);
		Answer answer = new Answer("junit",question);		
		User answerAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		User otherAnswerAuthor = new User("loulou", "loulou","loulou","http:/loulou/picture","loulou@yahoo.fr",
					"loulou");		
		answer.setAuthor(answerAuthor);
		otherAnswer.setAuthor(otherAnswerAuthor);
		
		Assert.assertEquals(answer.equals(otherAnswer),false);//the 2 answers have different authors
    
	}	
	
	
	@Test
	public void answerEqualsTestFunctionByCreated() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer otherAnswer = new Answer("junit",question);
		Answer answer = new Answer("junit",question);		
				
		answer.setCreated(null);
		otherAnswer.setCreated(null);
		
		Assert.assertEquals(answer.equals(otherAnswer),true);//the 2 answers have different authors
    
	}	
	
	
	@Test
	public void answerEqualsTestFunctionByQuestion() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		Question otherQuestion = new Question("domain", "unit test","what is the framework for the test?");
		
		Answer otherAnswer = new Answer("junit",otherQuestion);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), false);
		
		answer.setQuestion(null);
		Assert.assertEquals(answer.equals(otherAnswer), false);
	}
	
	@Test
	public void answerEqualsTestFunctionByText() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		
		Answer otherAnswer = new Answer("jacoco",question);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), false);  
		answer.setText(null);
		Assert.assertEquals(answer.equals(otherAnswer), false);
	}
	
	@Test
	public void answerEqualsTestFunctionByValidation() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		
		Answer otherAnswer = new Answer("jacoco",question);
		Answer answer = new Answer("junit",question);
		answer.validate();
		otherAnswer.unvalidate();

		Assert.assertEquals(answer.equals(otherAnswer), false);//the 2 answers are equal   
	}
	
	@Test
	public void answerTestOfGetterByText() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
       
	   
		Assert.assertEquals(answer.getText(), "junit");	    
	}
	
	@Test
	public void answerTestOfSetterAuthor() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		User answerAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		answer.setAuthor(answerAuthor);
		Assert.assertEquals(answer.getAuthor(), answerAuthor);
	}

	
	@Test
	public void answerTestOfSetterText() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		answer.setText("Cobertura for test coverage");
		Assert.assertFalse(answer.getText()== "junit");
		Assert.assertEquals(answer.getText(), "Cobertura for test coverage");
	}
		
	@Test
	public void answerTestOfSetQuestion() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");	
		Answer answer = new Answer("junit",question);
		
		Question otherQuestion = new Question("domain", "unit test","what is the framework of the test?");	
		answer.setQuestion(otherQuestion);
		
		Assert.assertEquals(answer.getQuestion(), otherQuestion);
	}
	
	@Test
	public void answerTestOfGetQuestion() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		
		Assert.assertEquals(answer.getQuestion(), question);
	}
	
	@Test
	public void answerTestOfGetCreated() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		
		Assert.assertEquals(answer.getCreated(), null);
	}
	
	@Test
	public void answerShouldBeForOneQuestion() {//one answer dont have 2 questions
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		Question otherQuestion = new Question("domain", "test","which type of test can i do?");
		Answer otherAnswer = new Answer("junit",otherQuestion);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), false);//false because answers cant be the same with 2 different questions
		
	    
	}
	@Test
	public void answerTestOfAddAndRemoveUpvote() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		
		Answer answer = new Answer("junit",question);		
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		User otherUser = new User("loulou", "loulou","loulou","http:/loulou/picture","loulou@yahoo.fr",
					"loulou");	
		
		answer.addUpvote(user);
		answer.addUpvote(otherUser);
		Assert.assertTrue(answer.getUpvoters().size()== 2);
		
		answer.removeUpvote(otherUser);
		Assert.assertTrue(answer.getUpvoters().size()== 1);
    
	}	
	@Test
	public void answerIsValidateTest() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		
		Answer answer = new Answer("junit",question);	
		
		answer.unvalidate();
		Assert.assertFalse(answer.isValidated());
		answer.validate();
		Assert.assertTrue(answer.isValidated());
    
	}	
	
	@Test
	public void AnswerTestBuilderMethod() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
		
		Answer answer = new Answer("junit",question);
		
		
		Answer.Builder answerBuilder = new Answer.Builder();
		answerBuilder.text = "junit";
		answerBuilder.question = question;
		Answer answerFromBuilder = answerBuilder.build(); //.with(User::,"").with(User::,"").with(User::,"").with(User::,"").with(User::password,"mePassword");
		
		Assert.assertEquals(answer.equals(answerFromBuilder),true);
	}
	@Test
	public void AnswerTestToStringMethod() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		Answer otherAnswer = new Answer("junit",question);
			
		Assert.assertEquals(answer.toString(),otherAnswer.toString());
	}
	

}
