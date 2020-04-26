package com.cloud.service;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.cloud.strategy.SendStrategy;
import com.cloud.strategy.StrategyContext;
import com.cloud.util.C3P0Util;
import com.cloud.util.Log;
import com.cloud.util.PropertiesReader;
import com.cloud.util.ThreadStateFlag;

public class CommonThread extends Thread {
	private String sendWay;
	private ThreadStateFlag threadStateFlag;
	private String datatype;
	private String sendFlag;
	private String appType;
	private String sendPath;
	private String baseQuerySql;
	private String baseUpdateSql;

	public CommonThread(String sendWay, ThreadStateFlag threadStateFlag, String datatype, String sendFlag,
			String appType, String sendPath) {
		super();
		this.threadStateFlag = threadStateFlag;
		this.datatype = datatype;
		this.sendFlag = sendFlag;
		this.appType = appType;
		this.sendPath = sendPath;
		this.sendWay = PropertiesReader.getProp(sendWay);
		this.baseQuerySql = PropertiesReader.getProp("baseQuerySql_" + sendWay);
		this.baseUpdateSql = PropertiesReader.getProp("baseUpdateSql_" + sendWay);
	}

	public void run() {
		synchronized (threadStateFlag) {
			List<HashMap<String, String>> unSendList = null;
			JSONObject data = null;
			String tableName = "sports";
			String querySql = String.format(baseQuerySql, tableName, sendFlag, "%" + appType + "%");
			unSendList = C3P0Util.getData(querySql);
			System.out.println("*******************print unSendList***********************");
			System.out.println(unSendList);
			if (unSendList.size() > 0) {
				for (HashMap<String, String> map : unSendList) {
					data = new JSONObject();
					data.put("appType", appType);
					data.put("dataType", map.get("dataType"));
					data.put("collectDate", map.get("receiveTime"));
					data.put("phone", map.get("phone"));
					data.put("dataValue", map.get("dataValue"));
					data.put("deviceID", map.get("deviceID"));
					data.put("company", map.get("company"));
					data.put("pname", map.get("pname"));
					data.put("teamName", map.get("teamName"));

					if (StrategyContext.sendData(data.toString(), sendPath, appType, sendWay)) {
						String updateSql = String.format(baseUpdateSql, tableName, sendFlag, map.get("id"));
						boolean updateSuccess = C3P0Util.executeUpdate(updateSql);
						System.out.println(updateSql);
						if(!updateSuccess){
							Log.error("send data success,update data fail,sql is "+updateSql);
						}
					}
				}

			}
			threadStateFlag.notify();
		}

	}

}
