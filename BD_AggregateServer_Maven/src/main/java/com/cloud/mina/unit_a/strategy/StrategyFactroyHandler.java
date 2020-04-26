package com.cloud.mina.unit_a.strategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.web.client.RestTemplate;

import com.cloud.mina.bean.Message;
import com.cloud.mina.unit_a.sportpackage.PackageData;
import com.cloud.mina.util.Log;

/**
 * mina的Iohandler自定义扩展类
 * 
 * @author Administrator
 *
 */
public class StrategyFactroyHandler extends IoHandlerAdapter {
	// 定义变量区域
	public MHDataPacketHandleStrategy chain = null;
	PackageData packet = null;
	// springCloud消息转发模板
	private RestTemplate restTemplate;
	static Map<String, Class> classMap = new HashMap<String, Class>();

	static {
		/**
		 * 不同厂家就是不同的策略，unitA的sport/BP 通过数据包的名字来匹配类名，如sports和bloodpressure
		 */
		classMap.put("sports", UnitASportsPacketHandleStrategy.class);
		classMap.put("bloodpressure", UnitABloodPressurePacketHandleStrategy.class);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		// 1.参数验证
		if (message != null && message instanceof PackageData) {
			packet = (PackageData) message;
			// 2. 调用具体设备处理
			chain = (MHDataPacketHandleStrategy) classMap.get(packet.getName()).newInstance();
			if (chain != null) {
				chain.handle(session, message);
			}
			// 发送给转发服务
			try {
				Message responseMes = restTemplate.getForObject("http://BOOT-DISPATCH/sendData?appType= "
						+ packet.getAppType() + "&dataType=" + packet.getType(), Message.class);
				if (responseMes != null && responseMes.getCode() == 1001) {
					// 发送成功
					Log.info("发送数据成功");
				} else {
					Log.error("发送数据失败");
				}
			} catch (Exception e) {
				Log.error("连接转发服务异常,转发服务地址为http://BOOT-DISPATCH");
			}

		}
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
