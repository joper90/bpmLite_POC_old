package database.model;

public class StepDataModel extends BaseModel{

	private String stepId;
	private String processId;
	private String stepType;
	private String stepDetails;
	private String userList;
	private String nextId;
	private String previousId;
	private String fieldData;
	private String globalData;
	private String displayOnly;
	
	public StepDataModel(){};
	
	

	public StepDataModel(String stepId, String processId, String stepType,
			String stepDetails, String userList, String nextId,
			String previousId, String fieldData, String globalData, String displayOnly) {
		super();
		this.stepId = stepId;
		this.processId = processId;
		this.stepType = stepType;
		this.stepDetails = stepDetails;
		this.userList = userList;
		this.nextId = nextId;
		this.previousId = previousId;
		this.fieldData = fieldData;
		this.globalData = globalData;
		this.displayOnly = displayOnly;
	}



	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	public String getStepDetails() {
		return stepDetails;
	}
	public void setStepDetails(String stepDetails) {
		this.stepDetails = stepDetails;
	}
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public String getNextId() {
		return nextId;
	}
	public void setNextId(String nextId) {
		this.nextId = nextId;
	}
	public String getPreviousId() {
		return previousId;
	}
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	public String getFieldData() {
		return fieldData;
	}
	public void setFieldData(String fieldData) {
		this.fieldData = fieldData;
	}
	public String getGlobalData() {
		return globalData;
	}
	public void setGlobalData(String globalData) {
		this.globalData = globalData;
	}
	public String getDisplayOnly() {
		return displayOnly;
	}	public void setDisplayOnly(String displayOnly) {
		this.displayOnly = displayOnly;
	}
	
	
}
