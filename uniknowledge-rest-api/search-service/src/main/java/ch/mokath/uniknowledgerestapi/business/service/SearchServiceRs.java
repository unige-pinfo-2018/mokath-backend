/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/*
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
*/

/**
 * @author zue
 */
@Path("")
public class SearchServiceRs {
	@Inject
	private SearchService searchService;

	@POST
	@Path("/")
	@Consumes("application/json")
    @Produces("application/json")
	public Response getSearch(final String requestBody) {
        return Response.status(Response.Status.BAD_REQUEST).entity("not implemented...yet...").build();

	}

}
