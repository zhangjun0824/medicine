package com.medicine.framework.security.log;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import com.medicine.framework.entity.log.LoginLog;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.log.LoginLogService;
import com.medicine.framework.util.GlobalProperties;
import com.medicine.framework.util.log.HttpReqUtil;
import com.thoughtworks.xstream.InitializationException;


public class CustomLogoutHandler
        implements
        LogoutSuccessHandler,
        InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginLogService logService;

    private String logoutSuccessUrl;

    private boolean forwardToSuccess = false;

    public void setForwardToSuccess(boolean forwardToSuccess) {
        this.forwardToSuccess = forwardToSuccess;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        try {
            this.saveLoginInfo(request, authentication);
            if (this.forwardToSuccess) {
                logger.info("logout success,Forwarding to "
                        + this.logoutSuccessUrl);

                request.getRequestDispatcher(this.logoutSuccessUrl)
                        .forward(request, response);
            } else {
                logger.info("logout success,Redirecting to "
                        + this.logoutSuccessUrl);

                this.redirectStrategy.sendRedirect(request, response,
                        this.logoutSuccessUrl);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户退出信息至数据库" + e.getMessage());
            }
        }
    }

    //@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor ={Exception.class})
    public void saveLoginInfo(HttpServletRequest request,
                              Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();
        try {
            LoginLog log = new LoginLog();
            String ip = HttpReqUtil.getIpAddress(request);
            Date date = new Date();
            log.setLogDate(date);
            log.setUserId(user.getId());
            log.setUserName(user.getUsername());
            log.setIp(ip);
            log.setType(GlobalProperties.getGlobalProperty("logout.suc.mes"));
            log.setEquipment(HttpReqUtil.getFromMobileRule(request));
            logService.saveLog(log);
        } catch (DataAccessException e) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户退出信息至数据库" + e.getMessage());
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(logoutSuccessUrl))
            throw new InitializationException("You must configure logoutSuccessUrl");
    }

}
