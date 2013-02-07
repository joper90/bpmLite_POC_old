package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lite.models.ReturnModel;
import workers.StartProcessWorker;

@Path("/startProcess")
public class StartProcess {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startProcess (@QueryParam("user") String user, 
									@QueryParam("password")String password, 
									@QueryParam("processName")String processName, 
									@QueryParam("version")String version)
	{	
		ReturnModel ret = new ReturnModel();
		ret = StartProcessWorker.startProcess(user, password, processName, version);
		
		if (ret.isWorked())
		{
			return Response.status(201).entity(ret).build();
		}
		return Response.status(500).entity(ret).build();
	}

}
