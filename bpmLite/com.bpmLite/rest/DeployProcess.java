package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.ReturnModel;
import workers.DeployProcessWorker;

import commonModels.deployment.PAProcessArtifact;


@Path("/DeployProcess")
public class DeployProcess {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnModel deployProcess(@QueryParam("user") String user,@QueryParam("password") String pass, PAProcessArtifact processArtifact)
	{
		return DeployProcessWorker.deployProcess(user, pass, processArtifact);
	}
	
}
