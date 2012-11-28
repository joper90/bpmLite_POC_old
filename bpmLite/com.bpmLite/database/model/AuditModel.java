package database.model;

public class AuditModel {

	private Integer id;
	private String action;
	private String user;
	private String stepId;
	private String startTime;
	private String competeTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCompeteTime() {
		return competeTime;
	}
	public void setCompeteTime(String competeTime) {
		this.competeTime = competeTime;
	}
	
	
}
