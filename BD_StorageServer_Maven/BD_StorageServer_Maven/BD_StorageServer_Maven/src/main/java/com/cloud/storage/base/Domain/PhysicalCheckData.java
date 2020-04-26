package com.cloud.storage.base.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 体检报告信息javaBean
 * 
 * @author Administrator
 *
 */
@Document(collection = "phyCheckData")
public class PhysicalCheckData {
	// 监测编号
	@Id
	private String checkNum;
	// 患者id
	@Indexed
	private Integer patientId;

	// 常规检查
	private GeneralProjecr generalProjecr;
	// 内科
	private Medical medical;
	// 外科
	private Surgery surgery;
	// 血常规
	private RoutineBlood routineBlood;

	// 体检日期
	private String checkDate;

	/**
	 * 常规项目检查
	 * 
	 * @author Administrator
	 *
	 */
	public static class GeneralProjecr {
		// 身高
		private Double height;
		// 体重
		private Double weight;
		// 体重指数
		private Double BMI;
		// 腰围
		private Integer waistline;
		// 收缩压
		private Integer systolicPressure;

		// 舒张压
		private Integer diastolicPressure;
		// 意见
		private String remark;

		public Double getHeight() {
			return height;
		}

		public void setHeight(Double height) {
			this.height = height;
		}

		public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

		public Double getBMI() {
			return BMI;
		}

		public void setBMI(Double bMI) {
			BMI = bMI;
		}

		public Integer getWaistline() {
			return waistline;
		}

		public void setWaistline(Integer waistline) {
			this.waistline = waistline;
		}

		public Integer getSystolicPressure() {
			return systolicPressure;
		}

		public void setSystolicPressure(Integer systolicPressure) {
			this.systolicPressure = systolicPressure;
		}

		public Integer getDiastolicPressure() {
			return diastolicPressure;
		}

		public void setDiastolicPressure(Integer diastolicPressure) {
			this.diastolicPressure = diastolicPressure;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
	}

	// 内科
	public static class Medical {
		// 病史
		private String medicalHistory;
		// 家族史
		private String familyHistory;
		// 心律
		private String heartRhythm;
		// 心音
		private String heartSounds;

		// 肺部听诊
		private String lungsAuscultation;

		// 肝脏听诊
		private String liverAuscultation;

		// 心率
		private Integer heartRate;

		// 肾脏叩诊
		private String kidney;

		// 意见
		private String remark;

		public String getMedicalHistory() {
			return medicalHistory;
		}

		public void setMedicalHistory(String medicalHistory) {
			this.medicalHistory = medicalHistory;
		}

		public String getFamilyHistory() {
			return familyHistory;
		}

		public void setFamilyHistory(String familyHistory) {
			this.familyHistory = familyHistory;
		}

		public String getHeartRhythm() {
			return heartRhythm;
		}

		public void setHeartRhythm(String heartRhythm) {
			this.heartRhythm = heartRhythm;
		}

		public String getHeartSounds() {
			return heartSounds;
		}

		public void setHeartSounds(String heartSounds) {
			this.heartSounds = heartSounds;
		}

		public String getLungsAuscultation() {
			return lungsAuscultation;
		}

		public void setLungsAuscultation(String lungsAuscultation) {
			this.lungsAuscultation = lungsAuscultation;
		}

		public String getLiverAuscultation() {
			return liverAuscultation;
		}

		public void setLiverAuscultation(String liverAuscultation) {
			this.liverAuscultation = liverAuscultation;
		}

		public Integer getHeartRate() {
			return heartRate;
		}

		public void setHeartRate(Integer heartRate) {
			this.heartRate = heartRate;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getKidney() {
			return kidney;
		}

		public void setKidney(String kidney) {
			this.kidney = kidney;
		}

	}

	// 外科
	public static class Surgery {
		// 淋巴结
		private String lymphGland;
		// 皮肤
		private String skin;
		// 甲状腺
		private String thyroid;
		// 脊柱
		private String spine;
		// 四肢关节
		private String extremitiesJoint;
		// 前列腺
		private String prostate;
		// 肛门
		private String anus;

