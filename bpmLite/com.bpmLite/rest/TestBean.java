package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.xmlbeans.XmlException;

import com.bpmlite.api.RequestCallBackDocument;

@Path("/TestBean")
public class TestBean {

	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response deployProcess(String processArtifact)
	{

		RequestCallBackDocument r = null;
		try {
			r = RequestCallBackDocument.Factory.parse(processArtifact);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(r.getRequestCallBack().getRequestGuid());

		
		return Response.status(201).entity(r.xmlText()).build();
	}
	
	
}
