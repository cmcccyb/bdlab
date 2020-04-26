package com.cloud.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.dao.SportsDataMongoDao;
import com.cloud.storage.dao.MongodbBaseDao;

/**
 * 运动数据Mongodb的dao层实现类
 * 
 * @author Administrator
 *
 */
@Repository
public class SportsDataMongoDaoImpl extends MongodbBaseDao<SportsData> implements SportsDataMongoDao {

	@Override
	protected Class getEntityClass() {
		return SportsData.class;
	}

	@Autowired
	@Override
	protected void setMongoTemplate(@Qualifier("mongoTemplate") MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveOne(SportsData data) {
		this.save(data);
	}
}
