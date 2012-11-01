package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidateDetails {

	private String userName;
	private String userGuid;
	private String userKey;
	
	public ValidateDetails()
	{
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	
	
}
