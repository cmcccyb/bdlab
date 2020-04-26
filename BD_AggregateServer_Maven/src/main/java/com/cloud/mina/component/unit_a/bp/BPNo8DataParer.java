
package com.cloud.mina.component.unit_a.bp;

import org.apache.mina.core.buffer.IoBuffer;

import com.cloud.mina.component.filter.UnitABPComponent;
import com.cloud.mina.unit_a.bppackage.BPNo8DataPackage;
import com.cloud.mina.unit_a.sportpackage.PackageData;
import com.cloud.mina.util.ByteUtil;
import com.cloud.mina.util.DataTypeChangeHelper;
import com.cloud.mina.util.DateUtil;
import com.cloud.mina.util.DeviceIDResolver;

/**
 * unitA公司的血压数据解码器
 * 
 * @author Administrator
 *
 */
public class BPNo8DataParer extends UnitABPComponent {
	@Override
	public boolean check(IoBuffer buffer) {
		// 此协议与计步器8号包方式4相同，需要根据包长度进一步区分:血压计8号包47个字节；
		if (buffer.get(8) == 8 && buffer.get(9) == 4) {
			byte[] length = new byte[4];
			length[0] = buffer.get(4);
			length[1] = buffer.get(5);
			length[2] = buffer.get(6);
			length[3] = buffer.get(7);
			long len = DataTypeChangeHelper.unsigned4BytesToInt(length, 0);
			if (len == 47)
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public PackageData generateRealPackageData(IoBuffer buffer) {
		log.info(this.getClass().getSimpleName() + ".generateRealPackageData() begin...");
		BPNo8DataPackage data = new BPNo8DataPackage();
		data.setName("bloodpressure");
		data.setType("no8");
		byte[] year_b = new byte[2];
		byte[] sp_b = new byte[2]; // 高压（收缩压）
		byte[] dp_b = new byte[2]; // 低压（舒张压）
		byte[] mp_b = new byte[2]; // 平均动脉压
		byte[] hr_b = new byte[2]; // 心率
		year_b[0] = buffer.get(26);
		year_b[1] = buffer.get(27);

		int year = ByteUtil.getShortByLargePattern(year_b, 0);
		int month = buffer.get(28);
		int day = buffer.get(29);
		int hour = buffer.get(30);
		int min = buffer.get(31);
		int second = buffer.get(32);
		sp_b[0] = buffer.get(33);
		sp_b[1] = buffer.get(34);
		dp_b[0] = buffer.get(35);
		dp_b[1] = buffer.get(36);
		mp_b[0] = buffer.get(37);
		mp_b[1] = buffer.get(38);
		hr_b[0] = buffer.get(39);
		hr_b[1] = buffer.get(40);
		int sp = ByteUtil.getShortByLargePattern(sp_b, 0);
		int dp = ByteUtil.getShortByLargePattern(dp_b, 0);
		int mp = ByteUtil.getShortByLargePattern(mp_b, 0);
		int hr = ByteUtil.getShortByLargePattern(hr_b, 0);

		StringBuffer sbtime = new StringBuffer();
		sbtime.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":")
				.append(min).append(":").append(second);
		String time = DateUtil.getCompactDatetime(sbtime.toString());
		System.out.println("measureTime : " + time);
		String deviceId = DeviceIDResolver.getDeviceIDFromBytes(buffer.array(), 10);
		data.setMeasureTime(time);
		data.setHeartrate(String.valueOf(hr));
		data.setHighpressure(String.valueOf(sp));
		data.setLowpressure(String.valueOf(dp));
		data.setMeanpressure(String.valueOf(mp));
		data.setDeviceID(deviceId);
		data.setPatientID(DeviceIDResolver.searchPatientidByDeviceid(deviceId));
		data.setCompany(DeviceIDResolver.searchCompanyByDeviceid(deviceId));
		data.setAppType(DeviceIDResolver.searchAppTypeByDeviced(data.getDeviceID()));
		log.info(this.getClass().getSimpleName() + ".generateRealPackageData() end.");
		return data;
	}
}
