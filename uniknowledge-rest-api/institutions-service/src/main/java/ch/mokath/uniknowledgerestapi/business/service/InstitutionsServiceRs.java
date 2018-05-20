/**
 * 
 */
package ch.mokath.uniknowledgerestapi.business.service;

import javax.inject.Inject;
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

/**
 * @author tv0g
 * @author zue
 */
@Path("institutions")
public class InstitutionsServiceRs {
	@Inject
	private InstitutionsService institutionsService;

	private Logger log = LoggerFactory.getLogger(InstitutionsServiceImpl.class);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createInstitution(@NotNull final String requestBody) {
		try {
            Institution i = new Gson().fromJson(requestBody, Institution.class);
			institutionsService.createInstitution(i);
            return Response.ok(i.toString()).build();
		} catch (JsonSyntaxException jse) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("blabla"+ce.toString()).build();
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
            return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Path("/{id}")
    @Produces("application/json")
	public Response getInstitution(@PathParam("id") String id) {
		try {
            return Response.ok(institutionsService.getInstitution(id).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return  CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@GET
	@Path("/")
    @Produces("application/json")
	public Response getInstitutions() {
		try {
            return Response.ok(institutionsService.getInstitutions().toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
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
            Institution i = new Gson().fromJson(requestBody,Institution.class);
            return Response.ok(institutionsService.updateInstitution(i,id).toString()).build();
		} catch (JsonSyntaxException jse) {
			return CustomErrorResponse.INVALID_JSON_OBJECT.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteInstitution(@PathParam("id") String id){
		try {
            institutionsService.deleteInstitution(id);
            return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
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
		try {
            return  Response.ok(institutionsService.addUser(uid,iid).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
    }
	
	@GET
	@Path("/{iid}/users/")
    @Produces("application/json")
	public Response getUsers(@PathParam("iid") String iid) {
		try {
			return Response.ok(institutionsService.getUsers(iid).toString()).build();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch (Exception e) {
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
	
	@DELETE
	@Path("/{iid}/user/{uid}")
	@Produces("application/json")
	public Response removeUser(@PathParam("uid") String uid,@PathParam("iid") String iid){
		try {
            institutionsService.removeUser(uid,iid);
			return CustomErrorResponse.DELETE_SUCCESS.getHTTPResponse();
        } catch (CustomException ce) {
            return ce.getHTTPJsonResponse();
		} catch(Exception e) {
			log.error("Exception thrown while removing user with id : "+uid+ " : "+e.getMessage());
			return CustomErrorResponse.ERROR_OCCURED.getHTTPResponse();
		}
	}
}
