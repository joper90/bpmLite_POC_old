package workers;

import model.ReturnModel;
import rest.client.RestPostGlobalDataRequest;

import com.bpmlite.api.ProcessArtifactDocument;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.Fields;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.ExtendedData;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.Participants;

import config.Statics;
import database.DAO.BpmLiteDAO;
import database.model.FieldDataModel;
import database.model.GlobalMappingsModel;
import database.model.ProcessModel;
import database.model.StepDataModel;

public class DeployProcessWorker {

	public static ReturnModel deployProcess(String user, String password, ProcessArtifactDocument processToDeploy)
	{
		ReturnModel ret = new ReturnModel();
		//Convert first to a doc element.
		
		//Validatae the user.
		String rootUser = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(Statics.SERVER_INFO_ADMIN_KEY);
		String rootPass = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(Statics.SERVER_INFO_ADMIN_PASS);
		
		if (rootUser != null && rootPass != null)
		{
			if (rootUser.equals(user) && rootPass.equals(password))
			{
				//Validate sucess.. now we can try and actually deploy the process.
				ret = internalDeploy(processToDeploy);
			}
			else
			{
				//Failed to validate.. error
				ret.setValues("failed", "Cannot validated user/pass", false);
			}
		}
		else
		{
			//Failed to even validate the details from the database
			ret.setValues("failed", "Cannot get root validation details", false);
		}
		
		return ret;
	}


	private static ReturnModel internalDeploy(ProcessArtifactDocument processToDeploy)
	{
		ReturnModel ret = new ReturnModel();
		
		//First deploy the field Ids, to get the list of the actual fild id's
		//This deploys the fields and the globals.
		String fieldsDeployed = deployFields(processToDeploy.getProcessArtifact().getFieldsArray());
		
		//Now inject the  process information, so we can get a processid
		int deployProcessInformation = deployProcessInformation(processToDeploy.getProcessArtifact(), fieldsDeployed);
		
		if (deployProcessInformation != Statics.FAILED)
		{
			//Deploy the steps now.
			boolean stepDeployed = deploySteps(processToDeploy.getProcessArtifact().getProcessData().getStepsArray(), 
												deployProcessInformation);
			
			if (stepDeployed)
			{
				ret.setResult("Process deployed");
				ret.setWorked(true);
				ret.setReason("");
			}

		}
		else
		{
			ret.setValues("failed", "Cannot deploy processinformation correctly!!", false);
		}

		return ret;
	}
	
	private static String deployFields(Fields[] fieldArray)
	{
		String fieldIdList = "";
		for(Fields f : fieldArray)
		{
			
			if (f.getGlobal() == true)
			{
				//So with a global we need to send it too the guard to get a unique id back.
				Integer globalRef = RestPostGlobalDataRequest.doPost(f);
				// Now save the reference away in the look up table.
				GlobalMappingsModel gMapping = new GlobalMappingsModel();
				gMapping.setGlobalDeployedId(f.getFieldId());
				gMapping.setGlobalGuardId(globalRef);
				BpmLiteDAO.instance.getGlobalMappingsDAO().insertData(gMapping);
								
			}
			else
			{			
				BpmLiteDAO.instance.getFieldDataDAO().insertData(
										new FieldDataModel(
													f.getName(), 
													f.getType().toString(), 
													f.getInitalData(), 
													f.getFieldId())
										);
				fieldIdList = fieldIdList + f.getFieldId() + ",";
			}
		}
		
		if (fieldIdList.length() > 0) 
		{
			fieldIdList = fieldIdList.substring(0, fieldIdList.length() -1);
		}else
		{
			fieldIdList = null;
		}
	
		return fieldIdList;
	}
	
	
	private static Integer deployProcessInformation(ProcessArtifact pInfo, String fieldsDeployed)
	{
		//name,version,guid,startStep,fieldIds
		ProcessModel processModel = new ProcessModel(pInfo.getProcessData().getProcessName(),
													 pInfo.getProcessData().getVersion(),
													 pInfo.getUniqueGuid(),
													 Statics.START_STEP_INITAL_VALUE, //StartStep now known yet!
													 fieldsDeployed); //common delimited list
													 
		boolean worked = BpmLiteDAO.instance.getProcessDAO().insertData(processModel);
		
		if (worked)
		{
			//Worked so lets get the id.
			return BpmLiteDAO.instance.getProcessDAO().getIdByGuid(pInfo.getUniqueGuid());
		}
		return Statics.FAILED;
	}
	
	private static boolean deploySteps(Steps[] stepArray, int processId)
	{
		for (Steps s : stepArray)
		{
			//Get pairs of data.
			String extendedData = "";
			ExtendedData[] extendedDataArray = s.getExtendedDataArray();
			if (extendedDataArray.length != 0)
			{
				for (ExtendedData e : extendedDataArray)
				{
					extendedData = extendedData + e.getName() + ":" + e.getValue() + ",";
				}
				//Clean up.
				extendedData = extendedData.substring(0,extendedData.length() -1 );
			}
			
			String partipantsList = "";
			Participants[] participantsArray = s.getParticipantsArray();
			if (participantsArray.length != 0)
			{
				for (Participants p : participantsArray)
				{
					partipantsList = partipantsList + p.getUserGuid() + ":";
				}
				partipantsList = partipantsList.substring(0,partipantsList.length() -1 );
			}
			
			/*
			Public StepDataModel(String stepId, String processId, String stepType,
			String stepDetails, String userList, String nextId,
			String previousId, String fieldData, String globalData) {
			 */
			
			BpmLiteDAO.instance.getStepDataDAO().insertData(
						new StepDataModel(String.valueOf(s.getStepId()),
										String.valueOf(processId),
										s.getStepType().toString(),
										extendedData,
										partipantsList,
										UtilWorker.arrayToString(s.getNextStepIdArray()),
										UtilWorker.arrayToString(s.getPreviousStepIdArray()),
										UtilWorker.arrayToString(s.getFieldDataArray()),
										UtilWorker.arrayToString(s.getGlobalDataArray())
										)	
										);
						
		} //End of steps
		
		return true;
	}

}
