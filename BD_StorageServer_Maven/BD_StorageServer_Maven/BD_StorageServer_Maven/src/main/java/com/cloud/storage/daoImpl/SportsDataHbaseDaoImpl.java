package com.cloud.storage.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.storage.base.Domain.HQuery;
import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.dao.SportsDataHbaseDao;
import com.cloud.storage.util.HBaseTemplate;

@Repository
public class SportsDataHbaseDaoImpl implements SportsDataHbaseDao{
	@Autowired
	private  HBaseTemplate hbaseTemplate;
	
	public boolean addSportsData(HQuery query){
		boolean addSuccess=false;
		try {
			hbaseTemplate.execute(query);
			addSuccess=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addSuccess;
	}

	@Override
	public List<SportsData> selectByQuery(HQuery query) {
		// TODO Auto-generated method stub
		List<SportsData> datas = hbaseTemplate.find(query, SportsData.class);
		return datas;
	}
	
}
