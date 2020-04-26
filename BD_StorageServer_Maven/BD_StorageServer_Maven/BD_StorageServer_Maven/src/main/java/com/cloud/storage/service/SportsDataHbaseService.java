package com.cloud.storage.service;

import com.cloud.storage.base.Domain.SportsData;

public interface SportsDataHbaseService {
	boolean saveData(SportsData data) throws Exception;
}
