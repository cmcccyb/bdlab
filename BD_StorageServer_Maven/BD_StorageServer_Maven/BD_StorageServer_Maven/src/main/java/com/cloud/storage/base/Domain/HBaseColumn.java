package com.cloud.storage.base.Domain;

public class HBaseColumn {
	private String family;
	private String qualifier;
	private String value;
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public HBaseColumn(String family, String qualifier, String value) {
		super();
		this.family = family;
		this.qualifier = qualifier;
		this.value = value;
	}
	
}
