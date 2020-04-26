package com.cloud.strategy;

import org.apache.log4j.Logger;


public interface MessagePublisher {
	public static Logger log = Logger.getLogger(MessagePublisher.class);

	public boolean sendByQuene(String msg, String queneName);

}
