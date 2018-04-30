/**
 * 
 */
package com.mokath.uniknowledgerestapi;

import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

/**
 * @author tv0g
 *
 */

public enum CustomErrorResponse{
	
	INVALID_JSON_OBJECT(Response.Status.BAD_REQUEST, "Invalid JSON for Object"),
	ERROR_OCCURED(Response.Status.INTERNAL_SERVER_ERROR, "An error occured"),
	INVALID_CREDS(Response.Status.NOT_FOUND, "Invalid username or password");
	
	private String errorMessage;
	private Response.Status status;
	
	CustomErrorResponse(Response.Status status, String errorMessage){
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	private JsonObject getJSON() {
		JsonObject errorJSON = new JsonObject();
		errorJSON.addProperty("error", this.errorMessage);
		return errorJSON;
	}
	
	public Response getHTTPResponse() {
		
		return Response.status(this.status).entity(this.getJSON().toString()).build();
	}

}
