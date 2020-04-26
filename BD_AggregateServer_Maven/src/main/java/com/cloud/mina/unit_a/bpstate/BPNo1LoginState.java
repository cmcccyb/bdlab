package com.cloud.mina.unit_a.bpstate;

import java.util.Calendar;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.bppackage.BPNo1LoginPacket;
import com.cloud.mina.util.ByteUtil;
import com.cloud.mina.util.MLinkCRC;

/**
 * UnitA血压登录数据处理状态类
 * 
 * @author Administrator
 *
 */
public class BPNo1LoginState implements BloodPressurePacketHandleState {

	/**
	 * @param session
	 *            void
	 */
	private void responseToClient(IoSession session, boolean isFound) {
		byte[] reply = new byte[19];
		reply[0] = -89;
		reply[1] = -72;
		reply[2] = 0;
		reply[3] = 2; // header
		reply[4] = 0;
		reply[5] = 0;
		reply[6] = 0;
		reply[7] = 19; // length
		reply[8] = 1; // type
		if (isFound)
			reply[9] = 1; // type 1登陆成功,5密码错误
		else
			reply[9] = 5;
		Calendar c = Calendar.getInstance();//
		int year = c.get(Calendar.YEAR);
		ByteUtil.putShortByLarge(reply, (short) year, 10);// year
		reply[12] = (byte) (c.get(Calendar.MONTH) + 1);// month
		reply[13] = (byte) c.get(Calendar.DATE);// day
		reply[14] = (byte) c.get(Calendar.HOUR_OF_DAY);// Hour
		reply[15] = (byte) c.get(Calendar.MINUTE);// Minute
		reply[16] = (byte) c.get(Calendar.SECOND);// Second
		// *****************************************wy
		// crc
		byte[] crc_c = new byte[2];
		crc_c = MLinkCRC.crc16(reply);
		reply[17] = crc_c[0]; // 未知
		reply[18] = crc_c[1];

		session.write(IoBuffer.wrap(reply));
	}

	public boolean handlePacket(IoSession session, Object message) {
		log.info(this.getClass().getSimpleName() + ".handlePacket() begin...");
		BPNo1LoginPacket packet = null;
		if (message != null && message instanceof BPNo1LoginPacket) {
			packet = (BPNo1LoginPacket) message;
			// 设备ID校验失败
			if (packet.getPatientID() == null || "".equals(packet.getPatientID().trim())) {
				// 回复NAK
				responseToClient(session, false);
				return false;
			}
			session.setAttribute("patientId", packet.getPatientID());
			session.setAttribute("deviceId", packet.getDeviceID());
			session.setAttribute("password", packet.getPassword());
			// 回复ACK
			responseToClient(session, true);
			log.info(this.getClass().getSimpleName() + ".handlePacket() end.");
			return true;
		} else {
			return false;
		}
	}

}
