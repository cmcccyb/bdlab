package com.cloud.storage.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.storage.base.Domain.Message;
import com.cloud.storage.base.Domain.Patient;
import com.cloud.storage.base.Domain.PhysicalCheckData;
import com.cloud.storage.base.Domain.PhysicalCheckData.GeneralProjecr;
import com.cloud.storage.base.Domain.PhysicalCheckData.Medical;
import com.cloud.storage.base.Domain.PhysicalCheckData.RoutineBlood;
import com.cloud.storage.base.Domain.PhysicalCheckData.Surgery;
import com.cloud.storage.service.PatientService;
import com.cloud.storage.service.PhysicalCheckDataService;

import net.sf.json.JSONObject;

/**
 * 体检信息Controller层
 * 
 * @author Administrator
 *
 */
@RestController
public class PhysicalCheckDataController {
	@Autowired
	private PhysicalCheckDataService physicalCheckDataService;
	@Autowired
	private PatientService patientService;

	@RequestMapping("receivePhyCheckData")
	public Message receivePhyCheckData(String data) {
		Message message = new Message();
		if (StringUtils.isNotBlank(data)) {
			try {
				PhysicalCheckData physicalCheckData = (com.cloud.storage.base.Domain.PhysicalCheckData) JSONObject
						.toBean(JSONObject.fromObject(data), PhysicalCheckData.class);
				if (physicalCheckData != null) {
					if (physicalCheckData.getPatientId() != null
							|| StringUtils.isBlank(physicalCheckData.getCheckNum())) {
						message.setCode(10002);
						message.setMessage("miss import params");
					} else {
						message = physicalCheckDataService.savePhyCheckData(physicalCheckData);
						message.setCode(10000);
						message.setMessage("数据处理成功");
					}
				}
			} catch (Exception e) {
				message.setCode(10001);
				message.setMessage("参数非法");
			}
		} else {
			// 业务状态吗 10001 非法参数
			message.setCode(10001);
			message.setMessage("参数非法");
		}
		return message;
	}

	@RequestMapping("getPhyCheckDataByUserId")
	public Map<String, Object> getPhyCheckDataByUserId(Integer userId) {
		Map<String, Object> reslut = new HashMap<String, Object>();
		if (userId != null) {
			reslut = physicalCheckDataService.getPhyCheckDataByUserId(userId);
		}
		return reslut;
	}

	@RequestMapping("test")
	public void seceivePhyCheckData() {
		PhysicalCheckData physicalCheckData = new PhysicalCheckData();

		physicalCheckData.setCheckNum("201803204124154");
		physicalCheckData.setPatientId(1000);

		// 常规项目检查
		GeneralProjecr generalProjecr = new GeneralProjecr();
		generalProjecr.setHeight(173.0);
		generalProjecr.setWeight(91.0);
		generalProjecr.setBMI(30.4);
		// 收缩压
		generalProjecr.setSystolicPressure(138);
		generalProjecr.setRemark("体重指数增高 腰围增大");
		generalProjecr.setWaistline(80);

		// 内科
		Medical medical = new Medical();
		medical.setMedicalHistory("无");
		medical.setFamilyHistory("无特殊");
		medical.setHeartSounds("正常");
		medical.setHeartRate(72);
		medical.setHeartRhythm("齐");
		// 肾脏叩诊
		medical.setKidney("双肾区无扣痛");
		medical.setRemark("未见明显异常");

		// 血常规
		RoutineBlood routineBlood = new RoutineBlood();
		routineBlood.setHb(154.0);
		routineBlood.setHCT(44.80);
		routineBlood.setLYMPH(32.8);
		routineBlood.setLYMPHValue(2.3);
		routineBlood.setMON(8.4);
		routineBlood.setMONValue(0.6);
		routineBlood.setPLT(291.0);
		routineBlood.setRBC(5.69);
		routineBlood.setWBC(7.0);

		// 外科
		Surgery surgery = new Surgery();

		surgery.setAnus("未见明显异常");
		surgery.setLymphGland("颈部、锁骨上、腋窝及腹股沟未见明显异常");
		surgery.setExtremitiesJoint("未见明显异常");
		surgery.setOther("未见明显异常");
		surgery.setSkin("未见明显异常");
		surgery.setRemark("未见明显异常");
		surgery.setSpine("未见明显异常");
		surgery.setProstate("未见明显异常");

		physicalCheckData.setGeneralProjecr(generalProjecr);
		physicalCheckData.setMedical(medical);
		physicalCheckData.setRoutineBlood(routineBlood);
		physicalCheckData.setSurgery(surgery);

		physicalCheckDataService.savePhyCheckData(physicalCheckData);

	}

	@RequestMapping("insertPatient")
	public void insertPatient() {
		Patient patient = new Patient();
		patient.setAge("37");
		patient.setName("常耀斌");
		patient.setPatientId(1000);
		patient.setUnit("华为");
		patient.setPhone("131*******120");
		patient.setDept("云平台");
		patientService.savePatient2Mongo(patient);
	}
}
