
package com.cloud.mina.unit_a.strategy;

import org.apache.mina.core.session.IoSession;

/**
 * UnitA公司业务处理策略接口
 * 
 * @author Administrator
 *
 */
public interface MHDataPacketHandleStrategy {
	//UnitA数据处理方法
	public void handle(IoSession session, Object message);
}
