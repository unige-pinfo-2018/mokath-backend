/**
 * 
 */
package ch.mokath.uniknowledgerestapi.utils;

import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;


/**
 * @author zue
 *
 * Used as a workaround for:
 *  throwing EntityExistsException on services will not be caught in RS as EntityExistsException,
 *  but as generic Exception
 */

public class CustomException extends Exception {

    private String message;
    private Response.Status status = Response.Status.BAD_REQUEST;
    private static final String successOrError = "error";
    
    public CustomException(String message){
        this.message = message;
    }
    
    public CustomException(String message,Response.Status status){
        this.message = message;
        this.status = status;
    }
    
	public Response getHTTPJsonResponse() {
		return Response.status(this.status).entity(this.getJSON().toString()).build();
	}


	private JsonObject getJSON() {
		JsonObject errorJSON = new JsonObject();
		errorJSON.addProperty(this.successOrError, this.message);
		return errorJSON;
	}

}
