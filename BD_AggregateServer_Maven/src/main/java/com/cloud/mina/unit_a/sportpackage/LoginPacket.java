
package com.cloud.mina.unit_a.sportpackage;

/**
 * UnitA运动登录数据包
 * 
 * @author Administrator
 *
 */
public class LoginPacket extends PackageData {
	private String loginTime = "";

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
}
