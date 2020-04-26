package com.cloud.mina.component.filter;

import org.apache.mina.core.buffer.IoBuffer;

import com.cloud.mina.unit_a.sportpackage.PackageData;
/**
 * 业务树解码器的根类
 * @author Administrator
 *
 */
public class MHRootComponent extends PacketFilterComponent {

	public PackageData generateRealPackageData(IoBuffer buffer) {
		return null;
	}

	@Override
	public boolean check(IoBuffer buffer) {
		return false;
	}
}
