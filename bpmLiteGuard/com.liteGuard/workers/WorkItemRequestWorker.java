package workers;

import guard.models.CompleteFormData;
import guard.models.FormData;
import guard.models.FormData.FIELD_MODE;
import guard.models.FormData.FIELD_TYPE;

import java.util.ArrayList;

import com.bpmlite.api.WorkItemKeyDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails.KeyFieldDetails;

import config.Statics;
import config.Statics.GUID_KEY_MODE;
import database.FieldDataModel;
import database.GlobalData;
import database.KeyStoreModel;
import database.DAO.BpmGuardDAO;

public class WorkItemRequestWorker {

	
	//This is from teh server, before it sends out a return to the client for where the 
	// data is stored, it creates a new form data key
	
	public static boolean injectNewKey(WorkItemKeyDetailsDocument wItemKeyDetails)
	{
		//First check the root key
		if (!ValidationWorker.isValid(Statics.ADMIN,wItemKeyDetails.getWorkItemKeyDetails().getRootKey()))
		{
			System.out.println("Incorrect admin key supplied... cannot inject new key");
			return false;
		}
		
		//Add the form Data information to the Database.
		KeyStoreModel keyStore = new KeyStoreModel();
		keyStore.setKeyState(GUID_KEY_MODE.INJECTED.toString());
		keyStore.setCaseId(wItemKeyDetails.getWorkItemKeyDetails().getCaseId());
		keyStore.setProcessId(wItemKeyDetails.getWorkItemKeyDetails().getProcessId());
		keyStore.setUserGuid(wItemKeyDetails.getWorkItemKeyDetails().getUniqueFormGuid()); // the u
		keyStore.setUserId(wItemKeyDetails.getWorkItemKeyDetails().getUserKey()); //The user to setup the infor for
		keyStore.setStepId(wItemKeyDetails.getWorkItemKeyDetails().getStepId());
		if (wItemKeyDetails.getWorkItemKeyDetails().isSetDisplayOnly())
		{
			keyStore.setDisplayOnly(wItemKeyDetails.getWorkItemKeyDetails().getDisplayOnly());
		}
		if (wItemKeyDetails.getWorkItemKeyDetails().isSetOrderList())
		{
			keyStore.setOrderList(wItemKeyDetails.getWorkItemKeyDetails().getOrderList());
		}
		
		//Now deal with fields.
		//Get field list
		KeyFieldDetails[] fieldDetailsArray = wItemKeyDetails.getWorkItemKeyDetails().getKeyFieldDetailsArray();
		StringBuffer fieldIdList = new StringBuffer();
		for (KeyFieldDetails fDetails : fieldDetailsArray)
		{
			fieldIdList.append(fDetails.getFieldId());
			fieldIdList.append(",");
		}
		keyStore.setFieldIds(fieldIdList.toString()); // , Still has the last , added on.. 
			
		
		return BpmGuardDAO.instance.getKeyStoreDAO().addKeyStoreRecord(keyStore);
		
	}
	
	public static boolean isValidFormGuid(String requestId, String userId)
	{
		//Check if the guid exists in the required table (i.e its been added from teh bpmlite server)
		KeyStoreModel model = BpmGuardDAO.instance.getKeyStoreDAO().findDataByGuidAndUserId(requestId, userId);
		if (model != null) return true;
		return false;
	}
	
