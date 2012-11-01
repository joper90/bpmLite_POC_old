package workers;

import java.util.ArrayList;

import model.CompleteFormData;
import model.FormData;
import model.FormData.FIELD_MODE;
import model.FormData.FIELD_TYPE;
import model.WorkItemDetails;
import config.Statics;

public class WorkItemRequestWorker {

	
	//This is from teh server, before it sends out a return to the client for where the 
	// data is stored, it creates a new form data key
	
	public static boolean injectNewKey(String rootKey, WorkItemDetails formData)
	{
		//Add the form Data information to the Database.
		return true;
	}
	
	public static boolean isValidFormGuid(String formIdGuid, String userKey)
	{
		//Check if the guid exists in the required table (i.e its been added from teh bpmlite server)
		return true;
	}
	
	public static WorkItemDetails getWorkItemDetailsFromGuid(String formIdGuid, String userKey)
	{
		return new WorkItemDetails();
	}
	
	public static boolean removeFormGuidKey(String formIdGuid)
	{
		//remove the key from the database
		return true;
	}
		
	public static CompleteFormData getAllData(String formIdGuid)
	{
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
