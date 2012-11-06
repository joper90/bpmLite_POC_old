package model;

import javax.xml.bind.annotation.XmlRootElement;

import config.Statics;

@XmlRootElement
public class ReturnModel {

	private String result = Statics.EMS_PUSH_FAILED;
	private String reason = "Unknown Error..";
	private boolean worked = false;
	
	public ReturnModel(){}
	
	public ReturnModel(String result, String reason, boolean worked)
	{
		this.result = result;
		this.reason = reason;
		this.worked = worked;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isWorked() {
		return worked;
	}

	public void setWorked(boolean worked) {
		this.worked = worked;
	}
	
	
}
