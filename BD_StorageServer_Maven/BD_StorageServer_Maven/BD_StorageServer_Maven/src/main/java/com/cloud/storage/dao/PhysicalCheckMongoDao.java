package com.cloud.storage.dao;

import java.util.Map;

import com.cloud.storage.base.Domain.PhysicalCheckData;

/**
 * 体检信息的dao层接口
 * 
 * @author Administrator
 *
 */
public interface PhysicalCheckMongoDao {
	/**
	 * 保存体检信息
	 * 
	 * @param physicalCheckData
	 */
	public void savePhyCheckData(PhysicalCheckData physicalCheckData);

	/**
	 * 根据用户id查询其体检信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getPhyCheckDataByUserId(Integer userId);

}
