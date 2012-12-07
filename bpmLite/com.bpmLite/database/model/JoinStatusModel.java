package database.model;

public class JoinStatusModel extends BaseModel{

	private String stepId;
	private String stepOne;
	private String stepTwo;
	private String stepThree;
	private Integer stepCountRequired;

	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getStepOne() {
		return stepOne;
	}
	public void setStepOne(String stepOne) {
		this.stepOne = stepOne;
	}
	public String getStepTwo() {
		return stepTwo;
	}
	public void setStepTwo(String stepTwo) {
		this.stepTwo = stepTwo;
	}
	public String getStepThree() {
		return stepThree;
	}
	public void setStepThree(String stepThree) {
		this.stepThree = stepThree;
	}
	public Integer getStepCountRequired() {
		return stepCountRequired;
	}
	public void setStepCountRequired(Integer stepCountRequired) {
		this.stepCountRequired = stepCountRequired;
	}
	
	
}
