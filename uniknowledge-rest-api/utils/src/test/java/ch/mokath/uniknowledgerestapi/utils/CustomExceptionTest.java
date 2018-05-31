package ch.mokath.uniknowledgerestapi.utils;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
* @author ornela
* @author zue
*/
public class CustomExceptionTest {

	@Test
	public void CustomExceptionMessageAndHTTPResponseTest(){
        String message = "This is a Test message";
        Response expectedResponse = Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+message+"\"}").build();
        CustomException ce = new CustomException(message);
        Response customExceptionResponse = ce.getHTTPJsonResponse();

        Assert.assertTrue(customExceptionResponse.getStatus() == expectedResponse.getStatus());
        Assert.assertTrue(customExceptionResponse.getEntity().toString().equals(expectedResponse.getEntity().toString()));
	}

	@Test
	public void CustomExceptionWithResponseStatusTest(){
        String message = "This is a Test message with Status OK";
        Response.Status status = Response.Status.OK;
        Response expectedResponse = Response.status(status).entity("{\"error\":\""+message+"\"}").build();
        CustomException ce = new CustomException(message,status);
        Response customExceptionResponse = ce.getHTTPJsonResponse();

        Assert.assertTrue(customExceptionResponse.getStatus() == expectedResponse.getStatus());
        Assert.assertTrue(customExceptionResponse.getEntity().toString().equals(expectedResponse.getEntity().toString()));
	}
}
