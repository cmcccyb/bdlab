package com.cloud.storage.pattern.strategy;

import org.springframework.stereotype.Component;

import com.cloud.storage.pattern.state.Context;

/**
 * unitA公司的AppB业务数据处理策略类
 * 
 * @author Administrator
 *
 */
@Component("AppBdatabean")
public class AppBtrategy extends Strategy {
	@Override
	public boolean dealData(Context context) {
		return false;
	}
}
