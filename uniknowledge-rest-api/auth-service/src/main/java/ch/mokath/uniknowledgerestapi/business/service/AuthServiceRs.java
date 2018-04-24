/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.io.StringReader;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author tv0g
 *
 */
@Path("auth")
public class AuthServiceRs {

	@Inject
	private AuthService authService;

	@POST
	@Path("/login")
	@Produces("application/json")
	@Consumes("application/json")
	public Response login(@NotNull final String requestBody) {

		JsonObject jsonDatas = Json.createReader(new StringReader(requestBody)).readObject();
		final String email = jsonDatas.getString("email");
		final String password = jsonDatas.getString("password");

		boolean success = false;

		String token = authService.login(email, password);

		success = true;

		if (!success) {
			return Response.status(401).build();
		}
		
		return Response.ok(token).build();
	}

	@POST
	@Path("/logout")
	@Produces("application/json")
	@Consumes("application/json")
	public Response logout(@NotNull final String requestBody) {

		JsonObject jsonDatas = Json.createReader(new StringReader(requestBody)).readObject();
		final String JWToken = jsonDatas.getString("token");

		Boolean isLoggedOut = authService.logout(JWToken);

		if (!isLoggedOut)
			return Response.status(500).build();
		return Response.ok("Successfully logged out").build();
	}

}
