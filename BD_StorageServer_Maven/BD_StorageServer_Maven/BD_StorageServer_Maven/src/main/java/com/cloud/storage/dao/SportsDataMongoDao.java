package com.cloud.storage.dao;

import com.cloud.storage.base.Domain.SportsData;

/**
 * 运动数据dao层
 * 
 * @author Administrator
 *
 */
public interface SportsDataMongoDao {
	/**
	 * 保存运动信息数据入Mongodb库
	 * 
	 * @param data
	 */
	public void saveOne(SportsData data);
}
