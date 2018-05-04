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
	public void deleteQuestion(Question q) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void editQuestion(Question q) {
		// TODO Auto-generated method stub

	}
	
	/**********************************************************************
	 *                              ANSWERS                               *
	 **********************************************************************/

	@Override
	public void createAnswer(Question question, Answer answer) {
		em.persist(answer);
		question.addAnswer(answer);
		em.merge(question);
	}

	@Override
	public void validateAnswer(Answer answer) {
		answer.validate();
		em.merge(answer);
	}

	@Override
	public void likeAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAnswer(Answer a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editAnswer(Answer a) {
		// TODO Auto-generated method stub

	}
	
	private <T> Optional<List<User>> getUsersFrom(String field, T value) {

		// Create the Criteria Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to User Class
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> from = criteriaQuery.from(User.class);

		// Modify and create the query to match given field/value pairs entries
		criteriaQuery.where(criteriaBuilder.equal(from.get(field), value));
		TypedQuery<User> finalQuery = em.createQuery(criteriaQuery);

		// Execute SELECT request on previous defined query predicates
		List<User> matchedUsers = finalQuery.getResultList();
		// If users list is not empty, return list of users wrapped in Optional object
		// else, return an empty Optional object
		return matchedUsers.isEmpty() ? Optional.empty() : Optional.of(matchedUsers);
	}
	
	private User getUserById(long id) throws NoSuchElementException {
		Optional<List<User>> matchedUsers = getUsersFrom("id", id);
		
		return matchedUsers.get().get(0);
	}
		

}
