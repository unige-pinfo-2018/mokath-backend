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

	void createAnswer(Question q, Answer a);

	void validateAnswer(Answer a);

	void upvoteQuestion(Question q, User u);

	void likeAnswer(Answer a);

	void followQuestion(Question q, User u);

	void deleteQuestion(Question q, User u);

	void deleteAnswer(Answer a);

	void editQuestion(Question q);

	void editAnswer(Answer a);

}
