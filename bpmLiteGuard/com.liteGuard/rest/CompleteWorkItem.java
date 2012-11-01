package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.FormData;
import model.ReturnModel;
import workers.CompleteWorkItemWorker;

@Path("/completeWorkItem")
public class CompleteWorkItem {

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public ReturnModel setDetails(@QueryParam("userKey") String userKey, @QueryParam("formId") String formIdGuid,  @QueryParam("action") String action, FormData[] formData)
	{
		return CompleteWorkItemWorker.completeWorkItem(userKey, formIdGuid, action, formData);
		
	}

	
}
