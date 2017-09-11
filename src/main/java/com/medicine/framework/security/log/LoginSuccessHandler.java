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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import com.medicine.framework.entity.log.LoginLog;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.log.LoginLogService;
import com.medicine.framework.util.GlobalProperties;
import com.medicine.framework.util.log.HttpReqUtil;
import com.thoughtworks.xstream.InitializationException;


public class LoginSuccessHandler
        implements
        AuthenticationSuccessHandler,
        InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginLogService logService;

    private String defaultTargetUrl;

    private String mobileTargetUrl;

    private boolean forwardToDestination = false;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void setMobileTargetUrl(String mobileTargetUrl) {
        this.mobileTargetUrl = mobileTargetUrl;
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String jtype = request.getParameter("j_type");
        String targetUrl = "";
        try {

            saveLoginInfo(request, authentication);
            if (!StringUtils.isEmpty(jtype) && (jtype.equals("mobile") || jtype.equals("client"))) {
                targetUrl = mobileTargetUrl;
            } else {
                targetUrl = defaultTargetUrl;
            }

            if (this.forwardToDestination) {
                logger.info("Login success,Forwarding to " + targetUrl);

                request.getRequestDispatcher(targetUrl).forward(request, response);
            } else {
                logger.info("Login success,Redirecting to " + targetUrl);

                this.redirectStrategy.sendRedirect(request, response, targetUrl);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户登录信息至数据库" + e.getMessage());
            }
        }
    }

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
            log.setType(GlobalProperties.getGlobalProperty("login.suc.mes"));
            log.setEquipment(HttpReqUtil.getFromMobileRule(request));
            logService.saveLog(log);
        } catch (DataAccessException e) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户登陆信息至数据库" + e.getMessage());
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(defaultTargetUrl))
            throw new InitializationException(
                    "You must configure defaultTargetUrl");
    }

}
