package workers;

import java.util.ArrayList;


import jmsConnector.QueueJMSMessageSender;

import com.bpmlite.api.ActionModeType;
import com.bpmlite.api.CompleteWorkItemRequestDocument.CompleteWorkItemRequest;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;
import com.google.gson.Gson;

import config.Statics;
import engineConnector.EngineRestConnector;
import guard.models.CompleteFormData;
import guard.models.FormData;
import guard.models.ReturnModel;

public class CompleteWorkItemWorker {

	public static ReturnModel completeWorkItem( String userId,  String requestId, String action, CompleteFormData formData)
	{		
		
		//Fist check the user key.
		if (WorkItemRequestWorker.isValidFormGuid(requestId, userId))
		{
			//Entry exists and matches.
			WorkItemKeyDetails wItemDetails = WorkItemRequestWorker.getWorkItemDetailsFromGuid(requestId, userId);
			
			// Take a copy of the data from the database for a transaction rollback.					
			CompleteFormData currentData = WorkItemRequestWorker.getAllData(requestId);

			//Now check the master server is currently Alive..
			
			if(EngineRestConnector.isAlive())
			{
				//Now commit the data..
				FormData[] fDataArray = new FormData[formData.getFormData().size()];
				formData.getFormData().toArray(fDataArray);
				if (updateFormDataInDatabase(requestId, fDataArray))
				{
					
					CompleteWorkItemRequest comp = CompleteWorkItemRequest.Factory.newInstance();
					comp.setProcessId(wItemDetails.getProcessId());
					comp.setStepId(wItemDetails.getStepId());
			
					//TODO: change this crappy code.. !
					if (action.equalsIgnoreCase("SUBMIT"))
					{
						comp.setAction(ActionModeType.COMPLETE);
					}
					else if (action.equalsIgnoreCase("CLOSE"))
					{
						comp.setAction(ActionModeType.CLOSE);
					}
					
					QueueJMSMessageSender jmsSender = new QueueJMSMessageSender();
					
					if(jmsSender.sendMessageCheck(Statics.JMS_TOPIC_PUSH, comp.xmlText()))
					{
						return new ReturnModel(Statics.EMS_PUSH_WORKED,"Step completed sucessfully", false);
					}
					else
					{				
						if (rollbackData(requestId  , currentData.getFormData()))
						{
							return new ReturnModel(Statics.EMS_PUSH_FAILED,"Step not completed.. rolling back", false);
						}
						else
						{
							return new ReturnModel(Statics.EMS_PUSH_FAILED,"Step not completed.. rolling back FAILED", false);
						}
					}
					
				
				}else
				{
					return new ReturnModel(Statics.EMS_PUSH_FAILED,"Update form data failed.", false);
				}
			}
			else
			{
				return new ReturnModel(Statics.EMS_PUSH_FAILED,"Bpm Server is down..", false);
			}
			
		}
		
		return new ReturnModel(Statics.EMS_PUSH_FAILED,"Unknown User / user did not validate correctly.", false);
	}
	
	
	private static boolean updateFormDataInDatabase(String formIdGuid, FormData[] formData)
	{
		//TODO: code to update teh database.
		
		return true;
	}
	
	private static boolean rollbackData(String formIdGuid, FormData[] formData)
	{
		//TODO: code to replace the new code.
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
