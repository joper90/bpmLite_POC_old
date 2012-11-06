package workers;

import java.util.ArrayList;

import model.CompleteFormData;
import model.FormData;
import model.FormData.FIELD_MODE;
import model.FormData.FIELD_TYPE;

import com.bpmlite.api.WorkItemKeyDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;

import config.Statics;

public class WorkItemRequestWorker {

	
	//This is from teh server, before it sends out a return to the client for where the 
	// data is stored, it creates a new form data key
	
	public static boolean injectNewKey(WorkItemKeyDetailsDocument wItemKeyDetails)
	{
		//TODO : add inject key
		//Add the form Data information to the Database.
		return true;
	}
	
	public static boolean isValidFormGuid(String formIdGuid, String userKey)
	{
		//TODO : add check valid jkey in db
		//Check if the guid exists in the required table (i.e its been added from teh bpmlite server)
		return true;
	}
	
	public static WorkItemKeyDetails getWorkItemDetailsFromGuid(String formIdGuid, String userKey)
	{
		WorkItemKeyDetails wItemDetails = WorkItemKeyDetails.Factory.newInstance();
		//TODO: Added db connection information
		//Connect to db and get the details
		return wItemDetails;
	}
	
	public static boolean removeFormGuidKey(String formIdGuid)
	{
		//TODO: remove key from the database.
		//remove the key from the database
		return true;
	}
		
	public static CompleteFormData getAllData(String formIdGuid)
	{
		//TODO: get all the data from teh databae..
		//Get data from the database and populate the information.
		CompleteFormData c = new CompleteFormData();
		
		if (Statics.TEST_MODE)
		{
			c.setOrderList("DummyOrderList");
			c.setValid(true);
			
			ArrayList<FormData> formData = new ArrayList<FormData>();
			
			for (int a = 1 ; a < 4 ; a++)
			{
				FormData f = new FormData();
				f.setFieldMode(FIELD_MODE.INOUT);
				f.setFieldName("DummyTest:" + a);
				f.setFieldType(FIELD_TYPE.STRING);
				f.setFieldData("This is a test field string " + a);
				
				formData.add(f);
			}
			
			
			c.setFormData(formData);
		}
		
		return c;
	}
}
