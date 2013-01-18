package database.model;

public class FieldDataModel extends BaseModel{

	private String name;
	private String type;
	private String initialData;
	private Integer fieldId;
	
	public FieldDataModel()
	{
		
	}
	
	public FieldDataModel(String name, String type, String initialData, Integer fieldId)
	{
		this.name = name;
		this.type = type;
		this.initialData = initialData;
		this.fieldId = fieldId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getInitialData() {
		return initialData;
	}
	public void setInitialData(String initialData) {
		this.initialData = initialData;
	}
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	
	
	
}
