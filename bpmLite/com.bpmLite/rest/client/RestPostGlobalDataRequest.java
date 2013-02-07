package rest.client;

import guard.models.GlobalDataRequest;

import javax.ws.rs.core.MediaType;

import lite.models.ReturnModel;

import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.Fields;
import com.sun.jersey.api.client.WebResource;

public class RestPostGlobalDataRequest extends RestBase{

	
	public static Integer doPost(Fields fieldData)
	{
		GlobalDataRequest globalDataRequest = new GlobalDataRequest();
		globalDataRequest.setName(fieldData.getName());
		globalDataRequest.setData(fieldData.getInitalData());
		globalDataRequest.setType(fieldData.getType().toString());
		
	    WebResource service = getWebClient();
	    
	    ReturnModel  response = new ReturnModel();
	    
	    //public ReturnModel createGlobal(@QueryParam("userId") String userId, @QueryParam("key") String key, GlobalDataRequest globalDataRequest)
	    //public ReturnModel createGlobal(@QueryParam("userId") String userId, @QueryParam("key") String key, GlobalDataRequest globalDataRequest)
	    response = service.path("rest").path("createGlobals")
				.queryParam("userId","root")
				.queryParam("key","12345")
				.type(MediaType.APPLICATION_JSON)
				.post(ReturnModel.class, globalDataRequest);
	  
	    if (response.isWorked())
	    {
	    	return new Integer(response.getExtendedData().get("fieldId"));
	    }
	    return 0;
	    
	}
	
}
