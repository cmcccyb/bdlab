package com.cloud.mina.util;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

/**
 * 读取配置文件流的工具类
 * 
 * @author Administrator
 *
 */
public class PropertiesReader {

	private static Properties prop = new Properties();

	static {
		try {
			InputStream SystemIn = new ClassPathResource("SysConf.properties").getInputStream();
			prop.load(SystemIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProp(String name) {
		if (prop != null && prop.containsKey(name)) {
			return prop.getProperty(name, "");
		} else {
			return "";
		}

	}
}
