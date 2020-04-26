package com.cloud.storage.pattern.strategy;

import org.springframework.stereotype.Component;

import com.cloud.storage.pattern.chain.Handler;
import com.cloud.storage.pattern.chain.StepCountChainHandler;
import com.cloud.storage.pattern.chain.StepDetailChainHandler;
import com.cloud.storage.pattern.chain.StepEffectiveChainHandler;
import com.cloud.storage.pattern.state.Context;
import com.cloud.storage.util.Log;

/**
 * unitA公司的AppA业务数据处理策略类
 * 
 * @author Administrator
 *
 */
@Component("AppAdatabean")
public class AppAtrategy extends Strategy {
	@Override
	public boolean dealData(Context context) {
		Log.getLogger().d("Strategy data start save in db !");
		Handler newStepCountChainHandler = new StepCountChainHandler();
		Handler newStepDetailChainHandler = new StepDetailChainHandler();
		Handler newstepEffectiveChainHandler = new StepEffectiveChainHandler();

		newStepCountChainHandler.setSuccessor(newStepDetailChainHandler);
		newStepDetailChainHandler.setSuccessor(newstepEffectiveChainHandler);
		return newStepCountChainHandler.HandleRequest(context);
	}
}
