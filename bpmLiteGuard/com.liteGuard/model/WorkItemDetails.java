package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkItemDetails {

	private int processId;
	private int stepId;
	private String uniqueFormGuid;
	private String userKey;
		
	public WorkItemDetails()
	{
		
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getStepId() {
		return stepId;
	}

	public void setStepId(int stepId) {
		this.stepId = stepId;
	}

	public String getUniqueFormGuid() {
		return uniqueFormGuid;
	}

	public void setUniqueFormGuid(String uniqueFormGuid) {
		this.uniqueFormGuid = uniqueFormGuid;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	
	
}
