/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;


/**
 * @author matteo113
 *
 */
@Local
public interface PostsService {


	void createQuestion(@NotNull Question q, User u);

	void createAnswer(Question q, Answer a, User u);

	void validateAnswer(Answer a, User u);

	void upvoteQuestion(Question q, User u);

	void upvoteAnswer(Answer a, User u);

	void followQuestion(Question q, User u);

	void deleteQuestion(Question q, User u);

	void deleteAnswer(Answer a, User u, Question q);

	void editQuestion(Question oq, Question uq, User u);

	void editAnswer(Answer oa, Answer na, User u);

}
