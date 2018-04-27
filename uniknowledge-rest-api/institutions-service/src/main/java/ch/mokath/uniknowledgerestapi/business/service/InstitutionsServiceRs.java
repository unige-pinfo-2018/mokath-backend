/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Institution;

/**
 * @author tv0g
 *
 */
@Path("institutions")
public class InstitutionsServiceRs {

	@Inject
	private InstitutionsService institutionsService;

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createInstitution(@NotNull final String requestBody) {

		Institution i = new Gson().fromJson(requestBody, Institution.class);

		try {
			institutionsService.createInstitution(i);
		} catch (JsonSyntaxException e) {

			return Response.status(400, "Invalid JSON for Resource").build();
		}

		return Response.ok(i.toString()).build();
	}
}
