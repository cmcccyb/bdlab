
package com.cloud.mina.unit_a.bppackage;

import com.cloud.mina.unit_a.sportpackage.PackageData;

/**
 * UnitA公司血压业务数据包
 * 
 * @author Administrator
 *
 */
public class BPNo8DataPackage extends PackageData {
	private String measureTime; // 测量时间
	private String highpressure; // 高压
	private String lowpressure; // 低压
	private String heartrate; // 心率
	private String meanpressure; // 平均动脉压

	public String getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(String measureTime) {
		this.measureTime = measureTime;
	}

	public String getHighpressure() {
		return highpressure;
	}

	public void setHighpressure(String highpressure) {
		this.highpressure = highpressure;
	}

	public String getLowpressure() {
		return lowpressure;
	}

	public void setLowpressure(String lowpressure) {
		this.lowpressure = lowpressure;
	}

	public String getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}

	public String getMeanpressure() {
		return meanpressure;
	}

	public void setMeanpressure(String meanpressure) {
		this.meanpressure = meanpressure;
	}
}