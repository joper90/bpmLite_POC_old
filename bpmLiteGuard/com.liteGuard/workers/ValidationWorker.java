package workers;

import database.ServerInfoModel;
import database.DAO.BpmGuardDAO;



public class ValidationWorker {

	public enum ACTION_TYPES  {COMPLETE, CANCEL, CLOSE};
	
	public static boolean isValid(String name, String key)
	{
		//To check in the database
		ServerInfoModel model = BpmGuardDAO.instance.getServerInfoDAO().findDataByValue(name);
		if (model !=  null)
		{
			if (model.getData().equalsIgnoreCase(key)) return true;
		}
		return false;
	}
	
	public static boolean isAdmin(String name, String key)
	{
		return (name.equals("root") ? true : false);
	}
	
	
	public static boolean validateActionType(String actionType)
	{
		for (ACTION_TYPES action : ACTION_TYPES.values())
		{
			if (action.name().equalsIgnoreCase(actionType))
			{
				return true;
			}
		}
		return false;
	}
	
	public ACTION_TYPES getActionTypeFromString(String actionType)
	{
		if (validateActionType(actionType))
		{
			return ACTION_TYPES.valueOf(actionType.toUpperCase());
		}
		return null;
	}
}
