package guard.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * This is the complete form data, containing all the fields and the orderList (if needed)
 */

@XmlRootElement
public class CompleteFormData {

	private ArrayList<FormData> formData = new ArrayList<FormData>();
	private String orderList;
	private boolean isValid = false;
	
	
	//User details needed for posting
	private String userId;
	private String requestId;
	
	public CompleteFormData()
	{
		
	}

	public ArrayList<FormData> getFormData() {
		return formData;
	}

	public void setFormData(ArrayList<FormData> formData) {
		this.formData = formData;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	
	
}