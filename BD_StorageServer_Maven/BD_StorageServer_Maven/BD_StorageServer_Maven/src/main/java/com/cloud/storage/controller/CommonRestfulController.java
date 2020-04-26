package com.cloud.storage.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.pattern.state.Context;
import com.cloud.storage.service.SportsDataHbaseService;
import com.cloud.storage.service.ObservationService;
import com.cloud.storage.service.PatientService;
import com.cloud.storage.service.SportsDataService;
import com.cloud.storage.util.DateUtil;
import com.cloud.storage.util.JsonUtil;
import com.cloud.storage.util.PropertiesReader;
import com.cloud.storage.util.ResponseUtil;
import com.cloud.storage.util.ValidateUtil;

import net.sf.json.JSONObject;

/**
 * 数据接收接口，与DispatchServer转发服务进行数据对接
 * 
 * @author Administrator
 *
 */
@Controller
public class CommonRestfulController {

	@Autowired
	private ObservationService observationService;

	@Autowired
	private SportsDataService sportsDataService;
	@Autowired
	private SportsDataHbaseService sportsDataHbaseService;
	@Autowired
	private PatientService patientService;

	private static Logger log = Logger.getLogger(CommonRestfulController.class);

	/**
	 * 数据采集接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/businessDataReceive")
	public void businessDataReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("the start of businessDataReceive ");
		Map result = new HashMap();
		log.info("收到网关DispatchServer发来数据*_*... \r\n");
		String jsonData = "";
		try {
			jsonData = new String((request.getParameter("data").getBytes("iso-8859-1")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("receive data occur exception:" + e.getMessage());
		}
		JSONObject jo = JSONObject.fromObject(jsonData);
		// 数据参数校验
		String validateInfo = "" + ValidateUtil.checkAppType(JsonUtil.getJsonParamterString(jo, "appType"))
				+ (ValidateUtil.isValid(JsonUtil.getJsonParamterString(jo, "dataType")) == true ? "" : "false")
				+ ValidateUtil.checkDateTime(JsonUtil.getJsonParamterString(jo, "collectDate"))
				+ (ValidateUtil.isValid(JsonUtil.getJsonParamterString(jo, "phone")) == true ? "" : "false");
		// 校验通过
		if ("".equals(validateInfo)) {
			String isMongo = PropertiesReader.getProp("mongodb");
			String isMysql = PropertiesReader.getProp("mysql");
			String isHbase = PropertiesReader.getProp("hbase");
			System.out.println("isMongo-----------------------------------" + isMongo);
			
			Map<String,Class>classMap=new HashMap<>();
			
			classMap.put("dataValue", HashMap.class);
			// 入库mongodbJSONObject
			SportsData sportsData = (SportsData) JSONObject.toBean(JSONObject.fromObject(jsonData),
					SportsData.class,classMap);
			if ("true".equals(isMongo)) {
				// 入库mongodb
				sportsDataService.saveSportsData(sportsData);
			} else if ("true".equals(isMysql)) {
				// 入库mysql
				new Context(request, response, observationService,patientService).request();
			} else if ("true".equals(isHbase)) {
				// 入Hbase库
				sportsDataHbaseService.saveData(sportsData);
			}
		} else {
			response.setStatus(412);
			result.put("status", "数据验证失败！" + validateInfo);
			log.info("the end of businessDataReceive has invalidate param include " + validateInfo);
		}
		response.setStatus(200);
		ResponseUtil.writeInfo(response, JSONObject.fromObject(result).toString());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/businessDataInsert/{uniqueField}/{dateTime}/{businessType}/{appType}", params = { "param" })
	public ModelMap businessDataInsert(@PathVariable String uniqueField,
			@PathVariable String dateTime, @PathVariable String businessType,
			@PathVariable String appType, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		Map result = new HashMap();

			String params = request.getParameter("param");
			 flag = this.observationService.insertOrUpdateData(uniqueField,DateUtil.getCurrentTime(), businessType, appType, params,DateUtil.getCurrentTime());

			if (flag) {
				result.put("status", "success");
			} else {
				result.put("status", "数据插入失败！");
			}
		return new ModelMap(result);
	}
}
