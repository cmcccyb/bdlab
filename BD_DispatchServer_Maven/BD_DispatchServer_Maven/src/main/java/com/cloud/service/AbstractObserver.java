package com.cloud.service;

import java.util.HashMap;
import java.util.Random;

import com.cloud.util.PropertiesReader;
import com.cloud.util.ThreadStateFlag;

public abstract class AbstractObserver implements Observer {
	protected void commonUpdate(Subject subject, String appType,
			String sendFlag, String sendPath,
			HashMap<String, CommonThread> threadMap) {
		String datatype = subject.getDataType();
		String sendWayList = PropertiesReader.getProp("sendWayList");
		String[] sendWayArray = sendWayList.split(",");
		if (sendWayList == null) {
			return;
		}
		int i=0;
		for (String sendWay : sendWayArray) {
			// 1.定义线程的唯一性
			String threadKey = appType + "_" + datatype + "_" + sendWay;
			if (threadMap.containsKey(threadKey)) {
				CommonThread thread = threadMap.get(threadKey);
				if (thread.isAlive()) {
					continue;
				}
			}
			
			CommonThread thread = new CommonThread(sendWay,
					ThreadStateFlag.getInstance(threadKey), datatype, sendFlag,
					appType, sendPath);
			thread.setName(threadKey);
			thread.start();
			threadMap.put(threadKey, thread);
			i++;
			System.out.println(threadKey+"---"+i);
		}

	}
}
