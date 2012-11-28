package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.xmlbeans.XmlException;

import model.ReturnModel;

import workers.DeployProcessWorker;

import com.bpmlite.api.ProcessArtifactDocument;


@Path("/DeployProcess")
public class DeployProcess {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deployProcess(@QueryParam("user") String user,@QueryParam("password") String pass, String processArtifactDoc)
	{
		
		boolean validDoc = true;
		ReturnModel ret = new ReturnModel();
		
		ProcessArtifactDocument paDoc = null;
		try {
			paDoc = ProcessArtifactDocument.Factory.parse(processArtifactDoc);
		} catch (XmlException e) {
			System.out.println("[ENGINE] deployProcess rest - cannot parse document..");
			validDoc = false;
			e.printStackTrace();
		}
		
		if (validDoc)
		{
			 ret = DeployProcessWorker.deployProcess(user, pass, paDoc);
		}else
		{
			ret.setReason("Cannot parse processArtifactDoc");
			ret.setResult("Deploy Failed");
			ret.setWorked(false);
		}
		
		return Response.status(201).entity(ret).build();
	}
	
}
