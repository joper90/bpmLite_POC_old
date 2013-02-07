package workers;

import lite.models.ReturnModel;
import rest.client.RestPostGlobalDataRequest;

import com.bpmlite.api.ProcessArtifactDocument;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.Fields;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.ExtendedData;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.Participants;

import config.StaticsCommon;
import database.DAO.BpmLiteDAO;
import database.model.FieldDataModel;
import database.model.GlobalMappingsModel;
import database.model.ProcessModel;
import database.model.StepDataModel;

public class DeployProcessWorker {
	
	public DeployProcessWorker(){};
	
	private String deployedFieldIds = "";
	private String deployedGlobalFieldIds  = "";

	public ReturnModel deployProcess(String user, String password, ProcessArtifactDocument processToDeploy)
	{
		ReturnModel ret = new ReturnModel();
		//Convert first to a doc element.
		
		//Validatae the user.
		String rootUser = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(StaticsCommon.SERVER_INFO_ADMIN_KEY).getValue();
		String rootPass = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(StaticsCommon.SERVER_INFO_ADMIN_PASS).getValue();
		
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


	private ReturnModel internalDeploy(ProcessArtifactDocument processToDeploy)
	{
		ReturnModel ret = new ReturnModel();
		
		//First deploy the field Ids, to get the list of the actual fild id's
		//This deploys the fields and the globals.
		deployFields(processToDeploy.getProcessArtifact().getFieldsArray());
		
		//Now inject the  process information, so we can get a processid
		int deployProcessInformation = deployProcessInformation(processToDeploy.getProcessArtifact());
		
		if (deployProcessInformation != StaticsCommon.FAILED)
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
	
	private boolean deployFields(Fields[] fieldArray)
	{

		for(Fields f : fieldArray)
		{
			
			if (f.getGlobal() == true)
			{
				//So with a global we need to send it (REST) too the guard to get a unique id back.
				Integer globalRef = RestPostGlobalDataRequest.doPost(f);
				// Now save the reference away in the look up table.
				GlobalMappingsModel gMapping = new GlobalMappingsModel();
				gMapping.setGlobalDeployedId(f.getFieldId());
				gMapping.setGlobalGuardId(globalRef);
				BpmLiteDAO.instance.getGlobalMappingsDAO().insertData(gMapping);
				deployedGlobalFieldIds = deployedGlobalFieldIds + globalRef + ",";				
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
				deployedFieldIds = deployedFieldIds + f.getFieldId() + ",";
			}
		}
		
		if (deployedFieldIds.length() > 0) 
		{
			deployedFieldIds = deployedFieldIds.substring(0, deployedFieldIds.length() -1);
		}else
		{
			deployedFieldIds = null;
		}
		
		if (deployedGlobalFieldIds.length() > 0) 
		{
			deployedGlobalFieldIds = deployedGlobalFieldIds.substring(0, deployedGlobalFieldIds.length() -1);
		}else
		{
			deployedGlobalFieldIds = null;
		}
	
		return true;
	}
	
	
	private Integer deployProcessInformation(ProcessArtifact pInfo)
	{
		//name,version,guid,startStep,fieldIds
		ProcessModel processModel = new ProcessModel(pInfo.getProcessData().getProcessName(),
													 pInfo.getProcessData().getVersion(),
													 pInfo.getUniqueGuid(),
													 StaticsCommon.START_STEP_INITAL_VALUE, //StartStep now known yet!
													 deployedFieldIds,
													 deployedGlobalFieldIds); //common delimited list - Normals and globals.
													 
		boolean worked = BpmLiteDAO.instance.getProcessDAO().insertData(processModel);
		
		if (worked)
		{
			//Worked so lets get the id.
			return BpmLiteDAO.instance.getProcessDAO().getIdByGuid(pInfo.getUniqueGuid());
		}
		return StaticsCommon.FAILED;
	}
	
	private boolean deploySteps(Steps[] stepArray, int processId)
	{
		for (Steps s : stepArray)
		{
			//Get pairs of data.
			String extendedData = "";
			
			ExtendedData[] extendedDataArray = null;
			
			if (s.getExtendedDataArray().length > 0)
			{
				extendedDataArray = s.getExtendedDataArray();
				for (ExtendedData e : extendedDataArray)
				{
					extendedData = extendedData + e.getName() + ":" + e.getValue() + ",";
				}
				//Clean up.
				extendedData = extendedData.substring(0,extendedData.length() -1 );
			}
			
			String partipantsList = "";
			Participants[] participantsArray = null;
			
			if (s.getParticipantsArray().length > 0)
			{
				participantsArray = s.getParticipantsArray();
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
										UtilWorker.arrayToString(s.getGlobalDataArray()),
										UtilWorker.arrayToString(s.getDisplayOnlyArray())
										)	
										);
						
		} //End of steps
		
		return true;
	}

}
