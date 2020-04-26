
package com.cloud.mina.component.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import com.cloud.mina.unit_a.sportpackage.PackageData;

/**
 * mina的IOFilter自定义扩展类
 * 
 * @author changyaobin
 *
 */
public class ComponentIOFilter extends IoFilterAdapter {
	public Component component;

	public ComponentIOFilter(Component component) {
		super();
		this.component = component;
	}

	public ComponentIOFilter() {
		super();
	}

	// 数据接收转换核心方法
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {

		// 1.调用接口component实现字节流转为java对象 data =
		// component.getDataFromBuffer(ioBuffer);
		// 2.递归调用messageReceived，处理下一个设备 nextFilter.messageReceived(session,
		// data);
		packageHandle(nextFilter, session, message);
	}

	private void packageHandle(NextFilter nextFilter, IoSession session, Object message) {
		PackageData data = null;
		// 1.判断message是字节流还是JAVA对象PackageData
		// 比如：登录包被解析后，message换为LoginPacket，这个时候进入if(data ==
		// null)，此时的nextFilter是unitABPComponent，但是没有内容结束程序。
		if (message instanceof IoBuffer) {
			IoBuffer ioBuffer = (IoBuffer) message;
			ioBuffer.setAutoExpand(true);
			data = component.getDataFromBuffer(ioBuffer);
		}
		String appType = (String) session.getAttribute("appType");
		if (data == null) {
			// 2.Filter就是unitASportsComponent，unitABPComponent，因为unitABPComponent的nextfilter=null,结束程序.
			// 登录包过来后，IoFilterAdapter的messageReceived方法，执行nextFilter.messageReceived(session,
			// data)之后，递归进入packageHandle，在下面方法结束程序
			nextFilter.messageReceived(session, message);
		} else {
			nextFilter.messageReceived(session, data);
		}
	}
}
