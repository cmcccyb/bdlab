package com.cloud.storage.util;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @version
 */

public class Log {
	private static Logger logger = Logger.getLogger(Log.class);

	private static Log instance = null;

	public static synchronized Log getLogger() {
		if (instance == null)
			instance = new Log();
		return instance;
	}

	public static void info(String message) {
		logger.info(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public void i(String message) {
		logger.info(message);
	}

	public void d(String message) {
		logger.debug(message);
	}

	public void e(String message) {
		logger.error(message);
	}
}
