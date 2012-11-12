package model;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import workers.ProcessInfoWorker;

@XmlRootElement
public class ProcessInfoModel {
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ProcessInfoModel getProcessListing(@QueryParam("user") String user, @QueryParam("password") String password ) 
	{
		return ProcessInfoWorker.getProccessInfo(user, password);
	}
	
}
