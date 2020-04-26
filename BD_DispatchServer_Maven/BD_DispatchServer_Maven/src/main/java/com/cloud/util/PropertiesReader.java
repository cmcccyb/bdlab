package com.cloud.util;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class PropertiesReader {
	private static Properties prop = new Properties();
	private static Properties prop_db = new Properties();
	static
	{
		try {
			InputStream SystemIn = new ClassPathResource("SysConf.properties").getInputStream();
			prop.load(SystemIn);
			InputStream db_config = new ClassPathResource("database.properties").getInputStream();
			prop_db.load(db_config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getProp(String name)
	{
		if(prop!=null)
		{
			return prop.get(name).toString();
		}
		return null;
	}
	public static String getDbProp(String name)
	{
		if(prop_db!=null)
		{
			return prop_db.get(name).toString();
		}
		return null;
	}
}
