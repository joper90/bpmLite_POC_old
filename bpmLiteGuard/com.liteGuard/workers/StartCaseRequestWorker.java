package workers;

import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails.FieldDetails;

import database.FieldDataModel;
import database.GlobalData;
import database.DAO.BpmGuardDAO;

public class StartCaseRequestWorker {

	public static boolean setupInitialDetails(StartCaseDetailsDocument startCaseDetailsDoc)
	{		
		StartCaseDetails startCaseDetails = startCaseDetailsDoc.getStartCaseDetails();
		int processId = startCaseDetails.getProcessId();
		String caseId = startCaseDetails.getCaseId();
		FieldDetails[] fieldDetailsArray = startCaseDetails.getFieldDetailsArray();
		
		for (FieldDetails f : fieldDetailsArray)
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
		
		return true;
	}
	
}
