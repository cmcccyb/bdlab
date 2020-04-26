package com.cloud.strategy;

import com.cloud.util.Log;

public class StrategyContext {
	private static SendStrategy mqStrategy = null;
	private static SendStrategy postStrategy = null;
	static {
		mqStrategy = new MqStrategy();
		postStrategy = new PostStrategy();
	}

	/**
	 * 发送接口
	 * 
	 * @param data
	 *            //发送的数据
	 * @param url
	 *            //用户Post方式发送的目的地址
	 * @param appType
	 *            //用于MQ方式发送时，查找queueName
	 * @param sendWay
	 *            //用于指定发送策略：Post | MQ
	 * @return
	 */
	public static boolean sendData(String data, String url, String appType,
			String sendWay) {
		if ("MqStrategy".equals(sendWay)) {
			Log.debug("MqStrategy 方式发送数据");
			return mqStrategy.send(data, url, appType);
		} else if ("PostStrategy".equals(sendWay)) {
			Log.debug("PostStrategy 方式发送数据");
			return postStrategy.send(data, url, appType);
		} else {
			Log.info("没有指定发送方式！！！sendWay:" + sendWay);
			return false;
		}
	}
}
