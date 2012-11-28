package database.model;

import java.util.Date;

public class UserModel {
	private Integer id;
	private String name;
	private String description;
	private String uniqueKey;
	private Date   startDate; // hibernate mapping of timestamp //TODO need to check dataTime date perseistence.
	private String tibbrAddress;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getTibbrAddress() {
		return tibbrAddress;
	}
	public void setTibbrAddress(String tibbrAddress) {
		this.tibbrAddress = tibbrAddress;
	}
	
	
}
