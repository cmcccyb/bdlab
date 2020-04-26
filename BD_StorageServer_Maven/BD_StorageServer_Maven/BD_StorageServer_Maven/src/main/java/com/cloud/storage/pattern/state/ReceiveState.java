package com.cloud.storage.pattern.state;

import java.util.HashMap;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.util.JsonUtil;
import com.cloud.storage.util.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 接收状态（状态模式）
 * 
 * @author Administrator
 *
 */
public class ReceiveState extends State {
	@Override
	public void handle(Context context) {
		Log.getLogger().i("now is in receive state");
		String params = context.getRequest().getParameter("data");
		JSONObject jo = JSONObject.fromObject(params);
		String phone = JsonUtil.getJsonParamterString(jo, "phone");
		String appType = JsonUtil.getJsonParamterString(jo, "appType");
		String dataType = JsonUtil.getJsonParamterString(jo, "dataType");
		String pname = JsonUtil.getJsonParamterString(jo, "pname");
		String teamName = JsonUtil.getJsonParamterString(jo, "teamName");
		String company = JsonUtil.getJsonParamterString(jo, "company");
		SportsData data = new SportsData();
		data.setAppType(appType);
		data.setDataType(dataType);
		data.setPhone(phone);
		data.setPname(pname);
		data.setCompany(company);
		data.setTeamName(teamName);
		data.setCollectDate(JsonUtil.getJsonParamterString(jo, "collectDate"));
		data.setDataValue(JSONArray.toList(JSONArray.fromObject(jo.get( "dataValue")), HashMap.class));
		Log.getLogger().i("receive data : " + data.toString());
		context.setData(data);
		context.setState(new SaveState());
		context.request();
	}

}
