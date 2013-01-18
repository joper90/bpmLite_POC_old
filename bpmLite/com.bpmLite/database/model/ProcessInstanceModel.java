package database.model;

public class ProcessInstanceModel extends BaseModel{

	private Integer processId;
	private Integer currentStepId;
	private String caseId;
	private boolean initialDataSet;
	private String	guidCallback;
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public Integer getCurrentStepId() {
		return currentStepId;
	}
	public void setCurrentStepId(Integer currentStepId) {
		this.currentStepId = currentStepId;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public boolean isInitialDataSet() {
		return initialDataSet;
	}
	public void setInitialDataSet(boolean initialDataSet) {
		this.initialDataSet = initialDataSet;
	}
	public String getGuidCallback() {
		return guidCallback;
	}
	public void setGuidCallback(String guidCallback) {
		this.guidCallback = guidCallback;
	}
	

	
	
}
