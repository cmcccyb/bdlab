package com.cloud.mina.unit_a.bpstate;

import java.util.Calendar;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.bppackage.BPNo4SynsTimePackage;
import com.cloud.mina.util.ByteUtil;
import com.cloud.mina.util.MLinkCRC;

/**
 * UnitA血压时间同步数据处理状态类
 * 
 * @author Administrator
 *
 */
public class BPNo4SynsTimeState implements BloodPressurePacketHandleState {
	public boolean handlePacket(IoSession session, Object message) {
		log.info(this.getClass().getSimpleName() + ".handlePacket() begin...");
		if (message != null && message instanceof BPNo4SynsTimePackage) {
			responseToClient(session);
			log.info(this.getClass().getSimpleName() + ".handlePacket() end.");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param session
	 *            void
	 */
	private void responseToClient(IoSession session) {
		// server组装4号包的ACK并发送给设备
		Calendar c = Calendar.getInstance();//
		int year = c.get(Calendar.YEAR);

		byte[] reply = new byte[19];
		byte[] crc_c = new byte[4];
		reply[0] = -89;
		reply[1] = -72;
		reply[2] = 0;
		reply[3] = 1; // header
		reply[4] = 0;
		reply[5] = 0;
		reply[6] = 0;
		reply[7] = 19; // length
		reply[8] = 4;
		reply[9] = 0;// type

		// reply[10] = 7; reply[11] = -36; //year
		ByteUtil.putShortByLarge(reply, (short) year, 10);// year
		reply[12] = (byte) (c.get(Calendar.MONTH) + 1);// month
		reply[13] = (byte) c.get(Calendar.DATE);// day
		reply[14] = (byte) c.get(Calendar.HOUR_OF_DAY);// Hour
		reply[15] = (byte) c.get(Calendar.MINUTE);// Minute
		reply[16] = (byte) c.get(Calendar.SECOND);// Second
		// crc
		crc_c = MLinkCRC.crc16(reply);
		reply[17] = crc_c[0];
		reply[18] = crc_c[1];

		session.write(IoBuffer.wrap(reply));
	}
}
