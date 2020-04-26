package com.cloud.storage.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.dao.SportsDataMongoDao;
import com.cloud.storage.service.SportsDataService;

@Service
public class SportsDataServiceImpl implements SportsDataService {
	@Autowired
	private SportsDataMongoDao sportsDataMongoDao;

	@Override
	public void saveSportsData(SportsData sportsData) {
		sportsDataMongoDao.saveOne(sportsData);
	}

}
