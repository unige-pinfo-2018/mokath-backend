package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class QuestionTest {
	@Test
	public void questionEqualsTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		Question question = new Question(domains,"test","what is the framework of the test?");
		Question otherQuestion = new Question(domains,"test","what is the framework of the test?");
		
		User questionAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		User otherQuestionAuthor = new User("loulou", "loulou","loulou","http:/loulou/picture","loulou@yahoo.fr",
				"loulou");
		
		otherQuestion.setAuthor(otherQuestionAuthor);
		
		question.setAuthor(questionAuthor);
						
		Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 authors of the 2 questions are different
		
	}
	
	@Test
	public void questionEqualsTestFunctionByTitle() {
		HashSet<String> domains = new HashSet<String>();
		Question question = new Question(domains,"test","what is the framework of the test?");
		Question otherQuestion = new Question(domains,"test","what is the framework of the test?");
		
		otherQuestion.setTitle("framework");
						
		Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 authors of the 2 questions are different
		
	}
	
	
	@Test
	public void questionAddAnswerTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
				
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   question.addAnswer(otherAnswer);
	   
	   Assert.assertEquals(question.getAnswers().size()==2,true);//we added 2 element

	}
	
	@Test
	public void questionotherAddAnswerTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
		
		Question otherQuestion = new Question(domains, "test","which type of software to use for test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",otherQuestion);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check correspondance bettwen question
	   
	   Assert.assertEquals(question.getAnswers()==answer, false);//false cause return should be a set of the 2 answers that we added
	   
	}
	
	@Test
	public void questionRemoveAnswerTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check corresponding betwen question
	   
	   question.removeAnswer(otherAnswer);
	   
	   Assert.assertEquals(question.getAnswers().size()==1,true);//we removed 1 answer
	}
	
	@Test
	public void questionOtherRemoveAnswerTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		
		Question question = new Question(domains, "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check corresponding betwen question
	   
	   question.removeAnswer(otherAnswer);
	   
	   question.removeAnswer(answer);
	   
	   Assert.assertEquals(question.getAnswers().size()==0,true);//we removed 1 answer
	}	

}
