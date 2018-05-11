/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;

/**
 * @author matteo113
 *
 */
@Stateless
public class PostsServiceImpl implements PostsService {

	@PersistenceContext
	private EntityManager em;
	private Logger log = LoggerFactory.getLogger(PostsServiceImpl.class);
	private DBHelper DBHelper = new DBHelper();

	/**********************************************************************
	 * QUESTIONS *
	 **********************************************************************/

	@Override
	public void createQuestion(@NotNull Question question, User user) throws NoSuchElementException {
		User author = em.merge(user);
		author.addQuestion(question);
		question.setAuthor(author);
		question.setCreated(new Date());
		em.persist(question);
	}

	@Override
	public void upvoteQuestion(Question q, User u) throws NoSuchElementException {
		User user = em.merge(u);
		Question question = em.merge(q);

		user.addLikedQuestion(question);
	}

	@Override
	public void followQuestion(Question q, User u) throws NoSuchElementException {
		User user = em.merge(u);
		Question question = em.merge(q);

		user.addFollowedQuestion(question);
	}

	@Override
	public void deleteQuestion(Question q, User u) {
		User user = em.merge(u);
		Question question = em.merge(q);

		// TODO externalize check
		if (user.equals(question.getAuthor())) {
			
//			question.getAnswers().clear();
//			question.getUpvotes().clear();
			
			em.merge(question);

			user.removeQuestion(question);
			em.remove(question);
		}
	}

	@Override
	public void editQuestion(Question oq, Question uq, User u) {
		User user = em.merge(u);
		Question question = em.merge(oq);

		if (user.equals(question.getAuthor())) {
			question.setText(uq.getText());
			question.setTitle(uq.getTitle());
			question.setDomains(uq.getDomains());
		}
		em.merge(question);
	}

	/**********************************************************************
	 * ANSWERS *
	 **********************************************************************/

	@Override
	public void createAnswer(Question q, Answer a, User u) {
		User user = em.merge(u);
		Question question = em.merge(q);

		user.addAnswer(a);
		question.addAnswer(a);
		a.setQuestion(question);
		a.setAuthor(user);
		a.setCreated(new Date());

		em.persist(a);
	}

	@Override
	public void validateAnswer(Answer a, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(a);

		if (user.equals(answer.getAuthor())) {
			answer.validate();
		}
	}

	@Override
	public void upvoteAnswer(Answer a, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(a);

		user.addLikedAnswer(answer);
	}

	@Override
	public void deleteAnswer(Answer a, User u, Question q) {
		User user = em.merge(u);
		Answer answer = em.merge(a);
		Question question = em.merge(q);

		if (user.equals(answer.getAuthor())) {
			// remove all upvotes
			for (User usr : answer.getUpvotes()) {
				usr.removeLikedAnswer(answer);
			}

			user.removeAnswer(answer);
			question.removeAnswer(answer);
			em.remove(answer);
		}
	}

	@Override
	public void editAnswer(Answer oa, Answer ua, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(oa);

		// TODO externalise check
		if (user.equals(answer.getAuthor())) {
			answer.setText(ua.getText());
		}
		em.merge(answer);
	}

	private void deleteAnswer(Answer a) {
		Answer answer = em.merge(a);
		User user = em.merge(answer.getAuthor());
		Question question = em.merge(answer.getQuestion());

		// remove all upvotes
		answer.getUpvotes().clear();
		
		user.removeAnswer(answer);
		question.removeAnswer(answer);
		em.remove(answer);
	}

}
