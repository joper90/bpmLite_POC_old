package workers;

import com.bpmlite.api.SetUserDetailsRequestDocument;
import com.bpmlite.api.SetUserDetailsRequestDocument.SetUserDetailsRequest;
import com.bpmlite.api.SetUserDetailsRequestDocument.SetUserDetailsRequest.UserDetails;

import database.KeyStoreModel;

public class SetUserDetailsWorker {

	public static boolean validateNewUserData(SetUserDetailsRequestDocument setUserDetailsRequestDoc)
	{
		//TODO set the data for the userinfo in the database.
		SetUserDetailsRequest userDetails = setUserDetailsRequestDoc.getSetUserDetailsRequest();
		
		UserDetails[] userDetailsArray = userDetails.getUserDetailsArray();
		for (UserDetails u : userDetailsArray)
		{
			//Check if the user already exists.
			KeyStoreModel userKeyStore = new KeyStoreModel();
			
			
		}
		
		//Check if the user already exists..
/*		KeyStoreModel userKeyStore = new KeyStoreModel();
		userKeyStore.setKey(setUserDetailsRequestDoc.getSetUserDetailsRequest().get)*/
		
		return true;
	}
}
