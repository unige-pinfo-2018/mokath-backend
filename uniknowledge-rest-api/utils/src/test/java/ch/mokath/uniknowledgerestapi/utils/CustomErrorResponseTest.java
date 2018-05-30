package ch.mokath.uniknowledgerestapi.utils;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
* @author ornela
* @author zue
*/
public class CustomErrorResponseTest {

	@Test
	public void CustomErrorResponseMustReturnValidHTTPResponse(){
        Response expectedResponse = Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Invalid JSON for Object\"}").build();
        Response customErrorResponse = CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();

        Assert.assertTrue(customErrorResponse.getStatus() == expectedResponse.getStatus());
        Assert.assertTrue(customErrorResponse.getEntity().toString().equals(expectedResponse.getEntity().toString()));
	}

}
