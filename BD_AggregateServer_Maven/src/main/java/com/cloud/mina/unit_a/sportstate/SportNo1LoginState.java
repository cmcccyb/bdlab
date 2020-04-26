package com.cloud.mina.unit_a.sportstate;

import java.util.Calendar;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.LoginPacket;
import com.cloud.mina.util.DataTypeChangeHelper;
import com.cloud.mina.util.Logger;
import com.cloud.mina.util.MLinkCRC;
import com.cloud.mina.util.ParamInfoSendUtil;

/**
 * unitA公司运动数据包（1号包）登录状态处理类
 * 
 * @author Administrator
 *
 */
public class SportNo1LoginState implements SportsPacketHandleState {

	public boolean handlePacket(IoSession session, Object message) {
		LoginPacket packet = null;
		if (message != null && message instanceof LoginPacket) {
			packet = (LoginPacket) message;

			if (packet.getPatientID() == null || "".equals(packet.getPatientID().trim())) {
				return false;
			}
			session.setAttribute("patientId", packet.getPatientID());
			session.setAttribute("deviceId", packet.getDeviceID());
			session.setAttribute("company", packet.getCompany());
			session.setAttribute("loginTime", packet.getLoginTime());
			session.setAttribute("appType", packet.getAppType());
			// 回复ACK
			responseToClient(session);
			return true;
		} else {
			// 回复NAK
			// responseToClient(session);
			return false;
		}

	}

	private void responseToClient(IoSession session) {
		byte[] ack = responsePacking(session);
		session.write(IoBuffer.wrap(ack));
	}

	private byte[] responsePacking(IoSession session) {
		byte[] ack = new byte[19];
		byte[] crc_c = new byte[4];
		ack[0] = -89;
		ack[1] = -72;
		ack[2] = 0;
		ack[3] = 1;
		ack[4] = 0;
		ack[5] = 0;
		ack[6] = 0;
		ack[7] = 19;
		ack[8] = 1;
		if (ParamInfoSendUtil.checkNeedAskParamInfo((String) session.getAttribute("patientId"))) {
			ack[9] = 4;
			Logger.writeLog("patientID" + session.getAttribute("patientId") + " has param data to send to stepcounter");
		} else {
			ack[9] = 1;
			Logger.writeLog(
					"patientID" + session.getAttribute("patientId") + " has no param data to send to stepcounter");
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		byte[] year_b = DataTypeChangeHelper.int2byte(year);
		byte[] month_b = DataTypeChangeHelper.int2byte(month);
		byte[] day_b = DataTypeChangeHelper.int2byte(day);
		byte[] hour_b = DataTypeChangeHelper.int2byte(hour);
		byte[] minute_b = DataTypeChangeHelper.int2byte(minute);
		byte[] second_b = DataTypeChangeHelper.int2byte(second);
		ack[10] = year_b[1];
		ack[11] = year_b[0];
		ack[12] = month_b[0];
		ack[13] = day_b[0];
		ack[14] = hour_b[0];
		ack[15] = minute_b[0];
		ack[16] = second_b[0];
		crc_c = MLinkCRC.crc16(ack);
		ack[17] = crc_c[0];
		ack[18] = crc_c[1];
		return ack;
	}
}
