/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.Set;
import java.util.List;

import javax.ejb.Local;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomException;

/**
 * @author matteo113
 * @author zue
 */
@Local
public interface PostsService {

    /** Questions **/
	void createQuestion(Question q, User u) throws CustomException;
	
	Question getQuestion(final String questionId) throws CustomException;
    List<Question> getQuestions();
    List<Question> getTopQuestions(int nb);
    Set<Answer> getQuestionAnswers(final String questionId) throws CustomException;
    Set<User> getQuestionFollowers(final String questionId) throws CustomException;
    Set<User> getQuestionUpvoters(final String qid) throws CustomException;
    
    List<Question> getMyQuestions(final User u);
    Set<Question> getMyFollowedQuestions(final User u);
    Set<Question> getMyUpvotedQuestions(final User u);
   
    Question editQuestion(final String questionId, Question uq, User u) throws CustomException;

	void followQuestion(final String questionId, User u) throws CustomException;
	void upvoteQuestion(final String questionId, User u) throws CustomException;

	void deleteQuestion(final String questionId, User u) throws CustomException;
	

    /** Answers **/
	void createAnswer(final String questionId, Answer a, User u) throws CustomException;
	
    Answer getAnswer(final String answerId) throws CustomException;
    Set<User> getAnswerUpvoters(final String answerId) throws CustomException;
    
    List<Answer> getMyAnswers(final User u);
    Set<Answer> getMyUpvotedAnswers(final User u);
    
	Answer editAnswer(final String answerId, Answer na, User u) throws CustomException;

    void validateAnswer(final String answerId, User u) throws CustomException;
	void upvoteAnswer(final String answerId, User u) throws CustomException;

	void deleteAnswer(final String id, User u) throws CustomException;
}
