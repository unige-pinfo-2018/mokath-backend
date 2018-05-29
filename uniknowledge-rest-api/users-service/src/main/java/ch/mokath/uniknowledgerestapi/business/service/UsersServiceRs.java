/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.AuthInfos;
import ch.mokath.uniknowledgerestapi.dom.Token;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
import ch.mokath.uniknowledgerestapi.utils.Secured;

/**
 * @author tv0g
 * @author zue
 */
@Path("")
public class UsersServiceRs {
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	private UsersService usersService;

	private Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

	// ================================================================================
	// Public Endpoints
	// ================================================================================

	@POST
	@Path("/users")
	@Produces("application/json")
	@Consumes("application/json")
	public Response registerUser(@NotNull final String requestBody) {

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
			log.error("Invalid JSON Format for object : " + u.toString()+ " : "+e.getMessage());
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			log.error("Error thrown while creating a new user : "+e.getMessage());
			return CustomErrorResponse.IDENTIFIER_ALREADY_USED.getHTTPResponse();
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

	// ================================================================================
	// Secured Endpoints
	// ================================================================================

	@POST
	@Secured
	@Path("/logout")
	@Produces("application/json")
	public Response logout(@Context HttpServletRequest req) {

		Token token = (Token) req.getAttribute("token");
		
		try {
			usersService.logout(token);
			return CustomErrorResponse.LOGOUT_SUCCESS.getHTTPResponse();
		} catch(Exception e) {
			log.error("Exception thrown while logging out : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}

	/**
	 * Update user according to provided information in request body. MAKE SURE to
	 * check if the targeted user is the user querying the update by checking the
	 * match between user ID from request (if provided) and trusted user ID from
	 * token (Verified by AuthenticationMiddleware) IF not checked, this can lead to
	 * IDOR vulnerability
	 * (https://www.owasp.org/index.php/Top_10_2013-A4-Insecure_Direct_Object_References)
	 * 
	 * @param req
	 *            Request Context inherited from AuthenticationMiddleware
	 * @param requestBody
	 *            Request JSON Body
	 * @return HTTP Responses : 401 if unauthorized update, 500 if internal error,
	 *         200 if update was successful
	 */
	@PUT
	@Secured
	@Path("/users")
	@Produces("application/json")
	@Consumes("application/json")
	public Response updateUser(@Context HttpServletRequest req, @NotNull final String requestBody) {

        // Retrieve user from signed Token
        User trustedUser = (User) req.getAttribute("user");

		try {
            GsonBuilder builder = new GsonBuilder();  
            builder.excludeFieldsWithoutExposeAnnotation();  
            Gson gson = builder.create();  
		
            // Retrieve User informations from request body
            User requestUpdatedUser = gson.fromJson(requestBody, User.class);

            if (requestUpdatedUser.getId() != null) {
                Long untrustedID = requestUpdatedUser.getId();
			
                // If the issuer of the request is not the targeted user
                if (trustedUser.getId() != untrustedID) {
                    return CustomErrorResponse.PERMISSION_DENIED.getHTTPResponse();
                }
            }
            // Make sure ID and password remain unchanged
            requestUpdatedUser.setID(trustedUser.getId());
            requestUpdatedUser.setPassword(trustedUser.getPassword());
            // also pass earned Points
            requestUpdatedUser.setPoints(trustedUser.getPoints());

            // If all checks pass, we update the user
            User updatedUser = usersService.updateUser(requestUpdatedUser);
            return Response.ok(updatedUser.toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
        } catch (Exception e) {
            log.info("Exception thrown while updating user with id : " + trustedUser.getId() + " : " + e.getMessage());
            return CustomErrorResponse.IDENTIFIER_ALREADY_USED.getHTTPResponse();
		}
	}

	@DELETE
	@Secured
	@Path("/users")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deleteUser(@Context HttpServletRequest req, @NotNull final String requestBody) {
		
		// Retrieve user from trusted Token
		User trustedUser = (User) req.getAttribute("user");
		Token trustedToken = (Token) req.getAttribute("token");
		
		// Revoke the session and delete the user
		try {
			usersService.logout(trustedToken);
			usersService.deleteUser(trustedUser);
			return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
		} catch(Exception e) {
			log.error("Exception thrown while deleting user with id : "+trustedUser.getId()+ " : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Secured
	@Path("/users/me")
	@Produces("application/json")
	public Response getUser(@Context HttpServletRequest req) {
		User trustedUser = (User) req.getAttribute("user");
		
		return Response.ok(trustedUser.toString()).build();
	}
}
