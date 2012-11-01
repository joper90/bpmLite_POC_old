package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import workers.WorkItemRequestWorker;

import model.CompleteFormData;

@Path("/getFormData")
public class GetFormData {

	
	//This is get the form data, its based on the guid (form) and the user key passed in, which is authenticated.
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public CompleteFormData checkServerApp(@QueryParam("userKey") String userKey, @QueryParam("formId") String formIdGuid ) {
		
		CompleteFormData data = new CompleteFormData();
		//Check if the user key is valid.
		
		
		//Check if the guid is valid.
		if (WorkItemRequestWorker.isValidFormGuid(formIdGuid, userKey))
		{
			//WE have a valid guid, with a valid user key.
			data = WorkItemRequestWorker.getAllData(formIdGuid);
		}
		
		return data;
	}
	
}
