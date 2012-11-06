package workers;

import com.bpmlite.api.SetUserDetailsRequestDocument;
import com.bpmlite.api.SetUserDetailsRequestDocument.SetUserDetailsRequest;

public class SetUserDetailsWorker {

	public static boolean validateNewUserData(SetUserDetailsRequestDocument setUserDetailsRequestDoc)
	{
		//TODO set the data for the userinfo in the database.
		SetUserDetailsRequest userDetails = setUserDetailsRequestDoc.getSetUserDetailsRequest();
		return true;
	}
}
