package database.model;

public class ProcessInstanceModel extends BaseModel{

	private String processId;
	private String currentStepId;


	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getCurrentStepId() {
		return currentStepId;
	}
	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}
	
	
}
