package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import workers.ValidationWorker;

import model.ValidateDetails;

//Admin only (from the bpmLite server.. 

@Path("/setUserDetails")
public class SetUserDetails {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String setDetails(@QueryParam("rootKey") String rootKey, ValidateDetails validate) {
		System.out.println("Validate Called : " + rootKey);
		ValidationWorker.validateNewUser("key", validate);
		return "Worked";
	}

	// Get for test only. (Client Testsing) remove me.. 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ValidateDetails getMockDetails() {
		ValidateDetails v = new ValidateDetails();
		v.setUserKey("DummyData");
		v.setUserGuid("12345ABC");
		v.setUserName("joeh");

		return v;
	}
}
