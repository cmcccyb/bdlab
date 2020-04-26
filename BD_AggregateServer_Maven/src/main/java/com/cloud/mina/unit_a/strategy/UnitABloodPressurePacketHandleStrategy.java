
package com.cloud.mina.unit_a.strategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.bpstate.BPNo1LoginState;
import com.cloud.mina.unit_a.bpstate.BPNo4SynsTimeState;
import com.cloud.mina.unit_a.bpstate.BPNo8DataState;
import com.cloud.mina.unit_a.bpstate.BloodPressurePacketHandleState;
import com.cloud.mina.unit_a.sportpackage.PackageData;

/**
 * UnitA公司血压处理策略类
 * 
 * @author Administrator
 *
 */
public class UnitABloodPressurePacketHandleStrategy implements MHDataPacketHandleStrategy {
	private static Map<String, String> stateMap = new HashMap<String, String>();
	@SuppressWarnings("rawtypes")
	private static Map<String, Class> classMap = new HashMap<String, Class>();
	BloodPressurePacketHandleState state = null;

	public void setState(BloodPressurePacketHandleState state) {
		this.state = state;
	}

	static {

		classMap.put("login", BPNo1LoginState.class);
		classMap.put("no4", BPNo4SynsTimeState.class);
		classMap.put("no8", BPNo8DataState.class);
	}

	public void handle(IoSession session, Object message) {
		try {
			if (message != null && message instanceof PackageData) {
				PackageData packageData = (PackageData) message;
				setState((BloodPressurePacketHandleState) classMap.get(packageData.getType()).newInstance());
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (state != null) {
			state.handlePacket(session, message);
		}

	}

	public String getNextState(IoSession session, PackageData pack) {
		if (session.containsAttribute("currState") == false) {
			return "login";
		} else {
			return stateMap.get(session.getAttribute("currState"));
		}
	}

	public BloodPressurePacketHandleState getNextRealState(IoSession session, PackageData pack)
			throws InstantiationException, IllegalAccessException {
		BloodPressurePacketHandleState dataState = null;
		String state = getNextState(session, null);
		if (classMap.get(state) != null)
			dataState = (BloodPressurePacketHandleState) classMap.get(state).newInstance();

		return dataState;
	}
}
