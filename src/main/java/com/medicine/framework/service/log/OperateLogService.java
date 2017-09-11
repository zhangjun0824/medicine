package com.medicine.framework.service.log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.log.OperateLogMapper;
import com.medicine.framework.entity.log.OperateLog;
import com.medicine.framework.util.page.PageInfo;


@Service
public class OperateLogService extends BaseService {

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OperateLogMapper systemLogMapper;

    public void saveSysLog(OperateLog systemLog) {
//        systemLog.setId(this.getIdWorker().nextId());
//        systemLogMapper.insertSysLog(systemLog);
    }

    /**
     * 分页查询系统操作日志
     *
     * @param page
     * @return
     */
    public List<OperateLog> getOperateLogListPage(PageInfo page, OperateLog operateLog) {
        String startDate = operateLog.getStartDate();
        if (StringUtils.isNotEmpty(startDate) && startDate.contains("-")) {
            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String[] startDates = startDate.split("-");
            try {
                operateLog.setStartDate(df.format(df1.parse(startDates[0])) + " 00:00:00");
                operateLog.setEndDate(df.format(df1.parse(startDates[1])) + " 23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return systemLogMapper.getOperateLogListPage(page, operateLog);
    }
}
