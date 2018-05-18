package ch.mokath.uniknowledgerestapi.dom;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class QuestionTest {
	@Test
	public void questionEqualsTestFunction() {
		
		Question otherQuestion = new Question.Builder().with($ -> {
			$.domains = new HashSet<String>();
			$.title = "test";
			$.text = "what is the framework of the test?";
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
		
	   Question question = new Question.Builder().with($ -> {
			$.domains = new HashSet<String>();
			$.title = "test";
			$.text = "what is the framework of the test?";
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
	   
	   User questionAuthor = new User("baba", "baba","baba","http:/baba/picture","baba@yahoo.fr",
				"baba");
	   User otherQuestionAuthor = new User("loulou", "loulou","loulou","http:/loulou/picture","loulou@yahoo.fr",
				"loulou");
	   
	   otherQuestion.setAuthor(otherQuestionAuthor);
	   question.setAuthor(questionAuthor);
	   Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 authors of the 2 questions are different
	   
	   otherQuestion.setAuthor(questionAuthor);
	   otherQuestion.setTitle("matrix");
	   Assert.assertEquals(question.equals(otherQuestion), false);//false cause the 2 domains are different
	}


	@Test
	public void questionAddAndRemoveAnswerTestFunction() {
		HashSet<String> domains = new HashSet<String>();
		
		
		Answer answer = new Answer.Builder().with($ -> {
			$.text = "junit";
			$.question = new Question(domains, "test","what is the framework of the test?");
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
		
		Answer otherAnswer = new Answer.Builder().with($ -> {
			$.text = "cobertura";
			$.question = new Question(domains, "test","which type of software to use for test?");
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
		
	   Question question = new Question.Builder().with($ -> {
			$.domains = new HashSet<String>();
			$.title = "test";
			$.text = "what is the framework of the test?";
//			$.administrators = new HashSet<String>();
//			$.askers = new HashSet<String>();
//			$.repliers = new HashSet<String>();
		}).build();
	   
	   question.addAnswer(answer);
	   question.addAnswer(otherAnswer);//not check corresponding betwen question
	   
	   
	   Assert.assertEquals(question.getAnswers().size()==2,true);//we added 2 element
	   
	   Assert.assertEquals(question.getAnswers()==answer, false);//false cause return should be a set of the 2 answers that we added
	   
	   question.removeAnswer(otherAnswer);
	   Assert.assertEquals(question.getAnswers().size()==1,true);//we removed 1 answer
	   
	   question.removeAnswer(answer);
	   Assert.assertEquals(question.getAnswers().size()==0,true);//we removed 1 answer

	}

	

}