	public static WorkItemKeyDetails getWorkItemDetailsFromGuid(String formIdGuid, String userKey)
	{
		WorkItemKeyDetails wItemDetails = WorkItemKeyDetails.Factory.newInstance();
		//Connect to db and get the details
		KeyStoreModel model = BpmGuardDAO.instance.getKeyStoreDAO().findDataByGuidAndUserId(formIdGuid, userKey);
		
		
		wItemDetails.setProcessId(model.getProcessId());
		wItemDetails.setStepId(model.getStepId());
		wItemDetails.setUniqueFormGuid(formIdGuid);
		wItemDetails.setStepId(model.getStepId());
		
		//Now get the field information.
		
		
		FieldDataModel[] fields = BpmGuardDAO.instance.getFieldDataDAO().getAllFieldsByStringOfIds(model);
		GlobalData[] global = BpmGuardDAO.instance.getGlobalDataDAO().getAllGlobalFieldsByStringOfIds(model);
		KeyFieldDetails[] fDetailsArray = new KeyFieldDetails[fields.length + global.length];
		int pCount = 0;
		for (FieldDataModel fd : fields)
		{
			KeyFieldDetails fDetail = KeyFieldDetails.Factory.newInstance();
			fDetail.setFieldId(fd.getFieldId());
			fDetail.setIsGlobal(false);
			fDetailsArray[pCount++] = fDetail;
		}
		for (GlobalData gd : global)
		{
			KeyFieldDetails fDetail = KeyFieldDetails.Factory.newInstance();
			fDetail.setFieldId(gd.getFieldId());
			fDetail.setIsGlobal(true);
			fDetailsArray[pCount++] = fDetail;
		}
		
		wItemDetails.setKeyFieldDetailsArray(fDetailsArray);
		return wItemDetails;
	}
	
	public static boolean removeFormGuidKey(String formIdGuid)
	{
		return BpmGuardDAO.instance.getKeyStoreDAO().deleteRecordByGuid(formIdGuid);
	}
		
	public static CompleteFormData getAllData(String formIdGuid)
	{
		//Get data from the database and populate the information.
		CompleteFormData completFormData = new CompleteFormData();
		//Get the keyStore info from the database.
		KeyStoreModel keyModel = BpmGuardDAO.instance.getKeyStoreDAO().findDataByGuid(formIdGuid);				
		//add not null here.
	
			completFormData.setOrderList(keyModel.getOrderList());
			ArrayList<FormData> formData = new ArrayList<FormData>();
			
			//Get field Data and Global Data now.
			FieldDataModel[] fields = BpmGuardDAO.instance.getFieldDataDAO().getAllFieldsByStringOfIds(keyModel);
			GlobalData[] globals = BpmGuardDAO.instance.getGlobalDataDAO().getAllGlobalFieldsByStringOfIds(keyModel);
			
			for (FieldDataModel f : fields)
			{
				FormData fAdd = new FormData();
				fAdd.setFieldMode(getFieldMode(f.getFieldId(), keyModel.getDisplayOnly()));
				fAdd.setFieldName(f.getName());
				fAdd.setFieldData(f.getData());
				
				FIELD_TYPE eType = FIELD_TYPE.STRING;
				for (FIELD_TYPE fType : FIELD_TYPE.values())
				{
					if (fType.toString().equalsIgnoreCase(f.getType()))
					{
						eType = fType;
					}
				}
				
				fAdd.setFieldType(eType);
				fAdd.setGlobal(false);
				fAdd.setFieldId(f.getFieldId());
				formData.add(fAdd);
			}
			
			for (GlobalData d : globals)
			{
				FormData gAdd = new FormData();
				gAdd.setFieldMode(getFieldMode(d.getFieldId(), keyModel.getDisplayOnly()));
				gAdd.setFieldName(d.getName());
				gAdd.setFieldData(d.getData());
				
				FIELD_TYPE eType = FIELD_TYPE.STRING;
				for (FIELD_TYPE fType : FIELD_TYPE.values())
				{
					if (fType.toString().equalsIgnoreCase(d.getType()))
					{
						eType = fType;
					}
				}
				
				gAdd.setFieldType(eType);
				gAdd.setGlobal(true);
				gAdd.setFieldId(d.getFieldId());
				formData.add(gAdd);
			}
			
		completFormData.setFormData(formData);
		
		return completFormData;
	}
	
	private static FormData.FIELD_MODE getFieldMode(int idCheck, String listOfDisplayOnly)
	{
		String[] displayIds = listOfDisplayOnly.split(",");

		for (String s : displayIds)
		{
			int checkMe = new Integer(s);
			if (checkMe == idCheck)
			{
				return FIELD_MODE.DISPLAY;
			}
		}
		
		return FIELD_MODE.EDIT;
	}
}
