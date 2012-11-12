package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import workers.StartProcessWorker;

import model.ReturnModel;

@Path("/StartProcess")
public class StartProcess {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnModel startProcess (String name, String password, String processName, String processId)
	{		
		return StartProcessWorker.startProcess(name, password, processName, processId);
	}

}
