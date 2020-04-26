package com.cloud.service;

import java.util.HashMap;

public class CommonObserver extends AbstractObserver {
	private String sendFlag = null;
	private String sendPath = null;
	private String appType = null;
	private static HashMap<String, CommonThread> threadMap = new HashMap<String, CommonThread>();

	public CommonObserver(String sendFlag, String appType, String sendPath) {
		this.sendFlag = sendFlag;
		this.sendPath = sendPath;
		this.appType = appType;
	}

	@Override
	public void update(Subject subject) {
		commonUpdate(subject, appType, sendFlag, sendPath, threadMap);
	}
}
