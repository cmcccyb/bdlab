package com.cloud.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.storage.base.Domain.Patient;
import com.cloud.storage.dao.MongodbBaseDao;
import com.cloud.storage.dao.PatientMongoDao;

/**
 * 用户信息dao层实现类（用于操作Mongodb）
 * 
 * @author Administrator
 *
 */
@Repository
public class PatientMongoDaoImpl extends MongodbBaseDao<Patient> implements PatientMongoDao {

	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return Patient.class;
	}

	@Autowired
	@Override
	protected void setMongoTemplate(@Qualifier(value = "mongoTemplate") MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	public void savePatient(Patient patient) {
		super.save(patient);
	}

}
