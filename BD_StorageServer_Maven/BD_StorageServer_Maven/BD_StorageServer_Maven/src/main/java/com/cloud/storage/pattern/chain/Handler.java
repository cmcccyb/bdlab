package com.cloud.storage.pattern.chain;

import com.cloud.storage.pattern.state.Context;

/**
 * 责任抽象类（责任链模式）
 * 
 * @author Administrator
 *
 */
public abstract class Handler {
	protected Handler successor;

	/**
	 * @return the successor
	 */
	public Handler getSuccessor() {
		return successor;
	}

	/**
	 * @param successor
	 *            the successor to set
	 */
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}

	/**
	 * deal the request
	 */
	public abstract boolean HandleRequest(Context context);

}
