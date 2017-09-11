package com.medicine.framework.dao.log;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.framework.entity.log.OperateLog;
import com.medicine.framework.util.page.PageInfo;

public interface OperateLogMapper {

    public void insertSysLog(OperateLog operateLog);

    public List<OperateLog> getOperateLogListPage(@Param("page") PageInfo page, @Param("operateLog") OperateLog operateLog);

}
