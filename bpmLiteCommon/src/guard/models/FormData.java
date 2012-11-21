package guard.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormData {

	public enum FIELD_TYPE { STRING,INTEGER,DECIMAL,BOOLEAN};
	public enum FIELD_MODE { EDIT,DISPLAY};
	
	private String fieldName = "";
	private String fieldData = "";
	private FIELD_TYPE fieldType = FIELD_TYPE.STRING;
	private FIELD_MODE fieldMode = FIELD_MODE.EDIT;
	
	public FormData()
	{
		
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldData() {
		return fieldData;
	}

	public void setFieldData(String fieldData) {
		this.fieldData = fieldData;
	}

	public FIELD_TYPE getFieldType() {
		return fieldType;
	}

	public void setFieldType(FIELD_TYPE fieldType) {
		this.fieldType = fieldType;
	}

	public FIELD_MODE getFieldMode() {
		return fieldMode;
	}

	public void setFieldMode(FIELD_MODE fieldMode) {
		this.fieldMode = fieldMode;
	}
	
	
	
}
