/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.AuthInfos;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.Secured;

/**
 * @author tv0g
 *
 */
@Path("")
public class UsersServiceRs {

	@Inject
	private UsersService usersService;

	private Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

	@POST
	@Path("/register")
	@Produces("application/json")
	@Consumes("application/json")
	public Response register(@NotNull final String requestBody) {

		User u = new Gson().fromJson(requestBody, User.class);
		AuthInfos a = new AuthInfos(u.getEmail(), u.getPassword());

		try {
			String passToStore = a.createHash(a.getPassword());
			u.setPassword(passToStore);
		} catch (NoSuchAlgorithmException e) {

			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();

		} catch (InvalidKeySpecException e) {

			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}

		try {
			usersService.createUser(u);
		} catch (JsonSyntaxException e) {

			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		}

		return Response.ok(u.toString()).build();
	}

	@POST
	@Path("/login")
	@Produces("application/json")
	@Consumes("application/json")
	public Response login(@NotNull final String requestBody) {

		AuthInfos a = new Gson().fromJson(requestBody, AuthInfos.class);

		try {

			Optional<String> token = usersService.login(a);

			if (token.isPresent()) {
				JsonObject tokenJSON = new JsonObject();
				tokenJSON.addProperty("token", token.get());
				return Response.ok(tokenJSON.toString()).build();
			} else {
				return CustomErrorResponse.INVALID_CREDS.getHTTPResponse();
			}
		} catch (JsonSyntaxException e) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		} catch (NoSuchAlgorithmException e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		} catch (InvalidKeySpecException e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		} catch (EntityExistsException e) {
			return CustomErrorResponse.ALREADY_LOGGED_IN.getHTTPResponse();
		}
	}

	@GET
	@Secured
	@Path("/logout")
	@Produces("application/json")
	public Response logout(@Context HttpServletRequest req) {

		Token token = (Token) req.getAttribute("token");
		usersService.logout(token);
		return CustomErrorResponse.LOGOUT_SUCCESS.getHTTPResponse();
	}
}
