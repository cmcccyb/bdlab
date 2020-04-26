package com.cloud.send;

import java.util.HashMap;
import java.util.List;

import com.cloud.service.SubjectFactory;
import com.cloud.util.C3P0Util;

public class SendData {
	public static void main(String[] args) {
		String dataType = null;
		String appType = null;

			// 1.从sport中通过c3p0查询未发送（AppA_flag=0）的数据
			String sql = "SELECT * FROM sports WHERE AppA_flag='0' LIMIT 500";
			List<HashMap<String, String>> lists = C3P0Util.getData(sql);

			System.out.println(lists);
			
			// 2. 建立一个主题（收到数据后触发发送），通知所有的观察着接收数据
			for (HashMap<String, String> list : lists) {
				dataType = list.get("dataType");
				appType = list.get("appType");
				SubjectFactory.getSubject(appType, dataType).notifyObservers();
			}

	}
}
 