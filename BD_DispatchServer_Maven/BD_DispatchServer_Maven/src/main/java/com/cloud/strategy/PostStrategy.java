package com.cloud.strategy;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.cloud.util.Log;

public class PostStrategy implements SendStrategy {

	@Override
	public boolean send(String data, String url, String appType) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		boolean isSuccess = false;
		try {
			System.out.println(url);
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			post.addParameter("data", data);
			Log.debug(this.getClass().getSimpleName() + " begin send data...");
			Log.debug(this.getClass().getSimpleName() + " send data : " + data);
			// 设置连接超时时间
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			// 设置响应超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(15000);
			int returnFlag = client.executeMethod(post);
			if (returnFlag == 200) {
				isSuccess = true;
			}
			Log.info(this.getClass().getSimpleName()
					+ " success receive form post: "
					+ post.getStatusLine().toString() + ",returnFlag="
					+ returnFlag);
		} catch (Exception e) {
			isSuccess = false;
			Log.error(this.getClass().getSimpleName()
					+ " fail receive form post: " + e.getMessage());
		} finally {
			if (post != null) {
				post.releaseConnection();
				Log.info(this.getClass().getSimpleName()
						+ " post.releaseConnection() " + "is coming");
			}
		}
		return isSuccess;
	}

}
