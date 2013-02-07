package rest;

import guard.models.Validation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import workers.ValidationWorker;
import config.StaticsCommon;

@Path("/validateGuardServer")
public class ValidateServer {

/*	@Context
	UriInfo uriInfo;
	@Context
	Request request;*/

	
	/*
	 * Checks to see if the server is up and running.. i.e the guard is alive.
	 */

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Validation checkServerApp(@QueryParam("key") String key) {

		// Check for validation and return
		Validation v = new Validation();
		v.setVersion(StaticsCommon.VERSION);
		v.setAlive(StaticsCommon.guardIsAlive);

		// Checking the key:
		System.out.println("key passed : " + key);
		v.setAlive(ValidationWorker.isValid("root", key) ? true : false);

		return v;
	}

}
