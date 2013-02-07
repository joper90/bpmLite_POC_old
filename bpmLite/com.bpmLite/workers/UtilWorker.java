package workers;

import java.util.Arrays;
import java.util.UUID;

import com.bpmlite.api.FieldModeType;

import config.StaticsCommon;
import database.DAO.BpmLiteDAO;
import database.model.ServerInfoModel;

public class UtilWorker {

	
	public static String arrayToString(Object[] array)
	{
		String string = Arrays.toString(array);
		string = string.substring(1,string.length() - 1); //Strip off [] 
		return string;
	}
	
	public static String getAndThenIncrementCaseId() //thread lock for uniqueness??
	{
		ServerInfoModel sInfo = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(StaticsCommon.CASE_ID);
		String currentValue = sInfo.getValue();

		
		//Split down
		int currentCaseIdCount = new Integer(currentValue.substring(currentValue.indexOf(":")+1));

		String newValue = "bpm:" + String.format("%06d", ++currentCaseIdCount);
		
		sInfo.setValue(newValue);
		if (BpmLiteDAO.instance.getServerInfoDAO().updateModel(sInfo)) return currentValue;
				
		return StaticsCommon.FAILURE;
		
	}
	
	public static String getRandomGuid()
	{
		return UUID.randomUUID().toString();
	}

	
	public static FieldModeType.Enum getFieldModeFromString(String enumAsString)
	{
		return FieldModeType.Enum.forString(enumAsString);
	}
	
}
