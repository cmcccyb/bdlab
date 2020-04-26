package com.cloud.storage.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.storage.base.Domain.Message;
import com.cloud.storage.base.Domain.PhysicalCheckData;
import com.cloud.storage.dao.PhysicalCheckMongoDao;
import com.cloud.storage.service.PhysicalCheckDataService;

@Service
public class PhysicalCheckDataServiceImpl implements PhysicalCheckDataService {
	@Autowired
	private PhysicalCheckMongoDao phyCheckDao;

	@Override
	public Message savePhyCheckData(PhysicalCheckData physicalCheckData) {
		Message message = new Message();
		try {
			phyCheckDao.savePhyCheckData(physicalCheckData);
			message.setCode(1000);
			message.setMessage("数据处理成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setCode(10003);
			message.setMessage("数据处理失败");
		}
		return message;
	}

	@Override
	public Map<String, Object> getPhyCheckDataByUserId(Integer userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (userId != null) {
			result = phyCheckDao.getPhyCheckDataByUserId(userId);
		}
		return result;
	}
}
