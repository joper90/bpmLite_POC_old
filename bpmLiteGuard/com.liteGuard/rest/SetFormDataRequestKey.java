package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.WorkItemDetails;
import workers.WorkItemRequestWorker;

@Path("/setFormDetailsRequestKey")
public class SetFormDataRequestKey {

	//Required new generated FormDataRequestKey.. 
	//root key.
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String setDetails(@QueryParam("rootKey") String rootKey, WorkItemDetails formData) {
		System.out.println("Set form data request key Called : " + rootKey);
		WorkItemRequestWorker.injectNewKey(rootKey, formData);
		return "Worked";
	}
	
	//Testing to get jSON data
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public WorkItemDetails getMockDetails() {
		WorkItemDetails fData = new WorkItemDetails();
		fData.setProcessId(111);
		fData.setStepId(222);
		fData.setUniqueFormGuid("DummyGUID");

		return fData;
	}
	
	
}
