package com.cloud.storage.pattern.state;

/**
 * 状态抽象类（状态模式）
 * 
 * @author Administrator
 *
 */
public abstract class State {
	// 数据处理方法，Context为参数封装类
	public abstract void handle(Context context);
}
