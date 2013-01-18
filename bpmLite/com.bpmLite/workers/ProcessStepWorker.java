package workers;

import config.Statics.STEP_TYPE;
import database.DAO.BpmLiteDAO;
import database.model.ProcessInstanceModel;
import database.model.StepDataModel;

public class ProcessStepWorker {

	
	public static boolean processNextStep(String caseId)
	{
		//First see if we are on the inital step.
		ProcessInstanceModel pModel = BpmLiteDAO.instance.getProcessInstanceDAO().getByCaseId(caseId);
		if (pModel != null)
		{
			//Get the current (inital?) .. push inital details out???  - done in start case.
			StepDataModel stepDataModel = BpmLiteDAO.instance.getStepDataDAO().getStepByStepId(pModel.getCurrentStepId());
			
			//What kind of task is it? USER, AMXBPM, DB, EMAIL, POJO, TIBBR
			TaskWorker taskWorker = new TaskWorker();
			boolean taskWorked = false;
			
			STEP_TYPE sType = STEP_TYPE.valueOf(stepDataModel.getStepType());
			switch (sType)
			{
			case USER : taskWorked = taskWorker.processUserTask(pModel, stepDataModel);
				break;
			case AMXBPM :
				break;
			case DB :
				break;
			case EMAIL :
				break;
			case POJO :
				break;
			case TIBBR :
				break;
				
			}
			
			if (taskWorked == false)
			{
				//Step processing failed.
				return false;
			}
			
		}else
		{
			//Cannot get the specfic case Id.. not tooooo good really.
			return false;
		}
		
		return true;
	}
	
}
