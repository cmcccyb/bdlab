package com.cloud.storage.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.storage.base.Domain.Patient;
import com.cloud.storage.dao.PatientMongoDao;
import com.cloud.storage.daoImpl.JdbcDaoImpl;
import com.cloud.storage.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientMongoDao patientMongoDao;
	@Autowired
	private JdbcDaoImpl jdbcDaoImpl;

	@Override
	public void savePatient2Mongo(Patient patient) {
		patientMongoDao.savePatient(patient);
	}

	@Override
	public boolean savePatient2Mysql(Patient patient) {
		boolean saveSuccess=false;
		// TODO Auto-generated method stub
		String sql="select * from  patient where phone='"+patient.getPhone()+"'";
		List<HashMap<String, String>> data = jdbcDaoImpl.getData(sql);
		if(data!=null&&data.size()>0){
			String updateSql="update patient set `name`='"+patient.getName()+"',`deviceId`='"+patient.getDeviceId()+"',`appType`='"+patient.getAppType()+"' where `phone`='"+patient.getPhone()+"'";
			saveSuccess=jdbcDaoImpl.executeUpdate(updateSql);
		}else{
			String insertSql="insert into patient (`name`,`phone`,`deviceId`,`appType`) values('"+patient.getName()+"','"+patient.getPhone()+"','"+patient.getDeviceId()+"','"+patient.getAppType()+"') ";
			saveSuccess=jdbcDaoImpl.executeUpdate(insertSql);
		}
		return saveSuccess;
	}
}
