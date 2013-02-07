package connector.tibbrConnector;

import database.model.UserModel;

public class TibbrConnector {

	public static TibbrConnector instance = new TibbrConnector();
	
	private TibbrConnector(){};
	
	public static boolean postMessage(String message)
	{
		//TODO: implement postMessage()
		return true;
	}
	
	public static boolean postWorkItemMessage(String uniqueGuid, UserModel userModel)
	{
		System.out.println("----------> Sending message to the client. <-------------");
		System.out.println("Message : guid : " + uniqueGuid + " key:" +userModel.getUniqueKey() + " name:" + userModel.getName());
		return true;
	}
}
