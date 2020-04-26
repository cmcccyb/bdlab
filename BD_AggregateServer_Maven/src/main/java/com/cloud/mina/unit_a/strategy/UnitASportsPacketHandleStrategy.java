package com.cloud.mina.unit_a.strategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.PackageData;
import com.cloud.mina.unit_a.sportstate.SportLogoutState;
import com.cloud.mina.unit_a.sportstate.SportNo1LoginState;
import com.cloud.mina.unit_a.sportstate.SportNo8OneWayState;
import com.cloud.mina.unit_a.sportstate.SportNo8ThreeWayState;
import com.cloud.mina.unit_a.sportstate.SportNo8TwoWayState;
import com.cloud.mina.unit_a.sportstate.SportsPacketHandleState;

/**
 * unitA公司运动数据处理策略类（策略模式） UnitASportsPacketHandleStrategy就是Context（状态模式）
 */
public class UnitASportsPacketHandleStrategy implements MHDataPacketHandleStrategy {
	static Map<String, Class> classMap = new HashMap<String, Class>();

	// 定义变量区域
	static {
		classMap.put("login", SportNo1LoginState.class);
		classMap.put("logout", SportLogoutState.class);
		classMap.put("No8-1", SportNo8OneWayState.class);
		classMap.put("No8-2", SportNo8TwoWayState.class);
		classMap.put("No8-3", SportNo8ThreeWayState.class);

	}

	SportsPacketHandleState state = null;

	public void setState(SportsPacketHandleState state) {
		this.state = state;
	}

	public void handle(IoSession session, Object message) {
		// TODO Auto-generated method stub
		// 根据数据包的头，调用具体的状态类
		if (message != null && message instanceof PackageData) {
			PackageData packageData = (PackageData) message;

			try {
				setState((SportsPacketHandleState) classMap.get(packageData.getType()).newInstance());
				state.handlePacket(session, message);

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
