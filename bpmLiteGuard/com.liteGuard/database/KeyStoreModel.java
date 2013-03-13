package database;

public class KeyStoreModel {

	private Integer id;
	private String userId;
	private String fieldIds;
	private String userGuid;
	private String keyState;
	private Integer processId;
	private Integer stepId;
	private String caseId;
	private String orderList;
	private String displayOnly;
	private String globalIds;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getFieldIds() {
		return fieldIds;
	}
	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getKeyState() {
		return keyState;
	}
	public void setKeyState(String keyState) {
		this.keyState = keyState;
	}
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getOrderList() {
		return orderList;
	}
	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}
	public String getDisplayOnly() {
		return displayOnly;
	}
	public void setDisplayOnly(String displayOnly) {
		this.displayOnly = displayOnly;
	}
	public String getGlobalIds() {
		return globalIds;
	}
	public void setGlobalIds(String globalIds) {
		this.globalIds = globalIds;
	}
	
	
	
	
}
