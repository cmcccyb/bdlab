package com.cloud.mina.unit_a.sportstate;

import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.No8OneWayPacket;
import com.cloud.mina.util.SaveSportsNo8PacketUtil;

/**
 * unitA公司运动数据包（1号包）状态处理类
 * 
 * @author Administrator
 *
 */
public class SportNo8OneWayState implements SportsPacketHandleState {

	public boolean handlePacket(IoSession session, Object message) {
		// 定义变量
		No8OneWayPacket packet = null;

		// 1.验证参数
		if (message != null && message instanceof No8OneWayPacket) {
			packet = (No8OneWayPacket) message;
			session.setAttribute("patientId", packet.getPatientID());
			session.setAttribute("deviceId", packet.getDeviceID());
			session.setAttribute("company", packet.getCompany());
			// 2.数据包入库

			boolean result = SaveSportsNo8PacketUtil.saveNewSportHistory(session, packet);

			// 3.给设备应答
			if (result) {
				SaveSportsNo8PacketUtil.sendNo8Ack(session, result, 1);
			} else {
				System.out.println("数据保存失败！");
			}
		}
		return false;
	}
}
