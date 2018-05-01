/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;

/**
 * @author matteo113
 *
 */
@Stateless
public class PostsServiceImpl implements PostsService {

	@PersistenceContext
	private EntityManager em;

	private Logger log = LoggerFactory.getLogger(PostsServiceImpl.class);

	@Override
	public void createQuestion(@NotNull Question question) {
		em.persist(question);
	}

	@Override
	public void createAnswer(Question question, Answer answer) {
		question.addAnswer(answer);

	}

	@Override
	public void validateAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void likeQuestion(Question q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void likeAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void followQuestion(Question q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteQuestion(Question q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editQuestion(Question q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

}
