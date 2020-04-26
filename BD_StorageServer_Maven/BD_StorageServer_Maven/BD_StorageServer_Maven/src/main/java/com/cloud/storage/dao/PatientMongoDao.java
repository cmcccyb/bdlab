package com.cloud.storage.dao;

import com.cloud.storage.base.Domain.Patient;

/**
 * 患者的Dao层
 * 
 * @author Administrator
 *
 */
public interface PatientMongoDao {

	void savePatient(Patient patient);

}
