package com.cloud.mina.util;

import org.apache.log4j.PropertyConfigurator;

public class Log {
	static org.apache.log4j.Logger logger = null;

	static {
		PropertyConfigurator.configure("d:\\log\\log4j.properties");
		logger = org.apache.log4j.Logger.getLogger(Log.class);
	}

	/**
	 * write debug log
	 * 
	 * @param log
	 */
	public static void debug(String log) {
		logger.debug(log);
	}

	/**
	 * write info log
	 * 
	 * @param log
	 */
	public static void info(String log) {
		logger.info(log);
	}

	/**
	 * write error log
	 * 
	 * @param log
	 */
	public static void error(String log) {
		logger.error(log);
	}
}
