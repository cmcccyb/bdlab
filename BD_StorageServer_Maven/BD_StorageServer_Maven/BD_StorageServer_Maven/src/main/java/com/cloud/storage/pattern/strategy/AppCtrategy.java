package com.cloud.storage.pattern.strategy;

import org.springframework.stereotype.Component;

import com.cloud.storage.pattern.state.Context;

/**
 * unitA公司的AppC业务数据处理策略类
 * 
 * @author Administrator
 *
 */
@Component("AppCdatabean")
public class AppCtrategy extends Strategy {
	@Override
	public boolean dealData(Context context) {
		return false;
	}
}
