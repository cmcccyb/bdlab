package com.cloud.storage.service;

import java.util.Map;

import com.cloud.storage.base.Domain.Message;
import com.cloud.storage.base.Domain.PhysicalCheckData;

/**
 * 体检报告的service层
 * 
 * @author Administrator
 *
 */
public interface PhysicalCheckDataService {
	/**
	 * 保存体检报告数据
	 * 
	 * @Title : savePhyCheckData
	 * @功能描述: TODO
	 * @设定文件：@param physicalCheckData
	 * @返回类型：void
	 * @author：changyaobin
	 * @throws ：
	 */
	public Message savePhyCheckData(PhysicalCheckData physicalCheckData);

	/**
	 * 根据用户id查询用户基本信息以及体检信息
	 * 
	 * @Title : getPhyCheckDataByUserId
	 * @功能描述: TODO
	 * @设定文件：@param userId
	 * @返回类型：void
	 * @author：changyaobin
	 * @throws ：
	 */
	public Map<String, Object> getPhyCheckDataByUserId(Integer userId);
}
