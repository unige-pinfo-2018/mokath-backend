package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
* @author ornela
* @author zue
*/
public class QuestionTest {
	@Test
	public void questionEqualsTestFunction() {
		
		Question question = new Question("domain","test","what is the framework of the test?");
		Question otherQuestion = new Question("domain","test","what is the framework of the test?");
		
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
		
		Question question = new Question("domain","test","what is the framework of the test?");
		Question otherQuestion = new Question("domain","test","what is the framework of the test?");
		
		question.setTitle(null);
						
		Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 authors of the 2 questions are different
		
	}
	@Test
	public void questionEqualsTestFunctionByText() {
		
		Question question = new Question("domain","test","what is the framework of the test?");
		Question otherQuestion = new Question("domain","test","what is the framework of the test?");
		
		Assert.assertEquals(question.equals(otherQuestion), true);
		
		question.setText(null);				
		Assert.assertEquals(question.equals(otherQuestion), false);
		
	}
	@Test
	public void questionEqualsTestFunctionByDomain() {
		
		Question question = new Question("domain","test","what is the framework of the test?");
		Question otherQuestion = new Question("domain","test","what is the framework of the test?");
		
		question.setDomain(null);
						
		Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 authors of the 2 questions are different
		
	}
	@Test
	public void questionEqualsTestFunctionBycreated() {
		
		Question question = new Question("domain","test","what is the framework of the test?");
		Question otherQuestion = new Question("domain","test","what is the framework of the test?");
		
		otherQuestion.setCreated(null);
		question.setCreated(null);
						
		Assert.assertEquals(question.equals(otherQuestion), true);//false cause the 2 authors of the 2 questions are different
		
	}
	
	
	@Test
	public void questionAddAnswerTestFunction() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
				
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   question.addAnswer(otherAnswer);
	   
	   Assert.assertEquals(question.getAnswers().size()==2,true);//we added 2 element

	}
	
	@Test
	public void questionotherAddAnswerTestFunction() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Question otherQuestion = new Question("domain", "test","which type of software to use for test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",otherQuestion);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check correspondance bettwen question
	   
	   Assert.assertEquals(question.getAnswers()==answer, false);//false cause return should be a set of the 2 answers that we added
	   
	}
	
	@Test
	public void questionRemoveAnswerTestFunction() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check corresponding betwen question
	   
	   question.removeAnswer(otherAnswer);
	   
	   Assert.assertEquals(question.getAnswers().size()==1,true);//we removed 1 answer
	}
	
	@Test
	public void questionOtherRemoveAnswerTestFunction() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		Answer answer = new Answer("junit",question);
		
		Answer otherAnswer = new Answer("cobertura",question);
	   
	   question.addAnswer(answer);
	   
	   question.addAnswer(otherAnswer);//not check corresponding betwen question
	   
	   question.removeAnswer(otherAnswer);
	   
	   question.removeAnswer(answer);
	   
	   Assert.assertEquals(question.getAnswers().size()==0,true);//we removed 1 answer
	}	
	@Test
	public void questionTestFunctionOfGetText() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
	   Assert.assertEquals(question.getText(),"what is the framework of the test?");//we removed 1 answer
	}	
	@Test
	public void questionTestFunctionOfGetTitle() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
	   Assert.assertEquals(question.getTitle(),"test");//we removed 1 answer
	}
	@Test
	public void questionTestFunctionOfGetDomain() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
	   Assert.assertEquals(question.getDomain(),"domain");//we removed 1 answer
	   
	}
	@Test
	public void questionTestFunctionOfGetCreated() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
	   Assert.assertEquals(question.getCreated(),null);
	}
	@Test
	public void questionTestFunctionOfAddAndRemoveUpvoted() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		question.addUpvote(user);
		
	   Assert.assertTrue(question.getUpvoters().size()==1);
	   question.removeUpvote(user);
		
	   Assert.assertTrue(question.getUpvoters().size()==0);
	}
	@Test
	public void questionTestFunctionOfGetPopularity() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		question.addUpvote(user);
		
	   Assert.assertTrue(question.getPopularity()==1);
	   
	}
	
	@Test
	public void questionTestFunctionOfAddAndRemoveFollowers() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		User user = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
		question.addFollower(user);
		
	   Assert.assertTrue(question.getFollowers().size()==1);
	   question.removeFollower(user);
		
	   Assert.assertTrue(question.getFollowers().size()==0);
	}
	@Test
	public void questionTestFunctionOfSetText() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		question.setText("what is the framework of the test for unit test?");
		
	   Assert.assertFalse(question.getText()=="what is the framework of the test?");
	}
	@Test
	public void questionTestFunctionOfSetCreated() {
		
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		
		question.setCreated(null);
		
	   Assert.assertEquals(question.getCreated(),null);
	}
	
	@Test
	public void QuestionTestBuilderMethod() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");		
			
		
		Question.Builder questionBuilder = new Question.Builder();
		questionBuilder.domain = "domain";
		questionBuilder.title = "test";
		questionBuilder.text = "what is the framework of the test?";
		Question questionFromBuilder = questionBuilder.build(); 		
		Assert.assertEquals(question.equals(questionFromBuilder),true);
	}
	@Test
	public void QuestionTestToStringMethod() {
		
		Question question = new Question("domain", "test","what is the framework of the test?");
		Question otherQuestion = new Question("domain", "test","what is the framework of the test?");
			
		Assert.assertEquals(question.toString(),otherQuestion.toString());
	}


}
