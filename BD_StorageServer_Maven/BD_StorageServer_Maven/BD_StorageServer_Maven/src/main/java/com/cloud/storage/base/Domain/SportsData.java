package com.cloud.storage.base.Domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 运动信息数据javaBean
 * 
 * @author changyaobin
 *
 */
@Document(collection = "SportsData")
public class SportsData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String appType;
	private String dataType;
	private String phone;
	private String collectDate;
	private List<Map<String, String>>dataValue;
    private String deviceID;
	private String sendFlag;
	private String company;
	private String teamName;
	private String pname;
	

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	


	public List<Map<String, String>> getDataValue() {
		return dataValue;
	}

	public void setDataValue(List<Map<String, String>> dataValue) {
		this.dataValue = dataValue;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
