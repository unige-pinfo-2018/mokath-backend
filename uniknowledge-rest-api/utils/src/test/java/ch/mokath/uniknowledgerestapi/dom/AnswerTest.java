package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import ch.mokath.uniknowledgerestapi.dom.Answer;

public class AnswerTest {
	
	@Test
	public void answerEqualsTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		Question question = new Question(domains, "test","what is the framework of the test?");
		
		Answer otherAnswer = new Answer("junit",question);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), true);//the 2 answers are equal   
	}
	
	@Test
	public void answerEqualsTestFunctionByAuthor() {
		HashSet<String> domains = new HashSet<String>();
		Question question = new Question(domains, "test","what is the framework of the test?");		
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
	public void answerTestOfGetterByText() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
       
	   
		Assert.assertEquals(answer.getText(), "junit");	    
	}
	
	@Test
	public void answerTestOfSetterAuthor() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		User answerAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		answer.setAuthor(answerAuthor);
		Assert.assertEquals(answer.getAuthor(), answerAuthor);
	}
	
	@Test
	public void answerTestOfSetterText() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");		
		Answer answer = new Answer("junit",question);
		answer.setText("Cobertura for test coverage");
		Assert.assertFalse(answer.getText()== "junit");
		Assert.assertEquals(answer.getText(), "Cobertura for test coverage");
	}
		
	@Test
	public void answerShouldBeForOneQuestion() {//one answer dont have 2 questions
		HashSet<String> domains = new HashSet<String>();
		Question question = new Question(domains, "test","what is the framework of the test?");
		Question otherQuestion = new Question(domains, "test","which type of test can i do?");
		Answer otherAnswer = new Answer("junit",otherQuestion);
		Answer answer = new Answer("junit",question);

		Assert.assertEquals(answer.equals(otherAnswer), false);//false because answers cant be the same with 2 different questions
		
	    
	}
	

}
