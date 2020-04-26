
package com.cloud.mina.component.unit_a.bp;

import org.apache.mina.core.buffer.IoBuffer;

import com.cloud.mina.component.filter.UnitABPComponent;
import com.cloud.mina.unit_a.bppackage.BPNo4SynsTimePackage;
import com.cloud.mina.unit_a.sportpackage.PackageData;

/**
 * unitA公司的血压同步时间解码器
 * 
 * @author Administrator
 *
 */
public class BPNo4SynsTimeParer extends UnitABPComponent {
	@Override
	public boolean check(IoBuffer buffer) {
		if (buffer.get(8) == 4 && buffer.get(9) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public PackageData generateRealPackageData(IoBuffer buffer) {
		log.info(this.getClass().getSimpleName() + ".generateRealPackageData() begin...");
		BPNo4SynsTimePackage data = new BPNo4SynsTimePackage();
		data.setName("bloodpressure");
		data.setType("no4");
		log.info(this.getClass().getSimpleName() + ".generateRealPackageData() end.");
		return data;
	}
}
