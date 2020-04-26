/**
 */
package com.cloud.mina.unit_a.bpstate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.bppackage.BPNo8DataPackage;
import com.cloud.mina.util.C3P0Util;
import com.cloud.mina.util.Constants;
import com.cloud.mina.util.DateUtil;
import com.cloud.mina.util.MLinkCRC;

/**
 * UnitA血压业务数据处理状态类
 * 
 * @author Administrator
 *
 */
public class BPNo8DataState implements BloodPressurePacketHandleState {
	public boolean handlePacket(IoSession session, Object message) {
		log.info(this.getClass().getSimpleName() + ".handlePacket() begin...");
		BPNo8DataPackage packet = null;
		boolean result = false;
		if (message != null && message instanceof BPNo8DataPackage) {
			packet = (BPNo8DataPackage) message;
			result = saveBPData(session, packet);
			// 回复ACK
			responseToClient(session, result);
			log.info(this.getClass().getSimpleName() + ".handlePacket() end.");
			return true;
		}
		return false;
	}

	private boolean saveBPData(IoSession session, BPNo8DataPackage packet) {
		JSONArray dataValue = new JSONArray();
		JSONObject measureTime = new JSONObject();
		JSONObject heartrate = new JSONObject();
		JSONObject systolic = new JSONObject();
		JSONObject diastolic = new JSONObject();
		measureTime.put("measureTime", packet.getMeasureTime());
		heartrate.put("heartrate", packet.getHeartrate());
		systolic.put("systolic", packet.getHighpressure());
		diastolic.put("diastolic", packet.getLowpressure());
		dataValue.add(measureTime);
		dataValue.add(heartrate);
		dataValue.add(systolic);
		dataValue.add(diastolic);

		StringBuffer sql = new StringBuffer(
				"insert into bloodpressure(phone,deviceID,dataType,appType,company,receiveTime,realTime,dataValue) ");
		sql.append("values('").append(packet.getPatientID()).append("','").append(packet.getDeviceID()).append("','")
				.append(Constants.DATATYPE_BLOODPRESSURE).append("','").append("").append("','")
				.append(packet.getCompany()).append("','").append(DateUtil.getCurrentTime()).append("','")
				.append(packet.getMeasureTime()).append("','").append(dataValue.toString()).append("')");

		boolean result = C3P0Util.executeUpdate(sql.toString());
		if (result) {
			log.info("insert bloodpressure data success : " + sql.toString());
			/**
			 * 通知观察者启动发送线程
			 */
			// SubjectFactory.getSubject(packet.getAppType(),
			// Constants.DATATYPE_BLOODPRESSURE).notifyObserver();
		} else {
			log.info("!!!! insert bloodpressure data failure : " + sql.toString());
		}

		return result;
	}

	private void responseToClient(IoSession session, boolean isSuccess) {
		byte[] reply = new byte[12];
		reply[0] = -89;
		reply[1] = -72;
		reply[2] = 0;
		reply[3] = 1; // header
		reply[4] = 0;
		reply[5] = 0;
		reply[6] = 0;
		reply[7] = 12; // length
		reply[8] = 8; // type
		if (isSuccess)
			reply[9] = 14; // result
		else
			reply[9] = 15;
		// crc
		byte[] crc_c = new byte[2];
		crc_c = MLinkCRC.crc16(reply);
		reply[10] = crc_c[0];
		reply[11] = crc_c[1];

		session.write(IoBuffer.wrap(reply));
	}
}
