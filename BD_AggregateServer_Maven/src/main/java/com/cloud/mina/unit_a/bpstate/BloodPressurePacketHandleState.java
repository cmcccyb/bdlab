package com.cloud.mina.unit_a.bpstate;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

/**
 * UnitA血压数据处理状态接口
 * 
 * @author Administrator
 *
 */
public interface BloodPressurePacketHandleState {
	public static Logger log = Logger.getLogger(BloodPressurePacketHandleState.class);

	public boolean handlePacket(IoSession session, Object message);
}
