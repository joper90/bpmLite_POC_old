package database;

public class KeyStoreModel {

	private Integer id;
	private String userId;
	private String fieldIds;
	private String userGuid;
	private boolean keyCollected;
	
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
	public boolean isKeyCollected() {
		return keyCollected;
	}
	public void setKeyCollected(boolean keyCollected) {
		this.keyCollected = keyCollected;
	}
	
	
	
}
