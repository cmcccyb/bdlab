package com.cloud.storage.service;

import com.cloud.storage.base.Domain.Patient;

/**
 * 
 * @author Administrator
 *
 */
public interface PatientService {
	public void savePatient2Mongo(Patient patient);
	public boolean savePatient2Mysql(Patient patient);
}
