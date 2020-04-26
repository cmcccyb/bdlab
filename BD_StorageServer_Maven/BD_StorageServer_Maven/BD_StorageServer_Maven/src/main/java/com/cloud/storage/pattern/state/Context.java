package com.cloud.storage.pattern.state;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.service.ObservationService;
import com.cloud.storage.service.PatientService;

/**
 * 多个参数的封装类
 * 
 * @author Administrator
 *
 */
public class Context {

	private State state;
	private SportsData data;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ObservationService observationService;
	private PatientService patientService;

	public Context() {
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param transportService
	 * @param observationService
	 */
	public Context(HttpServletRequest request, HttpServletResponse response, ObservationService observationService,PatientService patientService) {
		this.request = request;
		this.response = response;
		this.observationService = observationService;
		this.patientService=patientService;
		this.state = new ReceiveState();// 数据刚接收进来，初始化为接收状态
	}

	// 数据处理
	public void request() {
		state.handle(this);
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	
	public SportsData getData() {
		return data;
	}

	public void setData(SportsData data) {
		this.data = data;
	}

	public void setObservationService(ObservationService observationService) {
		this.observationService = observationService;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the observationService
	 */
	public ObservationService getObservationService() {
		return observationService;
	}

	public PatientService getPatientService() {
		return patientService;
	}

	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

}
