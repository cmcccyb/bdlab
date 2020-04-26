package com.cloud.send;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class Simulator {

	public static void main(String[] args) {

		while (true) {
			// 接口地址模板
			String sendDestination ="http://localhost:8080/BD_StorageServer_Maven/service/";
			String url = sendDestination
					+ "businessDataInsert.json/%s/%s/%s/%s?format=json";
			for (int i = 0; i <= 100; i++) {
				// 组装时间格式为 yyyyMMddHHmmss 20140611171500
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
				String date = df.format(new Date());
				System.out.println("开始插入" + date);
				insertBloodPressCommon(date, url);
			}
			System.out.println("插入数据完成 OK");
		}
	}

	private static void insertBloodPressCommon(String date, String url) {
		url = String.format(url, "13910828743", date, "bloodPressure",
				"AppA");
		System.out.println(url);
		// 组装发送数据
		JSONObject jo = new JSONObject();
		jo.put("heartrate", "79");
		jo.put("systolic", "120");
		jo.put("diastolic", "80");
		insertCommon(jo, url);

	}

	/**
	 * 通用发送数据
	 * 
	 * @param jo
	 *            数据，格式为JSON对象
	 * @param url
	 *            发送数据的地址
	 */
	private static void insertCommon(JSONObject jo, String url) {

		HttpClient client = new HttpClient();
		PostMethod postM = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		try {
			postM = new PostMethod(url);
			postM.setParameter("param", jo.toString());
			System.out.println(jo.toString() + "@@@@@@");
			// 执行POST请求
			int executeMethod = client.executeMethod(postM);
			// 解析返回结果集
			Reader reader = new InputStreamReader(
					postM.getResponseBodyAsStream(), "utf8");
			BufferedReader br = new BufferedReader(reader);
			StringBuffer buf = new StringBuffer();
			String line = "";
			while (null != (line = br.readLine())) {
				buf.append(line);
			}
			// 将结果集复制给变量responseString
			String responseString = buf.toString();
			// 打印返回结果
			System.out.println(responseString);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != postM)
				postM.releaseConnection();// 释放连接
		}
	}
}
