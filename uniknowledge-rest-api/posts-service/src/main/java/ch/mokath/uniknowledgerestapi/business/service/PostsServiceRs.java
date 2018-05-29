/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.inject.Inject;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
import ch.mokath.uniknowledgerestapi.utils.Secured;

/**
 * @author matteo113
 *
 */
@Path("")
public class PostsServiceRs {

	@Inject
	private PostsService postsService;

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
	public Response modifyQuestion(@Context HttpServletRequest req, @PathParam("id") String questionId, @Context UriInfo info,
			final String requestBody) {
		try {
            String action = info.getQueryParameters().getFirst("action");
            User trustedUser = (User) req.getAttribute("user");
            if (action == null) {
                Question updatedQuestion = new Gson().fromJson(requestBody, Question.class);
                return Response.ok(postsService.editQuestion(questionId, updatedQuestion, trustedUser).toString()).build();
            }else{
				switch (action) {
				case "follow":
					postsService.followQuestion(questionId, trustedUser);
					return CustomErrorResponse.OPERATION_SUCCESS.getHTTPResponse();
				case "upvote":
					postsService.upvoteQuestion(questionId, trustedUser);
					return CustomErrorResponse.OPERATION_SUCCESS.getHTTPResponse();
				default:
					return CustomErrorResponse.INVALID_ACTION.getHTTPResponse();
				}
            }
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	@DELETE
	@Secured
	@Path("/questions/{id}")
	@Produces("application/json")
	public Response deleteQuestion(@Context HttpServletRequest req, @PathParam("id") String questionId) {
		try {
            User trustedUser = (User) req.getAttribute("user");
			postsService.deleteQuestion(questionId, trustedUser);
            return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
    }

	@GET
	@Path("/questions")
	@Produces("application/json")
	public Response getQuestions() {
		try {
            return Response.ok(postsService.getQuestions().toString()).build();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Path("/questions/{id}")
	@Produces("application/json")
	public Response getQuestion(@PathParam("id") String id) {
		try {
            return Response.ok(postsService.getQuestion(id).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Secured
	@Path("/questions/me")
	@Produces("application/json")
	public Response getMyQuestions(@Context HttpServletRequest req) {
		try {
            User trustedUser = (User) req.getAttribute("user");
            return Response.ok(postsService.getMyQuestions(trustedUser).toString()).build();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	@GET
	@Path("/questions/{id}/followers")
	@Produces("application/json")
	public Response getQuestionFollowers(@PathParam("id") String id) {
		try {
            return Response.ok(postsService.getQuestionFollowers(id).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Path("/questions/{id}/upvotes")
	@Produces("application/json")
	public Response getQuestionUpvotes(@PathParam("id") String id) {
		try {
            return Response.ok(postsService.getQuestionUpvotes(id).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	
	/** ANSWER **/
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
		try {
            String action = info.getQueryParameters().getFirst("action");
            User trustedUser = (User) req.getAttribute("user");
			if (action == null) {
                Answer updatedAnswer = new Gson().fromJson(requestBody, Answer.class);
                return Response.ok(postsService.editAnswer(answerId, updatedAnswer, trustedUser).toString()).build();
			} else {
				switch (action) {
				case "validate":
					postsService.validateAnswer(answerId, trustedUser);
					return CustomErrorResponse.OPERATION_SUCCESS.getHTTPResponse();
				case "upvote":
					postsService.upvoteAnswer(answerId, trustedUser);
					return CustomErrorResponse.OPERATION_SUCCESS.getHTTPResponse();
				default:
					return CustomErrorResponse.INVALID_ACTION.getHTTPResponse();
				}
            }
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
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
	@Path("/answers/{id}/updvotes")
	@Produces("application/json")
	public Response getAnswerUpvotes(@PathParam("id") String id) {
		try {
            return Response.ok(postsService.getAnswerUpvotes(id).toString()).build();
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
		try {
            return Response.ok(postsService.getQuestionAnswers(questionId).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
    }
	
	@GET
	@Secured
	@Path("/answers/me")
	@Produces("application/json")
	public Response getMyAnswers(@Context HttpServletRequest req) {
		try {
            User trustedUser = (User) req.getAttribute("user");
            return Response.ok(postsService.getMyAnswers(trustedUser).toString()).build();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
}
