package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * This is the complete form data, containing all the fields and the orderList (if needed)
 */

@XmlRootElement
public class CompleteFormData {

	private ArrayList<FormData> formData = new ArrayList<FormData>();
	private String orderList = "";
	private boolean isValid = false;
	
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
	
	
	
}
