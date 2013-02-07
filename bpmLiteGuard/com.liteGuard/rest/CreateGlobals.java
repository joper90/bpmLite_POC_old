package rest;

import guard.models.GlobalDataRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lite.models.ReturnModel;
import workers.Transformers;
import workers.ValidationWorker;
import config.StaticsCommon;
import database.GlobalData;
import database.DAO.BpmGuardDAO;


@Path("/createGlobals")
public class CreateGlobals {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnModel createGlobal(@QueryParam("userId") String userId, @QueryParam("key") String key, GlobalDataRequest globalDataRequest) 
	{
		ReturnModel ret = new ReturnModel();
		
		if (ValidationWorker.isValid(userId, key))
		{
			//Valid root user so we can now process the global data.
			//The old switcharoo..
			GlobalData gData = Transformers.requestToGlobalData(globalDataRequest);
			
			//Get then next element in the globals.
			Integer fieldId = BpmGuardDAO.instance.getServerInfoDAO().findDataAsIntAndIncrement(StaticsCommon.INITIAL_GLOBAL_COUNT);
			gData.setFieldId(fieldId);
			
			//Save off the gData		
			boolean insertFieldData = BpmGuardDAO.instance.getGlobalDataDAO().insertFieldData(gData);
			if (insertFieldData)
			{
				ret.setWorked(true);
				ret.addExtendedDataElement("fieldId", fieldId.toString());
				ret.setResult(StaticsCommon.SUCCESS);
			}else
			{
				ret.setReason("Cannot save new Data as :" + fieldId );
			}
			
			
		}else
		{
			ret.setReason("Invalid user");
		}
		
		return ret;
	}	
	
}
