/**
 * 
 */
package ch.mokath.uniknowledgerestapi.utils;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

/**
 * @author tv0g
 * @author zue
 */

public enum CustomErrorResponse {

	// ERRORS
	INVALID_JSON_OBJECT(false, Response.Status.BAD_REQUEST, "Invalid JSON for Object"), 
	ERROR_OCCURED(false,Response.Status.INTERNAL_SERVER_ERROR, "An error occured"), 
	INVALID_CREDS(false, Response.Status.NOT_FOUND, "Invalid username or password"), 
	ALREADY_LOGGED_IN(false, Response.Status.BAD_REQUEST,"You're already logged in !"), 
	INVALID_TOKEN(false, Response.Status.BAD_REQUEST,"Invalid Token !"),
	RESSOURCE_NOT_FOUND(false, Response.Status.NOT_FOUND, "Ressource not found !"),
	INVALID_ACTION(false, Response.Status.BAD_REQUEST, "Invalid action !"),
	PERMISSION_DENIED(false, Response.Status.UNAUTHORIZED, "Permission Denied !"),
	IDENTIFIER_ALREADY_USED(false, Response.Status.BAD_REQUEST, "Email or Username already in use !"),
//	BAD_REQUEST(false, Response.Status.BAD_REQUEST, "Bad request"),
	INVALID_INPUT(false, Response.Status.BAD_REQUEST, "Invalid input"),
	CONSTRAINT_VIOLATION(false, Response.Status.BAD_REQUEST, "Constraint violation"),

	// SUCCESS
	LOGOUT_SUCCESS(true,Response.Status.OK, "Logout Successful !"),
	DELETE_SUCCESS(true, Response.Status.OK, "Deleted resource Successfully"),
	OPERATION_SUCCESS(true, Response.Status.OK, "Operation Successful");

	private String successOrError;
	private String message;
	private Response.Status status;

	CustomErrorResponse(boolean isSuccess, Response.Status status, String errorMessage) {
		this.status = status;
		this.message = errorMessage;

		if (isSuccess)
			this.successOrError = "success";
		else
			this.successOrError = "error";
	}

	private JsonObject getJSON() {
		JsonObject errorJSON = new JsonObject();
		errorJSON.addProperty(this.successOrError, this.message);
		return errorJSON;
	}

	public Response getHTTPResponse() {

		return Response.status(this.status).entity(this.getJSON().toString()).build();
	}

}
