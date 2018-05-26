/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;
import ch.mokath.uniknowledgerestapi.utils.Secured;

//import javax.persistence.Proxy;
import ch.mokath.uniknowledgerestapi.utils.CustomException;

/**
 * @author matteo113
 *
 */
@Path("")
public class PostsServiceRs {
    @PersistenceContext
//	@PersistenceContext(type = PersistenceContextType.EXTENDED)
//	@Proxy(lazy=false)
	private EntityManager em;

	@Inject
	private PostsService postsService;
	private DBHelper DBHelper = new DBHelper();
	private Logger log = LoggerFactory.getLogger(PostsServiceRs.class);

	@POST
	@Secured
	@Path("/questions")
	@Consumes("application/json")
	@Produces("application/json")
	public Response newQuestion(@Context HttpServletRequest req,final String requestBody) {
		try {
			Question question = new Gson().fromJson(requestBody, Question.class);
			User trustedUserAsAuthor = (User) req.getAttribute("user");
			postsService.createQuestion(question, trustedUserAsAuthor);
            return Response.ok(question.toString()).build();
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
           return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	@PUT
	@Secured
	@Path("/questions/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modifyQuestion(@Context HttpServletRequest req, @PathParam("id") String id, @Context UriInfo info,
			final String requestBody) {

		String action = info.getQueryParameters().getFirst("action");
		User trustedUser = (User) req.getAttribute("user");

		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Question> wrappedQuestion = DBHelper.getEntityFromFields(wherePredicatesMap, Question.class, em);

			if (wrappedQuestion.isPresent()) {
				Question unwrappedQuestion = wrappedQuestion.get();

				if (action == null) {
					// TODO add update user
					try {
						Question updatedQuestion = new Gson().fromJson(requestBody, Question.class);
						postsService.editQuestion(unwrappedQuestion, updatedQuestion, trustedUser);
					} catch (JsonSyntaxException e) {
						return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
					}
				} else {
					switch (action) {
					case "upvote":
						postsService.upvoteQuestion(unwrappedQuestion, trustedUser);
						break;

					case "follow":
						postsService.followQuestion(unwrappedQuestion, trustedUser);
						break;

					default:
						return CustomErrorResponse.INVALID_ACTION.getHTTPResponse();
					}
				}

			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}

		// TODO add toString
		return Response.ok().build();
	}

	@DELETE
	@Secured
	@Path("/questions/{id}")
	@Produces("application/json")
	public Response deleteQuestion(@Context HttpServletRequest req, @PathParam("id") String id) {
		User trustedUser = (User) req.getAttribute("user");

		try {
		
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Question> wrappedQuestion = DBHelper.getEntityFromFields(wherePredicatesMap, Question.class, em);

			if (wrappedQuestion.isPresent()) {
				Question unwrappedQuestion = wrappedQuestion.get();

				if (unwrappedQuestion.getAuthor().equals(trustedUser)) {
					postsService.deleteQuestion(unwrappedQuestion, trustedUser);
				} else {
					return CustomErrorResponse.PERMISSION_DENIED.getHTTPResponse();
				}
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}

		// TODO add toString
		return Response.ok().build();

    }

	@GET
	@Path("/questions/{id}")
	@Produces("application/json")
	public Response getQuestion(@PathParam("id") String id) {
		Question unwrappedQuestion;
		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Question> wrappedQuestion = DBHelper.getEntityFromFields(wherePredicatesMap, Question.class, em);

			if (wrappedQuestion.isPresent()) {
				unwrappedQuestion = wrappedQuestion.get();
		return Response.ok(unwrappedQuestion.toString()).build();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			log.info("Exception thrown while querying question with id : " + id + " : " + e.getMessage());
             return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
//			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Secured
	@Path("/questions/me")
	@Produces("application/json")
	public Response getMyQuestions(@Context HttpServletRequest req) {
		User trustedUser = (User) req.getAttribute("user");
		
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		return Response.ok(gson.toJson(trustedUser.getQuestions())).build();
	}

	@POST
	@Secured
	@Path("/questions/{qid}/answers")
	@Consumes("application/json")
	public Response newAnswer(@PathParam("qid") String id, @Context HttpServletRequest req,final String requestBody) {
		try {
            User trustedUser = (User) req.getAttribute("user");
            Answer answer = new Gson().fromJson(requestBody, Answer.class);
            postsService.createAnswer(id, answer, trustedUser);
            return Response.ok(answer.toString()).build();
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
        }
	}

	@PUT
	@Secured
	@Path("/answers/{aid}")
	@Consumes("application/json")
	public Response modifyAnswer(@PathParam("aid") String answerId, @Context UriInfo info,
			@Context HttpServletRequest req, final String requestBody) {

		String action = info.getQueryParameters().getFirst("action");
		User trustedUser = (User) req.getAttribute("user");
		Answer unwrappedAnswer;
		
		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", answerId);
			Optional<Answer> wrappedAnswer = DBHelper.getEntityFromFields(wherePredicatesMap, Answer.class, em);

			if (wrappedAnswer.isPresent()) {
				unwrappedAnswer = wrappedAnswer.get();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}

			if (action == null) {
				Answer updatedAnswer = new Gson().fromJson(requestBody, Answer.class);
				postsService.editAnswer(unwrappedAnswer, updatedAnswer, trustedUser);
			} else {
				switch (action) {
				case "validate":
					postsService.validateAnswer(unwrappedAnswer, trustedUser);
					break;
				case "upvote":
					postsService.upvoteAnswer(unwrappedAnswer, trustedUser);
					break;

				default:
					return CustomErrorResponse.INVALID_ACTION.getHTTPResponse();
				}
			}

		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}

		return Response.ok().build();
	}

	@DELETE
	@Secured
	@Path("/answers/{id}")
	public Response deleteAnswer(@Context HttpServletRequest req, @PathParam("id") String answerId) {
        try {
            User trustedUser = (User) req.getAttribute("user");
			postsService.deleteAnswer(answerId, trustedUser);
            return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
        } catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	@GET
	@Path("/answers/{id}")
	@Produces("application/json")
	public Response getAnswer(@PathParam("id") String id) {
		try {
            return Response.ok(postsService.getAnswer(id).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	@GET
	@Path("/questions/{qid}/answers")
	@Produces("application/json")
	public Response getAllAnswersOfQuestion(@PathParam("qid") String questionId) {
		Question unwrappedQuestion;

		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", questionId);
			Optional<Question> wrappedQuestion = DBHelper.getEntityFromFields(wherePredicatesMap, Question.class, em);

			if (wrappedQuestion.isPresent()) {
				unwrappedQuestion = wrappedQuestion.get();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}

		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
		
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		
		return Response.ok(gson.toJson(unwrappedQuestion.getAnswers())).build();
	}
	
	@GET
	@Secured
	@Path("/answers/me")
	@Produces("application/json")
	public Response getMyAnswers(@Context HttpServletRequest req) {
		User trustedUser = (User) req.getAttribute("user");
		
		GsonBuilder builder = new GsonBuilder();  
	    builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();  
		return Response.ok(gson.toJson(trustedUser.getAnswers())).build();
	}
}
