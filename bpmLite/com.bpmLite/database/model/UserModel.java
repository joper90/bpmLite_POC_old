package database.model;

import java.util.Date;

public class UserModel extends BaseModel{
	private String name;
	private String description;
	private String uniqueKey;
	private Date   startTime; // hibernate mapping of timestamp //TODO need to check dataTime date perseistence.
	private String tibbrAddress;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getTibbrAddress() {
		return tibbrAddress;
	}
	public void setTibbrAddress(String tibbrAddress) {
		this.tibbrAddress = tibbrAddress;
	}
	
	
}
