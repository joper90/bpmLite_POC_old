package rest;

import guard.models.CompleteFormData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import config.Statics.GUID_KEY_MODE;

import database.DAO.BpmGuardDAO;

import workers.WorkItemRequestWorker;

@Path("/getFormData")
public class GetFormData {

	
	//This is get the form data, its based on the guid (form) and the user key passed in, which is authenticated.
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public CompleteFormData getAllFormData(@QueryParam("userId") String userId, @QueryParam("requestId") String requestId ) {
		
		CompleteFormData data = new CompleteFormData();
		//Check if the user key is valid.
		
		
		//Check if the guid is valid.
		if (WorkItemRequestWorker.isValidFormGuid(requestId, userId))
		{
			boolean keyTaken = BpmGuardDAO.instance.getKeyStoreDAO().isKeyTaken(requestId);
			if (keyTaken)
			{
				data.setKeyTaken(true);
			}
			else
			{
				data.setValid(true);
				//WE have a valid guid, with a valid user key.
				data = WorkItemRequestWorker.getAllData(requestId);
				data.setValid(true);
				data.setUserId(userId);
				data.setRequestId(requestId);
				
				//Mark the form as taken now
				BpmGuardDAO.instance.getKeyStoreDAO().updateKeyStoreStatus(userId, requestId, GUID_KEY_MODE.TAKEN);
			}
		}
		
		return data;
	}
	
}
