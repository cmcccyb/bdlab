package com.cloud.util;

import java.util.HashMap;

/**
 * 线程状态标识，对同一Key值只有一个实例，不同的Key有不同的实例，习惯称为伪单例,
 */
public class ThreadStateFlag {
	private static HashMap<String, ThreadStateFlag> iMap = new HashMap<String, ThreadStateFlag>();

	private ThreadStateFlag() {
	}

	public static synchronized ThreadStateFlag getInstance(String key) {
		if (iMap.containsKey(key)) {
			return iMap.get(key);
		} else {
			iMap.put(key, new ThreadStateFlag());
			return iMap.get(key);
		}
	}
}
