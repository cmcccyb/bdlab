
package com.cloud.mina.unit_a.sportstate;

import org.apache.mina.core.session.IoSession;

/**
 * unitA公司运动数据包状态接口
 * 
 * @author Administrator
 *
 */
public interface SportsPacketHandleState {
	/**
	 * 业务数据处理抽象方法定义
	 * 
	 * @param session
	 * @param message
	 * @return
	 */
	public boolean handlePacket(IoSession session, Object message);

}
