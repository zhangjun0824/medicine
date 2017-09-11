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
import com.medicine.framework.dao.log.LoginLogMapper;
import com.medicine.framework.entity.log.LoginLog;
import com.medicine.framework.util.page.PageInfo;


@Service
public class LoginLogService extends BaseService {

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginLogMapper logMapper;

    public void saveLog(LoginLog log) {
        logMapper.insertLog(log);
    }

    /**
     * 分页查询登陆退出日志
     *
     * @param page
     * @param log
     * @return
     */
    public List<LoginLog> getLogListPage(PageInfo page, LoginLog loginLog) {
        String startDate = loginLog.getStartDate();
        if (StringUtils.isNotEmpty(startDate) && startDate.contains("-")) {
            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String[] startDates = startDate.split("-");
            try {
                loginLog.setStartDate(df.format(df1.parse(startDates[0])) + " 00:00:00");
                loginLog.setEndDate(df.format(df1.parse(startDates[1])) + " 23:59:59");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return logMapper.getLogListPage(page, loginLog);
    }
}
