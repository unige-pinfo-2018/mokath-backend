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
	 *                            QUESTIONS                               *
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
	public void followQuestion(Question q, User u) throws NoSuchElementException{
		User user = em.merge(u);
		Question question = em.merge(q);
		
		user.addFollowedQuestion(question);
	}

	@Override
	public void deleteQuestion(Question q, User u) {
		User user = em.merge(u);
		Question question = em.merge(q);
		
		user.removeQuestion(question);
		em.remove(question);
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
	 *                              ANSWERS                               *
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
	public void validateAnswer(Answer a) {
		Answer answer = em.merge(a);
		answer.validate();
	}

	@Override
	public void likeAnswer(Answer a) {
				
	}

	@Override
	public void deleteAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editAnswer(Answer a) {
		// TODO Auto-generated method stub

	}
	
}
