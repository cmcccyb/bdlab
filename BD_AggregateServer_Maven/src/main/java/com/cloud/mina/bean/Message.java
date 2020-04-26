package com.cloud.mina.bean;

/**
 * 服务交互消息提示封装类
 * 
 * @author Administrator
 *
 */
public class Message {
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
