package com.cloud.storage.pattern.state;

import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.base.Domain.Patient;
import com.cloud.storage.pattern.factory.StrategyFactory;
import com.cloud.storage.pattern.strategy.Strategy;
import com.cloud.storage.util.Log;

/**
 * 保存状态（状态模式）
 * 
 * @author Administrator
 *
 */
public class SaveState extends State {

	@Override
	public void handle(Context context) {
		
		SportsData data = context.getData();
		Patient patient=new Patient();
		patient.setPhone(data.getPhone());
		patient.setDeviceId(data.getDeviceID());
		patient.setName(data.getPname());
		patient.setAppType(data.getAppType());
		boolean savePatient2MysqlSuccess = context.getPatientService().savePatient2Mysql(patient);
		if(savePatient2MysqlSuccess){
			Log.getLogger().i("now begin to save data!");
			Strategy st = new StrategyFactory(context.getRequest().getSession().getServletContext(),
					context.getData().getAppType()).getInstance();
			st.dealData(context);
		}else{
			Log.getLogger().error("save patient data fail");
		}
	}

}
