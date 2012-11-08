package workers;

import jmsConnector.QueueJMSMessageSender;

import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails.FieldDetails;

import config.Statics;

import database.FieldDataModel;
import database.GlobalData;
import database.DAO.BpmGuardDAO;

public class StartCaseRequestWorker {

	public static boolean setupInitialDetails(StartCaseDetailsDocument startCaseDetailsDoc)
	{
		//TODO: start a case, inject the data, based on the proces id into the field tables.
		
		StartCaseDetails startCaseDetails = startCaseDetailsDoc.getStartCaseDetails();
		int processId = startCaseDetails.getProcessId();
		int caseId = startCaseDetails.getCaseId();
		FieldDetails[] fieldDetailsArray = startCaseDetails.getFieldDetailsArray();
		String callbackGuidKey = startCaseDetails.getCallbackGuidKey();
		
		for (FieldDetails f : fieldDetailsArray)
		{
			if (f.getIsGlobal())
			{
				GlobalData gModel = new GlobalData();
				gModel.setFieldId(f.getFieldId());
				gModel.setName(f.getName());
				gModel.setType(f.getFieldType().toString());
				if (f.isSetInitalValue())
				{
					gModel.setData(f.getInitalValue());
				}
				
				//Now commit to the database
				BpmGuardDAO.instance.getGlobalDataDAO().insertFieldData(gModel);
				
			}
			else
			{
				FieldDataModel fModel = new FieldDataModel();
				fModel.setProcessId(processId);
				fModel.setCaseId(caseId);
				fModel.setName(f.getName());
				fModel.setFieldId(f.getFieldId());
				fModel.setType(f.getFieldType().toString());
				if (f.isSetInitalValue())
				{
					fModel.setData(f.getInitalValue());
				}
				
				//Now commit to the database
				BpmGuardDAO.instance.getFieldDataDAO().insertFieldData(fModel);
			}
		}
		
		//Now do the callback.
		RequestCallBackDocument callBack = RequestCallBackDocument.Factory.newInstance();
		RequestCallBack c = callBack.addNewRequestCallBack();
		c.setRequestGuid(callbackGuidKey);
		c.setWorked(true);
		
		QueueJMSMessageSender sender = new QueueJMSMessageSender();
		sender.sendMessageCheck(Statics.JMS_TOPIC_PUSH, callBack.xmlText());
		
		return true;
	}
	
}
