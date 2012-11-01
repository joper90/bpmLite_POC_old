package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Validation;
import workers.ValidationWorker;
import config.Statics;

@Path("/validateServer")
public class ValidateServer {

/*	@Context
	UriInfo uriInfo;
	@Context
	Request request;*/


	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Validation checkServerApp(@QueryParam("key") String key) {

		// Check for validation and return
		Validation v = new Validation();
		v.setVersion(Statics.VERSION);
		v.setAlive(Statics.isAlive);

		// Checking the key:
		System.out.println("key passed : " + key);
		v.setAlive(ValidationWorker.isValid("root", key) ? true : false);

		return v;
	}

}
