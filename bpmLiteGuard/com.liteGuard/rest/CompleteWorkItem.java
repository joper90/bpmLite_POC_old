package rest;

import guard.models.CompleteFormData;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lite.models.ReturnModel;

import workers.CompleteWorkItemWorker;


@Path("/completeWorkItem")
public class CompleteWorkItem {

	
/*	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ReturnModel setDetails(@QueryParam("userKey") String userKey, @QueryParam("formId") String formIdGuid,  @QueryParam("action") String action, CompleteFormData completeFormData)
	{
		return CompleteWorkItemWorker.completeWorkItem(userKey, formIdGuid, action, completeFormData);
		
	}*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setDetailsWithJsonString(@QueryParam("userId") String userId, @QueryParam("requestId") String requestId,  @QueryParam("action") String action, CompleteFormData completeFormData)
	{

		 ReturnModel ret = CompleteWorkItemWorker.completeWorkItem(userId, requestId, action, completeFormData);
		 
		 return Response.status(201).entity(ret).build();
		
	}

	
}
