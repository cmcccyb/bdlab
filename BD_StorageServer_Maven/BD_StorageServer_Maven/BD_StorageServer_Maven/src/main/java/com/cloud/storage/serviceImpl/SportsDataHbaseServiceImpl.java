package com.cloud.storage.serviceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloud.storage.base.Domain.HBaseColumn;
import com.cloud.storage.base.Domain.HQuery;
import com.cloud.storage.base.Domain.SportsData;
import com.cloud.storage.dao.SportsDataHbaseDao;
import com.cloud.storage.service.SportsDataHbaseService;
import com.cloud.storage.util.DateUtil;
import com.cloud.storage.util.ValidateUtil;

import net.sf.json.JSONArray;

@Service
public class SportsDataHbaseServiceImpl implements SportsDataHbaseService {
	private static Logger log = Logger.getLogger(SportsDataHbaseServiceImpl.class);
	@Autowired
	private SportsDataHbaseDao sportsDataHbaseDao;
	@Value("${sportTable}")
	private String sportTable;
	@Value("${patientTable}")
	private String patientTable;
	@Value("${patientFamily}")
	private String patientFamily;
	@Value("${sportFamily}")
	private String sportFamily;
	@Value("${qualifierPhone}")
	private String qualifierPhone;
	@Value("${qualifierDeviceId}")
	private String qualifierDeviceId;
	@Value("${qualifierCompany}")
	private String qualifierCompany;
	@Value("${qualifierAppType}")
	private String qualifierAppType;
	@Value("${qualifierDataType}")
	private String qualifierDataType;
	@Value("${qualifierDataValue}")
	private String qualifierDataValue;
	@Value("${qualifierReceiveDateTime}")
	private String qualifierReceiveDateTime;
	@Value("${qualifierPname}")
	private String qualifierPname;
	@Value("${qualifierTeamName}")
	private String qualifierTeamName;
	@Value("${flumeLogPath}")
	private String flumeLogPath;

	public boolean saveData(SportsData data) throws Exception {
		boolean saveSuccess = false;

		String dataType = data.getDataType();
		String appType = data.getAppType();
		String collectDate = data.getCollectDate();
		List<Map<String, String>> dataValue = data.getDataValue();
		String phone = data.getPhone();
		String deviceID = data.getDeviceID();


		if (ValidateUtil.paramCheck(appType, deviceID, collectDate, dataType, phone)) {
			if (ValidateUtil.paramCheck(phone)) {
				if ("stepCount".equals(dataType) || "stepDetail".equals(dataType)) {
					boolean isSuccessToPatient = insertHbaseDBForPatient(data, appType, phone, deviceID);
					boolean isSuccessToSports = insertHbaseDBForSports(dataType, appType, collectDate, dataValue, phone, deviceID);
					if(isSuccessToSports && isSuccessToPatient){
					HQuery query = new HQuery();
					query.setTable(sportTable);
					query.setFamily("data");
					query.setQualifier("phone");
					query.setQualifierValue(phone);
					List<SportsData> datas = sportsDataHbaseDao.selectByQuery(query);
					for (SportsData sportsData : datas) {
					}
					saveSuccess = true;
				}						
					}

			}
		}
		// 数据保存到hbase后，写入到日志文件中，供flume读取
		if (saveSuccess) {
			if ("stepCount".equals(dataType)) {
				String stepSum = "";
				String distanceSum = "";
				String calSum = "";
				if ("stepCount".equals(dataType)) {
					if (dataValue != null && dataValue.size() > 0) {
						Map<String, String> map = dataValue.get(0);

						for (Map<String, String> dataMap : dataValue) {
							if (dataMap.containsKey("stepSum")) {
								stepSum = dataMap.get("stepSum");
							} else if (dataMap.containsKey("distanceSum")) {
								distanceSum = dataMap.get("distanceSum");
							} else if (dataMap.containsKey("calSum")) {
								calSum = dataMap.get("calSum");
							}
						}
					}
				}
				// 数据日志信息
				String dataLog = data.getCollectDate() + " " + phone + " SOCKET/1.0 " + "bigData " + deviceID + " "
						+ stepSum + " " + distanceSum + " " + calSum;

				File file = new File(flumeLogPath);
				if (!file.exists()) {
					file.createNewFile();
				}

				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
				fileWriter.write(dataLog);
				fileWriter.newLine();
				fileWriter.close();
			}
		}

		return saveSuccess;

	}

	private boolean insertHbaseDBForSports(String dataType, String appType, String collectDate,
			List<Map<String, String>> dataValue, String phone, String deviceID, String ...value) {
		boolean isFinish = false;
		try {
			
			HQuery hquery = new HQuery();
			String stringDate = DateUtil.dateToString(collectDate);
			long time = DateUtil.getTime(stringDate);
			long Datetime = Long.MAX_VALUE - time;
			String rowkey = phone + "_" + Datetime + "_" + appType + "_" + dataType + DateUtil.getCurrentTime();
			hquery.setRow(rowkey);
			hquery.setTable(sportTable);
			List<HBaseColumn> columns = hquery.getColumns();
			columns.add(new HBaseColumn(sportFamily, qualifierPhone, phone));
			columns.add(new HBaseColumn(sportFamily, qualifierDeviceId, deviceID));
			columns.add(new HBaseColumn(sportFamily, qualifierCompany, "bigData"));
			columns.add(new HBaseColumn(sportFamily, qualifierAppType, appType));
			columns.add(new HBaseColumn(sportFamily, qualifierDataType, dataType));
			columns.add(new HBaseColumn(sportFamily, qualifierDataValue,
					JSONArray.fromObject(dataValue).toString()));
			columns.add(new HBaseColumn(sportFamily, qualifierReceiveDateTime, collectDate.toString()));
			columns.add(new HBaseColumn(sportFamily, "id", rowkey));
			sportsDataHbaseDao.addSportsData(hquery);
			isFinish = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("insert hbase in sports not successful!");
		}
		return isFinish;

	}

	private boolean insertHbaseDBForPatient(SportsData data, String appType, String phone, String deviceID) {
		boolean isFinish = false;
		try {
			HQuery patienQuery = new HQuery();
			List<HBaseColumn> infoColums = patienQuery.getColumns();
			String patientRowkey = phone + "_" + deviceID + "_" + appType;
			patienQuery.setRow(patientRowkey);
			patienQuery.setTable(patientTable);
			infoColums.add(new HBaseColumn(patientFamily, qualifierPhone, data.getPhone()));
			infoColums.add(new HBaseColumn(patientFamily, qualifierPname, data.getPname()));
			infoColums.add(new HBaseColumn(patientFamily, qualifierPname, data.getPname()));
			infoColums.add(new HBaseColumn(patientFamily, qualifierTeamName, data.getTeamName()));
			infoColums.add(new HBaseColumn(patientFamily, qualifierCompany, data.getCompany()));
			infoColums.add(new HBaseColumn(patientFamily, qualifierAppType, appType));
			sportsDataHbaseDao.addSportsData(patienQuery);
			isFinish = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("insert hbase in patient not successful!");
		}
		return isFinish;
	}
}
