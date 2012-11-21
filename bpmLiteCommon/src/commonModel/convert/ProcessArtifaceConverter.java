package commonModel.convert;

import java.util.HashMap;
import java.util.Set;

import com.bpmlite.api.FieldModeType;
import com.bpmlite.api.ProcessArtifactDocument;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.Fields;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.ExtendedData;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.FieldData;
import com.bpmlite.api.ProcessArtifactDocument.ProcessArtifact.ProcessData.Steps.Participants;
import com.bpmlite.api.StepType;
import commonModels.deployment.PAField;
import commonModels.deployment.PAProcessArtifact;
import commonModels.deployment.PAProcessData;
import commonModels.deployment.PAStep;

public class ProcessArtifaceConverter {
	public static ProcessArtifactDocument convertToBean(PAProcessArtifact toConvert)
	{
		ProcessArtifactDocument paDoc = ProcessArtifactDocument.Factory.newInstance();
		ProcessArtifact pa = paDoc.addNewProcessArtifact();
		ProcessData paData = pa.addNewProcessData();
		
		
		//Add fields
		Fields[] fArray = new Fields[toConvert.getFields().length];
		int pos = 0;
		for (PAField paF : toConvert.getFields())
		{
			Fields f = Fields.Factory.newInstance();
			f.setName(paF.getName());
			f.setType(FieldModeType.Enum.forString(paF.getType()));
			f.setInitalData(paF.getInitalData());
			f.setFieldId(paF.getFieldId());
			
			fArray[pos++] = f;
		}
			
		
		//Now do the Steps
		Steps[] sArray = new Steps[toConvert.getProcessData().getSteps().length];
		pos = 0;
		for (PAStep paS : toConvert.getProcessData().getSteps())
		{
			Steps s = Steps.Factory.newInstance();
			s.setStepId(paS.getStepId());
			s.setStepName(paS.getStepName());
			s.setStepType(StepType.Enum.forString(paS.getStepType()));
			
			//Add Participants
			Participants[] p = new Participants[paS.getParticipants().length];
			int pCount = 0;
			for (String pString : paS.getParticipants())
			{
				Participants pAdd = Participants.Factory.newInstance();
				pAdd.setUserGuid(pString);
				p[pCount++] = pAdd;
			}
			s.setParticipantsArray(p);
			
			//AddExtendedData
			ExtendedData[] e = new ExtendedData[paS.getExtendedData().size()];
			pCount = 0;
			HashMap<String, String> extendedDataMap = paS.getExtendedData();
			Set<String> keySet = extendedDataMap.keySet();
			for (String k : keySet)
			{
				ExtendedData eAdd = ExtendedData.Factory.newInstance();
				eAdd.setName(k);
				eAdd.setValue(extendedDataMap.get(k));
				e[pCount++] = eAdd;
			}
			s.setExtendedDataArray(e);
			
			//FieldData
			FieldData[] f = new FieldData[paS.getFieldIds().length];
			pCount = 0;
			for (int i : paS.getFieldIds())
			{
				FieldData fAdd = FieldData.Factory.newInstance();
				fAdd.setFieldId(i);
				f[pCount++] = fAdd;
			}
			s.setFieldDataArray(f);

			//NextStep
			s.setNextStepIdArray(paS.getNextStepId());
			//PreviousStep
			s.setPreviousStepIdArray(paS.getPreviousStepId());
			
			
			//Now add the step to the main step array
			sArray[pos++] = s;
		}
		
		paData.setStepsArray(sArray);
		paData.setProcessName(toConvert.getProcessData().getProcessName());
		paData.setProcessDescription(toConvert.getProcessData().getProcessDescription());
		paData.setVersion(toConvert.getProcessData().getVersion());
		paData.setAuthor(toConvert.getProcessData().getAuthor());
		
		
		pa.setUniqueGuid(toConvert.getUniqueGuid());
		pa.setFieldsArray(fArray);
		pa.setProcessData(paData);
		return paDoc;
	}
	
	public static PAProcessArtifact convertToNonBean(ProcessArtifactDocument toConvert)
	{
		PAProcessArtifact paRet = new PAProcessArtifact();
		//Create fields
		paRet.setUniqueGuid(toConvert.getProcessArtifact().getUniqueGuid());
		
		Fields[] fieldsArray = toConvert.getProcessArtifact().getFieldsArray();
		PAField[] paFieldsArray = new PAField[fieldsArray.length];
		int pCount = 0;
		for (Fields f : fieldsArray)
		{
			PAField toAdd = new PAField();
			toAdd.setName(f.getName());
			toAdd.setType(f.getType().toString());
			toAdd.setInitalData(f.getInitalData());
			toAdd.setFieldId(f.getFieldId());
			
			paFieldsArray[pCount++] = toAdd;
		}
		
		//Steps
		Steps[] stepsArray = toConvert.getProcessArtifact().getProcessData().getStepsArray();
		PAStep[] paStepArray = new PAStep[stepsArray.length];
		pCount = 0;
		for (Steps s : stepsArray)
		{
			PAStep sAdd = new PAStep();
			sAdd.setStepId(s.getStepId());
			sAdd.setStepName(s.getStepName());
			sAdd.setStepType(s.getStepType().toString());
			
			Participants[] participantsArray = s.getParticipantsArray();
			String[] pArray = new String[participantsArray.length];
			int paCount = 0;
			for (Participants p : participantsArray)
			{
				pArray[paCount++] = p.getUserGuid();
			}
			sAdd.setParticipants(pArray);
			
			HashMap<String, String> extMap = new HashMap<String, String>();						
			for (ExtendedData e : s.getExtendedDataArray())
			{
				extMap.put(e.getName(), e.getValue());
			}
			sAdd.setExtendedData(extMap);
			
			FieldData[] fieldDataArray = s.getFieldDataArray();
			int[] fArray = new int[fieldDataArray.length];
			paCount = 0;
			for (FieldData f : fieldDataArray)
			{
				fArray[pCount++] = f.getFieldId();
			}
			sAdd.setFieldIds(fArray);
			
			sAdd.setNextStepId(s.getNextStepIdArray());
			sAdd.setPreviousStepId(s.getPreviousStepIdArray());
					
			paStepArray[pCount++] = sAdd;
		}
		
		PAProcessData processData = new PAProcessData();
		processData.setProcessName(toConvert.getProcessArtifact().getProcessData().getProcessName());
		processData.setProcessDescription(toConvert.getProcessArtifact().getProcessData().getProcessDescription());
		processData.setVersion(toConvert.getProcessArtifact().getProcessData().getVersion());
		processData.setAuthor(toConvert.getProcessArtifact().getProcessData().getAuthor());
		processData.setSteps(paStepArray);
		
		paRet.setProcessData(processData);
		paRet.setFields(paFieldsArray);
		
		return paRet;
	}
		
}
