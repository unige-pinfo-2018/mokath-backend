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
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON for Resource").build();
		} catch (EntityExistsException eee) { //TODO get this to work and set correct message
            return Response.status(Response.Status.BAD_REQUEST).entity(eee).build();//CustomErrorResponse.IDENTIFIER_ALREADY_USED.getHTTPResponse();
		} catch (Exception e) { //TODO if EntityExistsException does not work, change message (!username)
            return CustomErrorResponse.IDENTIFIER_ALREADY_USED.getHTTPResponse();
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
                return Response.ok(unwrappedInst.toString()).build();//TODO MAKE THIS WORK WITHOUT LAZY EXCEPTION!!!!!
			} else {
				return CustomErrorResponse.RESSOURCE_NOT_FOUND.getHTTPResponse();
			}
		} catch (Exception e) {
			return  Response.status(Response.Status.BAD_REQUEST).entity(e).build();//CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateInstitution(@Context HttpServletRequest req,@NotNull final String requestBody,@PathParam("id") String id) {
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
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	
	@DELETE //TODO message if nothing deleted
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteInstitution(@Context HttpServletRequest req, @PathParam("id") String id){
		Institution inst = new Institution();
		inst.setId(id);
		try {
			institutionsService.deleteInstitution(inst);
			return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
		} catch(Exception e) {
			log.error("Exception thrown while deleting institution with id : "+id+ " : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
}
