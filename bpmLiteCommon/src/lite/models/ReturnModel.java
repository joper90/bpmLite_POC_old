package lite.models;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReturnModel {

	private String result = "failed";
	private String reason = "Unknown Error..";
	private boolean worked = false;
	private HashMap<String, String> extendedData = new HashMap<String,String>();
	
	public ReturnModel(){}
	
	public ReturnModel(String result, String reason, boolean worked)
	{
		this.result = result;
		this.reason = reason;
		this.worked = worked;
	}
	
	public void setValues(String result, String reason, boolean worked)
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

	public HashMap<String, String> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(HashMap<String, String> extendedData) {
		this.extendedData = extendedData;
	}
	
	public boolean addExtendedDataElement(String name, String value)
	{
		String put = this.extendedData.put(name, value);
		if (put == null) return true; // this means a new value;
		return false;
			
	}
	
}
