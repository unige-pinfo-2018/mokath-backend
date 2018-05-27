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
    Set<Answer> getQuestionAnswers(final String questionId) throws CustomException;

	Question editQuestion(final String questionId, Question uq, User u) throws CustomException;
    List<Question> getMyQuestions(final User u);

	void followQuestion(final String questionId, User u) throws CustomException;
	void upvoteQuestion(final String questionId, User u) throws CustomException;

//z	void deleteQuestion(Question q, User u);
//z	void editQuestion(Question oq, Question uq, User u);

	void deleteQuestion(final String questionId, User u) throws CustomException;

    /** Answers **/
	void createAnswer(final String questionId, Answer a, User u) throws CustomException;
	
    Answer getAnswer(final String answerId) throws CustomException;
    List<Answer> getMyAnswers(final User u);

	Answer editAnswer(final String answerId, Answer na, User u) throws CustomException;

    void validateAnswer(final String answerId, User u) throws CustomException;
	void upvoteAnswer(final String answerId, User u) throws CustomException;

	void deleteAnswer(final String id, User u) throws CustomException;
}
