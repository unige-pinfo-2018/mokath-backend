/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import java.util.HashMap;
import java.util.Map;
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
import com.google.gson.JsonSyntaxException;

import ch.mokath.uniknowledgerestapi.dom.Institution;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.CustomErrorResponse;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;

/**
 * @author tv0g
 * @author zue
 */
@Path("institutions")
public class InstitutionsServiceRs {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private InstitutionsService institutionsService;
	private DBHelper DBHelper = new DBHelper();

	private Logger log = LoggerFactory.getLogger(InstitutionsServiceImpl.class);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createInstitution(@NotNull final String requestBody) {

		Institution i = new Gson().fromJson(requestBody, Institution.class);

		try {
			institutionsService.createInstitution(i);
		} catch (JsonSyntaxException jse) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		} catch (EntityExistsException eee) { //TODO get this to work and set correct message
//            return Response.status(Response.Status.BAD_REQUEST).entity(eee).build();
            return CustomErrorResponse.IDENTIFIER_ALREADY_USED_INST.getHTTPResponse();
		} catch (Exception e) { //TODO if EntityExistsException works, change message
            return CustomErrorResponse.IDENTIFIER_ALREADY_USED_INST.getHTTPResponse();
		}

		return Response.ok(i.toString()).build();
	}
	
	@GET
	@Path("/{id}")
    @Produces("application/json")
	public Response getInstitution(@PathParam("id") String id) {
		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

			if (wrappedInst.isPresent()) {
				Institution unwrappedInst = wrappedInst.get();
                return Response.ok(unwrappedInst.toString()).build();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return  CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateInstitution(@NotNull final String requestBody,@PathParam("id") String id) {
		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

			if (wrappedInst.isPresent()) {
				Institution unwrappedInst = wrappedInst.get();

                Institution requestedInst = new Gson().fromJson(requestBody,Institution.class);
                requestedInst.setId(unwrappedInst.getId());
                Institution updatedInst = institutionsService.updateInstitution(requestedInst);
                return Response.ok(updatedInst.toString()).build();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (JsonSyntaxException jse) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteInstitution(@Context HttpServletRequest req, @PathParam("id") String id){
		Institution inst = new Institution();
		inst.setId(id);
		try {
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id", id);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePredicatesMap,Institution.class,em);

			if (wrappedInst.isPresent()) {
                institutionsService.deleteInstitution(inst);
                return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch(Exception e) {
			log.error("Exception thrown while deleting institution with id : "+id+ " : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	/** User **/
	@PUT
	@Path("/{iid}/users/{uid}")
	@Produces("application/json")
	public Response addUser(@PathParam("iid") String iid,@PathParam("uid") String uid) {
		try { // get Institution and User
			Map<String, Object> wherePMinst = new HashMap<String, Object>();
			wherePMinst.put("id", iid);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

			Map<String, Object> wherePMuser = new HashMap<String, Object>();
			wherePMuser.put("id", uid);
			Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

			if (wrappedInst.isPresent() && wrappedUser.isPresent()) {
				Institution i = wrappedInst.get();
				User u = wrappedUser.get();
				institutionsService.addUser(u,i);
                return CustomErrorResponse.OPERATION_SUCCESS.getHTTPResponse();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
    }
	
	@GET
	@Path("/{iid}/users/")
    @Produces("application/json")
	public Response getUsers(@PathParam("iid") String iid) {
		try {
			Map<String, Object> wherePMinst = new HashMap<String, Object>();
			wherePMinst.put("id", iid);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

			if (wrappedInst.isPresent()) {
				Institution i = wrappedInst.get();
                return Response.ok(institutionsService.getUsers(i)).build();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@DELETE
	@Path("/{iid}/user/{uid}")
	@Produces("application/json")
	public Response removeUser(@PathParam("iid") String iid,@PathParam("uid") String uid){
		try {
			Map<String, Object> wherePMinst = new HashMap<String, Object>();
			wherePMinst.put("id", iid);
			Optional<Institution> wrappedInst = DBHelper.getEntityFromFields(wherePMinst,Institution.class,em);

			Map<String, Object> wherePMuser = new HashMap<String, Object>();
			wherePMuser.put("id", uid);
			Optional<User> wrappedUser = DBHelper.getEntityFromFields(wherePMuser,User.class,em);

			if (wrappedInst.isPresent() && wrappedUser.isPresent()) {
				Institution i = wrappedInst.get();
				User u = wrappedUser.get();
                boolean removed = institutionsService.removeUser(u,i);
                
                if(removed)  return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
                else return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch(Exception e) {
			log.error("Exception thrown while removing user with id : "+uid+ " : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
}
