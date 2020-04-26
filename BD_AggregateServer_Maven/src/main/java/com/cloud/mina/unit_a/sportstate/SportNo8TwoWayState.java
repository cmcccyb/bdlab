
package com.cloud.mina.unit_a.sportstate;

import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.No8TwoWayPacket;
import com.cloud.mina.util.PropertiesReader;
import com.cloud.mina.util.SaveSportsNo8PacketUtil;

/**
 * unitA公司运动数据包（2号包）状态处理类
 * 
 * @author Administrator
 *
 */
public class SportNo8TwoWayState implements SportsPacketHandleState {
	public boolean handlePacket(IoSession session, Object message) {
		No8TwoWayPacket packet = null;
		if (message != null && message instanceof No8TwoWayPacket) {
			packet = (No8TwoWayPacket) message;
			if (packet.getPatientID() != null && !"".equals(packet.getPatientID())) {
				session.setAttribute("patientId", packet.getPatientID());
				session.setAttribute("deviceId", packet.getDeviceID());
				session.setAttribute("company", packet.getCompany());
			}
			// 数据存储入库
			for (int i = 0; i < packet.getStepcount2data().size(); i++) {
				SaveSportsNo8PacketUtil.saveNewSportDetail(session, packet.getStepcount2data().get(i),
						packet.getStepdate().get(i));

			}
			SaveSportsNo8PacketUtil.sendNo8Ack(session, true, 2);
			return true;
		}
		return false;
	}
}
