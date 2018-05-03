/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;


/**
 * @author matteo113
 *
 */
@Local
public interface PostsService {


	void createQuestion(@NotNull Question q, long id);

	void createAnswer(Question q, Answer a);

	void validateAnswer(Answer a);

	void likeQuestion(Question q);

	void likeAnswer(Answer a);

	void followQuestion(Question q);

	void deleteQuestion(Question q);

	void deleteAnswer(Answer a);

	void editQuestion(Question q);

	void editAnswer(Answer a);

}
