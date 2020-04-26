package com.cloud.storage.dao;

import java.util.List;

import com.cloud.storage.base.Domain.HQuery;
import com.cloud.storage.base.Domain.SportsData;

public interface SportsDataHbaseDao {
	boolean addSportsData(HQuery query);

	 List<SportsData> selectByQuery(HQuery query);
}
