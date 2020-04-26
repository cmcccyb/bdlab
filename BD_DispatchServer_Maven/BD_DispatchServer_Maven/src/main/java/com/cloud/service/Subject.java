package com.cloud.service;

import java.util.ArrayList;
import java.util.List;


public abstract class Subject {
	public List<Observer> observers = new ArrayList<Observer>();
	protected String dataType;
	protected String appType;

	public void add(Observer observer) {
		this.observers.add(observer);
	};

	public void del(Observer observer) {

	};

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	};

}
