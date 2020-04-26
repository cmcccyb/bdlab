package com.cloud.service;

import com.cloud.util.AppInfoContext;

public class SubjectFactory {
	public static Subject getSubject(String appTypes, String dataType) {
		DataSaveSubject subject = new DataSaveSubject();
		subject.setAppType(appTypes);
		subject.setDataType(dataType);

		if (appTypes != null && !"".equals(appTypes) ){
			String[] app = appTypes.split(";");
			for (String appType : app) {
				Observer obs = (Observer) new CommonObserver(
						AppInfoContext.getPropertyByApp(appType, "sendFlag"),
						appType, AppInfoContext.getPropertyByApp(appType,
								"sendPath"));
				subject.add(obs);
			}
		}
		return subject;
	}
}
