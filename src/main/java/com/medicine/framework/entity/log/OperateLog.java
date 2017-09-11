package com.medicine.framework.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

@PK
public class OperateLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4566090709995018694L;

    private String userId;

    private String userName;

    private Date logDate;

    private String logClassName;

    private String logMethod;

    private String logParam;

    private String type;// 类型 操作成功or失败

    private String ip;

    private String equipment;// 设备类型

    private String logDesc;

    private String info;

    private String startDate;

    private String endDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogClassName() {
		return logClassName;
	}

	public void setLogClassName(String logClassName) {
		this.logClassName = logClassName;
	}

	public String getLogMethod() {
		return logMethod;
	}

	public void setLogMethod(String logMethod) {
		this.logMethod = logMethod;
	}

	public String getLogParam() {
		return logParam;
	}

	public void setLogParam(String logParam) {
		this.logParam = logParam;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "OperateLog{" +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", logDate=" + logDate +
				", logClassName='" + logClassName + '\'' +
				", logMethod='" + logMethod + '\'' +
				", logParam='" + logParam + '\'' +
				", type='" + type + '\'' +
				", ip='" + ip + '\'' +
				", equipment='" + equipment + '\'' +
				", logDesc='" + logDesc + '\'' +
				", info='" + info + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				'}';
	}
}
