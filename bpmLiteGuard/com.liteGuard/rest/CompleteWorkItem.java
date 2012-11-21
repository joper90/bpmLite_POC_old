package rest;

import guard.models.FormData;
import guard.models.ReturnModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import workers.CompleteWorkItemWorker;

@Path("/completeWorkItem")
public class CompleteWorkItem {

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ReturnModel setDetails(@QueryParam("userKey") String userKey, @QueryParam("formId") String formIdGuid,  @QueryParam("action") String action, FormData[] formData)
	{
		return CompleteWorkItemWorker.completeWorkItem(userKey, formIdGuid, action, formData);
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public ReturnModel setDetailsWithJsonString(@QueryParam("userKey") String userKey, @QueryParam("formId") String formIdGuid,  @QueryParam("action") String action, FormData[] formData)
	{
		return CompleteWorkItemWorker.completeWorkItem(userKey, formIdGuid, action, formData);
		
	}

	
}
