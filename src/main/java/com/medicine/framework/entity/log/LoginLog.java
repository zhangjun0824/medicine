package com.medicine.framework.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;


@PK(field = "id")
public class LoginLog extends BaseEntity implements Serializable {


    private Date logDate;

    private String userId;

    private String userName;

    private String type;//类型  登陆or退出

    private String ip;

    private String equipment;//设备类型

    private String startDate;

    private String endDate;

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

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

  
}
