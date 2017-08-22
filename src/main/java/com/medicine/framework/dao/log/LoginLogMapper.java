package com.medicine.framework.dao.log;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.framework.entity.log.LoginLog;
import com.medicine.framework.util.page.PageInfo;


public interface LoginLogMapper {

    public void insertLog(LoginLog loginLog);

    public List<LoginLog> getLogListPage(@Param("page") PageInfo page, @Param("loginLog") LoginLog loginLog);
}
