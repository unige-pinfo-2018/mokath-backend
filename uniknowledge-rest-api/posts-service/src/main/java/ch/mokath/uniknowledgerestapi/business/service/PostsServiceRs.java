/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.Secured;

/**
 * @author matteo113
 *
 */
@Path("")
public class PostsServiceRs {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private PostsService postsService;
	
	@POST
	@Secured
	@Path("/questions")
	@Consumes("application/json")
	@Produces("application/json")
	public Response newQuestion(@Context HttpServletRequest req, @NotNull final String requestBody) {
		Question question;
		User author;
		
		try {
			question = new Gson().fromJson(requestBody, Question.class);
			
			long userId = (long) req.getAttribute("userID");
			author = getUsersFrom("id", userId).get().get(0);
			
			postsService.createQuestion(question, author);
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		}
		
		//TODO add toString
		return Response.ok().build();
	}
	
	@PUT
	@Secured
	@Path("/questions/{id}")
	@Produces("application/json")
	public Response likeQuestion(@Context HttpServletRequest req, @PathParam("id") String id) {
		Question question;
		User user;
		
		try {
			question = getQuestionsFrom("id", id).get().get(0);
			
			long userId = (long) req.getAttribute("userID");
			user = getUsersFrom("id", userId).get().get(0);
			
			postsService.likeQuestion(question, user);
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.QUESTION_NOT_FOUND.getHTTPResponse();
		}
		//TODO add toString
		return Response.ok().build();
	}
	
	
	private <T> Optional<List<Question>> getQuestionsFrom(String field, T value) {

		// Create the Criteria Builder
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// Link Query to Question Class
		CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
		Root<Question> from = criteriaQuery.from(Question.class);

		// Modify and create the query to match given field/value pairs entries
		criteriaQuery.where(criteriaBuilder.equal(from.get(field), value));
		TypedQuery<Question> finalQuery = em.createQuery(criteriaQuery);

		// Execute SELECT request on previous defined query predicates
		List<Question> matchedUsers = finalQuery.getResultList();
		// If users list is not empty, return list of users wrapped in Optional object
		// else, return an empty Optional object
		return matchedUsers.isEmpty() ? Optional.empty() : Optional.of(matchedUsers);
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
}
