package workers;

import guard.models.CompleteFormData;
import guard.models.FormData;
import guard.models.FormData.FIELD_TYPE;
import guard.models.ReturnModel;

import java.util.ArrayList;

import jmsConnector.QueueJMSMessageSender;

import com.bpmlite.api.ActionModeType;
import com.bpmlite.api.CompleteWorkItemRequestDocument.CompleteWorkItemRequest;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;

import config.Statics;
import config.Statics.GUID_KEY_MODE;
import database.FieldDataModel;
import database.GlobalData;
import database.KeyStoreModel;
import database.DAO.BpmGuardDAO;
import engineConnector.EngineRestConnector;

public class CompleteWorkItemWorker {

	public static ReturnModel completeWorkItem( String userId,  String requestId, String action, CompleteFormData formData)
	{		
		
		//Fist check the user key.
		if (WorkItemRequestWorker.isValidFormGuid(requestId, userId))
		{
			
			//check the status (needs to be taken, but not actioned).. 
			GUID_KEY_MODE keyState = BpmGuardDAO.instance.getKeyStoreDAO().getKeyState(requestId);
			if (keyState != GUID_KEY_MODE.TAKEN)
			{
				return new ReturnModel(Statics.FAILED,"Incorrect key state", false);
			}
			
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
						return new ReturnModel(Statics.SUCCESS,"Step completed sucessfully", false);
					}
					else
					{				
						if (rollbackData(requestId  , currentData.getFormData()))
						{
							return new ReturnModel(Statics.FAILED,"Step not completed.. rolling back", false);
						}
						else
						{
							return new ReturnModel(Statics.FAILED,"Step not completed.. rolling back FAILED", false);
						}
					}
					
				
				}else
				{
					return new ReturnModel(Statics.FAILED,"Update form data failed.", false);
				}
			}
			else
			{
				return new ReturnModel(Statics.FAILED,"Bpm Server is down..", false);
			}
			
		}
		
		return new ReturnModel(Statics.FAILED,"Unknown User / user did not validate correctly.", false);
	}
	
	
	private static boolean updateFormDataInDatabase(String requestId, FormData[] formData)
	{		
		KeyStoreModel keyStore = BpmGuardDAO.instance.getKeyStoreDAO().findDataByGuid(requestId);
		
		String[] fIds = keyStore.getFieldIds().split(",");
		ArrayList<Integer> fIdsInt = new ArrayList<Integer>();
		for (String f : fIds)
		{
			fIdsInt.add(new Integer(f));
		}
		
		String[] displayOnly = keyStore.getDisplayOnly().split(",");
		ArrayList<Integer> dInt = new ArrayList<Integer>();
		for (String d : displayOnly)
		{
			dInt.add(new Integer(d));
		}
		
		
		for (FormData f : formData)
		{
			if (fIdsInt.contains(f.getFieldId()))
			{
				//Check if readOnly
				if (dInt.contains(f.getFieldId()))
				{
					System.out.println("[COMPLETE] --> displayfield only");
				}
				else
				{
					//we contain the object
					if (f.isGlobal())
					{
						//Get a copy of the current data.
						GlobalData globalFieldById = BpmGuardDAO.instance.getGlobalDataDAO().getGlobalFieldById(f.getFieldId());
						
						//Check for type is correct.
						if (isTypeCorrect(globalFieldById.getData(), globalFieldById.getType(), globalFieldById.getFieldId()))
						{
							globalFieldById.setData(f.getFieldData());
							BpmGuardDAO.instance.getGlobalDataDAO().updateField(globalFieldById);
							System.out.println("[COMPLETE] --> GLOBAL UPDATED :" +f.getFieldId());
						}
						else
						{
							System.out.println("[COMPLETE] --> GLOBAL IGNORED.. incorrect field type??? :" +f.getFieldId());
						}
							
					}
					else
					{
						//Get current fieldId
						FieldDataModel fieldById = BpmGuardDAO.instance.getFieldDataDAO().getFieldById(keyStore.getProcessId(),
																										keyStore.getCaseId(), 
																										f.getFieldId());
						
						//Check the type is correct
						
						if (isTypeCorrect(fieldById.getData(), fieldById.getType(), fieldById.getFieldId()))
						{
							fieldById.setData(f.getFieldData());
							BpmGuardDAO.instance.getFieldDataDAO().updateField(fieldById);
							System.out.println("[COMPLETE] --> FIELD UPDATED :" +f.getFieldId());
						}
						else
						{
							System.out.println("[COMPLETE] --> FIELD IGNORED.. incorrect field type??? :" +f.getFieldId());
						}
					}
				}
			}else
			{
				System.out.println("[COMPLETE] --> invalid field Id passed in..");
			}
		}
		return true;
	}
	
	private static boolean rollbackData(String requestId, FormData[] formData)
	{
		//Actually call the normal update code with older values...
		System.out.println("[COMPLETE] -------------------> ROLLING BACK TRANSACTIONS <-----------------");
		return updateFormDataInDatabase(requestId, formData);
		
	}
	
	private static boolean rollbackData(String requestId, ArrayList<FormData> formData)
	{
		FormData[] fDataArray = new FormData[formData.size()];
		formData.toArray(fDataArray);
		
		return rollbackData(requestId, fDataArray);
	}
	
	private static boolean isTypeCorrect(String fieldValue, String type, Integer fieldId)
	{
		//Convert type to the enum.
		FIELD_TYPE fType;
		for (FIELD_TYPE f : FIELD_TYPE.values())
		{
			if (f.toString().equalsIgnoreCase(type))
			{
				fType = f;
				
				switch (fType)
				{
				case STRING:
							//Bound to be correct.. as we get passed in a string.
							break;
				case INTEGER:
							//First check for a . to check its not a decimal
							if (fieldValue.contains("."))
							{
								//prob a decimal.. but falut as rounding will take place.
								return false;
							}
							else
							{
								try
								{
									Integer.parseInt(fieldValue);
								}catch (Exception e)
								{
									return false;
								}
							}
							break;

				case DECIMAL:
							//Try and convert to a float.
							try
							{
								Float.parseFloat(fieldValue);
							}catch (Exception e)
							{
								return false;
							}
							break;
				case BOOLEAN:
							//check for a true/false;
							if (fieldValue.equalsIgnoreCase("true") || fieldValue.equalsIgnoreCase("false"))
							{
								//ok..but change this.. or adding logging
							}else
							{
								if (fieldValue.equalsIgnoreCase("0") || fieldValue.equalsIgnoreCase("1"))
								{
									//also valid
								}else
								{
									return false;
								}
							}
							break;
				}
				
			}
		}
		System.out.println("[COMPLETE] --> INCORRECT Type for :" + fieldId + " " +fieldValue+" " +type);
		return false;
	}
			
}
