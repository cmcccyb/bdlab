package com.cloud.storage.pattern.factory;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloud.storage.pattern.strategy.Strategy;

/**
 * 策略工厂，根据不同的AppType来生产对应的策略处理类
 * 
 * @author Administrator
 *
 */
public class StrategyFactory {

	private Strategy strategy;

	public StrategyFactory(ServletContext sc, String appType) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		strategy = (Strategy) ctx.getBean(appType + "databean");
	}

	public Strategy getInstance() {
		return strategy;
	}
}
