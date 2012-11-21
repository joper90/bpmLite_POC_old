package commonModels.deployment;

import java.util.HashMap;

public class PAStep {

	private int	stepId;
	private String stepName;
	private String stepType;
	private String[] participants;
	private HashMap<String, String> extendedData = new HashMap<String,String>();
	private int[] fieldIds;
	private int[] nextStepId = new int[3];
	private int[] previousStepId = new int[3];
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	public String[] getParticipants() {
		return participants;
	}
	public void setParticipants(String[] participants) {
		this.participants = participants;
	}
	public HashMap<String, String> getExtendedData() {
		return extendedData;
	}
	public void setExtendedData(HashMap<String, String> extendedData) {
		this.extendedData = extendedData;
	}
	public int[] getFieldIds() {
		return fieldIds;
	}
	public void setFieldIds(int[] fieldIds) {
		this.fieldIds = fieldIds;
	}
	public int[] getNextStepId() {
		return nextStepId;
	}
	public void setNextStepId(int[] nextStepId) {
		this.nextStepId = nextStepId;
	}
	public int[] getPreviousStepId() {
		return previousStepId;
	}
	public void setPreviousStepId(int[] previousStepId) {
		this.previousStepId = previousStepId;
	}
	
	
	
}
