package com.cloud.storage.pattern.chain;

import java.util.HashMap;
import java.util.Map;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.pattern.state.Context;
import com.cloud.storage.util.Log;
import com.cloud.storage.util.PropertiesReader;

import net.sf.json.JSONObject;

/**
 * unitA 三号包业务处理责任类
 * 
 * @author Administrator
 *
 */
public class StepEffectiveChainHandler extends Handler {

	@Override
	public boolean HandleRequest(Context context) {
		String dataType = context.getData().getDataType();
		// 判断是否是有效步数
		if (PropertiesReader.getProp("DATATYPE_STEPEFFECTIVE").equalsIgnoreCase(dataType)) {
			SportsData data = context.getData();
			Log.getLogger().d("data deal by StepDetailChainHandler !");
			if (data.getDataValue() != null && data.getDataValue().size() != 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				String measureTime = data.getDataValue().get(7).get("measureTime");
				String hour = data.getDataValue().get(6).get( "hour");
				if (Integer.parseInt(hour) < 10 && measureTime.length() == 10) {
					hour = "0" + hour;
				}
				measureTime += (" " + hour + ":" + "00" + ":" + "00");
				map.put("stepEffective", data.getDataValue());
				// insert into database
				boolean bool = context.getObservationService().insertOrUpdateData(data.getPhone(), measureTime,
						PropertiesReader.getProp("DATATYPE_STEPEFFECTIVE"),PropertiesReader.getProp("APPTYPE_AppA"),
						JSONObject.fromObject(map).toString(), data.getCollectDate());
				if (!bool)
					Log.getLogger().e("StepEffectiveChainHandler save data into db error!");
				return bool;
			} else {
				Log.getLogger().e("StepEffectiveChainHandler datavalue is null!");
				return false;
			}
		} else {
			if (successor != null)
				return successor.HandleRequest(context);
			else
				return false;
		}
	}

}
