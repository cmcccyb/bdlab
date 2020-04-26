package com.cloud.strategy;

import com.cloud.util.PropertiesReader;

public class MqStrategy implements SendStrategy {
	static ObsDataMsgPublisher publisher = null;
	static {
		publisher = ObsDataMsgPublisher.getInstance();
	}

	@Override
	public boolean send(String data, String url, String appType) {
		if (publisher == null) {
			return false;
		}
		String queueName = PropertiesReader.getProp("jms.queue." + appType);
		System.out.println("queueName="+queueName);
		if (queueName == null || "".equals(queueName)) {
			return false;
		}
		return publisher.sendByQuene(data, queueName);
	}

}