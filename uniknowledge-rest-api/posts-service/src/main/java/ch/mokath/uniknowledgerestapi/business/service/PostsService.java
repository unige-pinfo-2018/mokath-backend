/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.HashSet;

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

    //Questions
	void createQuestion(Question q, User u) throws CustomException;
    HashSet<Answer> getQuestionAnswers(final String questionId) throws CustomException;

	void upvoteQuestion(Question q, User u);

	void upvoteAnswer(Answer a, User u);

	void followQuestion(Question q, User u);

//z	void deleteQuestion(Question q, User u);
	void deleteQuestion(Question q, User u) throws CustomException;


	void editQuestion(Question oq, Question uq, User u);

    // Answers
	void createAnswer(final String qid, Answer a, User u) throws CustomException;
    Answer getAnswer(final String id) throws CustomException;
	
	void validateAnswer(Answer a, User u);

	void editAnswer(Answer oa, Answer na, User u);

	void deleteAnswer(final String id, User u) throws CustomException;
}
