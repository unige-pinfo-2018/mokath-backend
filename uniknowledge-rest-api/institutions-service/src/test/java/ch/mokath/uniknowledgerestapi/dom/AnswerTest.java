package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import ch.mokath.uniknowledgerestapi.dom.Answer;

public class AnswerTest {
	
	@Test
	public void answerEqualsTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		Question otherQuestion = new Question(domains, "test","what is the framework of the test?");
		Answer otherAnswer = new Answer("junit",otherQuestion);
	   Answer answer = new Answer.Builder().with($ -> {
			$.text = "junit";
			$.question = new Question(domains, "test","what is the framework of the test?");
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();

		Assert.assertEquals(answer.equals(otherAnswer), true);
		User answerAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
			"baba");
		User otherAnswerAuthor = new User("loulou", "loulou","loulou","http:/loulou/picture","loulou@yahoo.fr",
				"loulou");
		answer.setAuthor(answerAuthor);
		otherAnswer.setAuthor(otherAnswerAuthor);
		Assert.assertFalse(answer.equals(otherAnswer)== true);
	    
	}
	@Test
	public void answerTestofGetter() {
		HashSet<String> domains = new HashSet<String>();
		
	   Answer answer = new Answer.Builder().with($ -> {
			$.text = "junit";
			$.question = new Question(domains, "test","what is the framework of the test?");
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
       
	   
		Assert.assertEquals(answer.getText(), "junit");
		User answerAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
			"baba");
		answer.setAuthor(answerAuthor);
		Assert.assertEquals(answer.getAuthor(), answerAuthor);
		answer.setText("Cobertura for test coverage");
		Assert.assertFalse(answer.getText()== "junit");
		Assert.assertEquals(answer.getText(), "Cobertura for test coverage");
	    
	}
	@Test
	public void answerShouldBeForOneQuestion() {//one answer dont have 2 questions
		HashSet<String> domains = new HashSet<String>();
		Question otherQuestion = new Question(domains, "test","which type of test can i do?");
		Answer otherAnswer = new Answer("junit",otherQuestion);
	   Answer answer = new Answer.Builder().with($ -> {
			$.text = "junit";
			$.question = new Question(domains, "test","what is the framework of the test?");
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();

		Assert.assertEquals(answer.equals(otherAnswer), false);//false because answers cant be the same with 2 different questions
		
	    
	}
	

}
