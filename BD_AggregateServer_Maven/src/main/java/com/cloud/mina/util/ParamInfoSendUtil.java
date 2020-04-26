package com.cloud.mina.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 向设备下推配置信息、用户信息 of sending param info to stepcounter
 */
public class ParamInfoSendUtil {

	/**
	 * get need update info
	 */
	@SuppressWarnings("finally")
	public static boolean checkNeedAskParamInfo(String phone) {
		boolean needCheck = false;

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = C3P0Util.getConnection();
			st = conn.createStatement();
			String query = "SELECT needsend FROM userparaminfo_gateway WHERE phone='" + phone + "'";
			rs = st.executeQuery(query);
			if (rs != null && rs.next()) {
				needCheck = (rs.getString("needsend") != null && (rs.getString("needsend").equals("1")
						|| rs.getString("needsend").equals("2") || rs.getString("needsend").equals("3"))) ? true
								: false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			C3P0Util.releaseResource(conn, st, rs);
			return needCheck;
		}

	}
}
