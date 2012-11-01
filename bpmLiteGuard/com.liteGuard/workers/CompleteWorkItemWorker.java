package workers;

import java.util.ArrayList;

import model.CompleteFormData;
import model.FormData;
import model.ReturnModel;
import model.WorkItemDetails;
import config.Statics;
import engineConnector.EngineRestConnector;

public class CompleteWorkItemWorker {

	public static ReturnModel completeWorkItem( String userKey,  String formIdGuid, String action, FormData[] formData)
	{
		//Fist check the user key.
		if (WorkItemRequestWorker.isValidFormGuid(formIdGuid, userKey))
		{
			//Entry exists and matches.
			WorkItemDetails wItemDetails = WorkItemRequestWorker.getWorkItemDetailsFromGuid(formIdGuid, userKey);
			
			// Take a copy of the data from the database for a transaction rollback.					
			CompleteFormData currentData = WorkItemRequestWorker.getAllData(formIdGuid);

			//Now check the master server is currently Alive..
			
			if(EngineRestConnector.isAlive())
			{
				//Now commit the data..
				if (updateFormDataInDatabase(formIdGuid, formData))
				{
					//Updated.. so now we need to inform the server of the complete status. WITH THE ACTION
					if(EngineRestConnector.stepCompleted(wItemDetails.getProcessId(), wItemDetails.getStepId(), action))
					{
						new ReturnModel(Statics.REST_WORKED,"Step completed sucessfully", false);
					}
					else
					{				
						if (rollbackData(formIdGuid, currentData.getFormData()))
						{
							new ReturnModel(Statics.REST_FAILED,"Step not completed.. rolling back", false);
						}
						else
						{
							new ReturnModel(Statics.REST_FAILED,"Step not completed.. rolling back FAILED", false);
						}
					}
					
				
				}else
				{
					new ReturnModel(Statics.REST_FAILED,"Update form data failed.", false);
				}
			}
			else
			{
				new ReturnModel(Statics.REST_FAILED,"Bpm Server is down..", false);
			}
			
		}
		
		return new ReturnModel(Statics.REST_FAILED,"Unknown User / user did not validate correctly.", false);
	}
	
	
	private static boolean updateFormDataInDatabase(String formIdGuid, FormData[] formData)
	{
		//code to update teh database.
		return true;
	}
	
	private static boolean rollbackData(String formIdGuid, FormData[] formData)
	{
		//code to replace the new code.
		return true;
	}
	
	private static boolean rollbackData(String formIdGuid, ArrayList<FormData> formData)
	{
		FormData[] formDataAsArray = new FormData[formData.size()];
		for (int count = 0; count< formData.size(); count++)
		{
			formDataAsArray[count] = formData.get(count);
		}
		
		return rollbackData(formIdGuid, formDataAsArray);

	}
	
}
