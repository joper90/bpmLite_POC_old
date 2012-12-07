package database.model;

public class AssignedRoleModel extends BaseModel{


	private Integer user;
	private String role;
	private String groupMembership;
	private String userSkills;

	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGroupMembership() {
		return groupMembership;
	}
	public void setGroupMembership(String groupMembership) {
		this.groupMembership = groupMembership;
	}
	public String getUserSkills() {
		return userSkills;
	}
	public void setUserSkills(String userSkills) {
		this.userSkills = userSkills;
	}
	
	
	
}
