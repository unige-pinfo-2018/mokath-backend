/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

		JsonObject jsonDatas = Json.createReader(new StringReader(requestBody)).readObject();

		final String name = jsonDatas.getString("name");
		final String logoPictureURL = jsonDatas.getString("logoPictureURL");
		final String contactEmail = jsonDatas.getString("contactEmail");

		// TODO: Refactor parsing into util function
		Set<String> domains = new HashSet<String>();
		jsonDatas.getJsonArray("domains").forEach($ -> domains.add($.asJsonObject().toString()));

		Set<String> administrators = new HashSet<String>();
		jsonDatas.getJsonArray("administrators").forEach($ -> administrators.add($.asJsonObject().toString()));

		Set<String> askers = new HashSet<String>();
		jsonDatas.getJsonArray("askers").forEach($ -> askers.add($.asJsonObject().toString()));

		Set<String> repliers = new HashSet<String>();
		jsonDatas.getJsonArray("repliers").forEach($ -> repliers.add($.asJsonObject().toString()));

		institutionsService.createInstitution(
				new Institution.Builder().with(
						$ -> {
							$.name = name;
							$.logoPictureURL = logoPictureURL;
							$.contactEmail = contactEmail;
							$.administrators = administrators;
							$.askers = askers;
							$.repliers = repliers;
						})
				.build()
				);
		
		Boolean success = true;
		if (!success) {
			return Response.status(401).build();
		}

		return Response.ok(success).build();
	}
}
