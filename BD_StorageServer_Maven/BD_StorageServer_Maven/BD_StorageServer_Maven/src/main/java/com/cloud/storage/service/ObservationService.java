package com.cloud.storage.service;

/**
 * 数据插入mysql的接口层
 * 
 * @author Administrator
 *
 */
public interface ObservationService {

	/**
	 * 通用的数据插入接口
	 * 
	 * @param uniqueField
	 *            phone number
	 * @param dateTime
	 *            measureTime
	 * @param businessType
	 *            from concept table's conceptDescribe filed
	 * @param appType
	 *            app type
	 * @param param
	 *            JSONObject string
	 * @param receiveDateTime
	 *            when the date be collected
	 */
	public boolean insertOrUpdateData(String uniqueField, String dateTime, String businessType, String appType,
			String param, String collectDate);
}