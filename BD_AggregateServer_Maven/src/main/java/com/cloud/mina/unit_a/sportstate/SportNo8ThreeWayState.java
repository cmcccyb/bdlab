
package com.cloud.mina.unit_a.sportstate;

import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.No8ThreeWayPacket;
import com.cloud.mina.util.SaveSportsNo8PacketUtil;

/**
 * unitA公司运动数据包（3号包）状态处理类
 * 
 * @author Administrator
 *
 */
public class SportNo8ThreeWayState implements SportsPacketHandleState {
	public boolean handlePacket(IoSession session, Object message) {
		No8ThreeWayPacket packet = null;
		if (message != null && message instanceof No8ThreeWayPacket) {
			packet = (No8ThreeWayPacket) message;
			if (packet.getPatientID() != null && !"".equals(packet.getPatientID())) {
				session.setAttribute("patientId", packet.getPatientID());
				session.setAttribute("deviceId", packet.getDeviceID());
				session.setAttribute("company", packet.getCompany());
			}
			boolean result = false;

			result = SaveSportsNo8PacketUtil.saveNewSportSimple(session, packet);

			if (result) {
				SaveSportsNo8PacketUtil.sendNo8Ack(session, result, 3);
			}

			return true;
		}
		return false;
	}
}