		// 外科其他
		private String other;

		private String remark;

		public String getLymphGland() {
			return lymphGland;
		}

		public void setLymphGland(String lymphGland) {
			this.lymphGland = lymphGland;
		}

		public String getSkin() {
			return skin;
		}

		public void setSkin(String skin) {
			this.skin = skin;
		}

		public String getThyroid() {
			return thyroid;
		}

		public void setThyroid(String thyroid) {
			this.thyroid = thyroid;
		}

		public String getSpine() {
			return spine;
		}

		public void setSpine(String spine) {
			this.spine = spine;
		}

		public String getExtremitiesJoint() {
			return extremitiesJoint;
		}

		public void setExtremitiesJoint(String extremitiesJoint) {
			this.extremitiesJoint = extremitiesJoint;
		}

		public String getProstate() {
			return prostate;
		}

		public void setProstate(String prostate) {
			this.prostate = prostate;
		}

		public String getAnus() {
			return anus;
		}

		public void setAnus(String anus) {
			this.anus = anus;
		}

		public String getOther() {
			return other;
		}

		public void setOther(String other) {
			this.other = other;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	}

	// 血常规
	public static class RoutineBlood {
		// 白细胞计数
		private Double WBC;
		// 淋巴细胞百分比
		private Double LYMPH;
		// 中间细胞百分比
		private Double MON;
		// 淋巴细胞绝对值
		private Double LYMPHValue;
		// 中间细胞绝对值
		private Double MONValue;
		// 红细胞计数
		private Double RBC;
		// 血红蛋白
		private Double Hb;

		// 红细胞压积
		private Double HCT;
		// 血小板计数
		private Double PLT;
		private String remark;

		public Double getWBC() {
			return WBC;
		}

		public void setWBC(Double wBC) {
			WBC = wBC;
		}

		public Double getLYMPH() {
			return LYMPH;
		}

		public void setLYMPH(Double lYMPH) {
			LYMPH = lYMPH;
		}

		public Double getMON() {
			return MON;
		}

		public void setMON(Double mON) {
			MON = mON;
		}

		public Double getLYMPHValue() {
			return LYMPHValue;
		}

		public void setLYMPHValue(Double lYMPHValue) {
			LYMPHValue = lYMPHValue;
		}

		public Double getMONValue() {
			return MONValue;
		}

		public void setMONValue(Double mONValue) {
			MONValue = mONValue;
		}

		public Double getRBC() {
			return RBC;
		}

		public void setRBC(Double rBC) {
			RBC = rBC;
		}

		public Double getHb() {
			return Hb;
		}

		public void setHb(Double hb) {
			Hb = hb;
		}

		public Double getHCT() {
			return HCT;
		}

		public void setHCT(Double hCT) {
			HCT = hCT;
		}

		public Double getPLT() {
			return PLT;
		}

		public void setPLT(Double pLT) {
			PLT = pLT;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	}

	public GeneralProjecr getGeneralProjecr() {
		return generalProjecr;
	}

	public void setGeneralProjecr(GeneralProjecr generalProjecr) {
		this.generalProjecr = generalProjecr;
	}

	public Medical getMedical() {
		return medical;
	}

	public void setMedical(Medical medical) {
		this.medical = medical;
	}

	public Surgery getSurgery() {
		return surgery;
	}

	public void setSurgery(Surgery surgery) {
		this.surgery = surgery;
	}

	public RoutineBlood getRoutineBlood() {
		return routineBlood;
	}

	public void setRoutineBlood(RoutineBlood routineBlood) {
		this.routineBlood = routineBlood;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 结果分析
	 * 
	 * @author Administrator
	 *
	 */
	public static class ResultAnalysis {
		// BMI分析
		private String BMIAnalysis;
		// 血压分析
		private String bloodPressureAnalysis;

		// 心率分析
		private String heartRateAnalysis;

	}

}
