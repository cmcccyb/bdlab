package com.cloud.storage.base.Domain;

/**
 * 服务对接消息JavaBean
 * 
 * @author Administrator
 *
 */
public class Message {
	// 业务状态码
	private int code;
	// 操作后提示信息
	private String message;
	// 操作返回数据
	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
