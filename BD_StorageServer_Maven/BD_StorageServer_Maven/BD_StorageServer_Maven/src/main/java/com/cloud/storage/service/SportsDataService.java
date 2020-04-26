package com.cloud.storage.service;

import com.cloud.storage.base.Domain.SportsData;

/**
 * 运动信息的service层
 * 
 * @author Administrator
 *
 */
public interface SportsDataService {
	/**
	 * 运动数据保存入库接口
	 * 
	 * @param sportsData
	 */
	public void saveSportsData(SportsData sportsData);
}
