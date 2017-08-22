package com.medicine.framework.security.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.framework.util.State;
import com.thoughtworks.xstream.InitializationException;


public class LoginFailureHandler
        implements
        AuthenticationFailureHandler,
        InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String defaultFailureUrl;

    private boolean forwardToDestination = false;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private ObjectMapper mapper = new ObjectMapper();

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }


    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String jtype = request.getParameter("j_type");
        String targetUrl = "";

        State state = new State("0");

        try {

            if (exception instanceof AuthenticationException) {
                state.setCode("1");
                state.setMsg("用户名或密码错误！");
            } else {
                state.setCode("2");
                state.setMsg("登陆失败！");
            }
            if (!StringUtils.isEmpty(jtype) && jtype.equals("browser")) {
                targetUrl = defaultFailureUrl;
                if (this.forwardToDestination) {
                    request.getRequestDispatcher(targetUrl).forward(request, response);
                } else {
                    this.redirectStrategy.sendRedirect(request, response, targetUrl);
                }
            }
            logger.info("Login Failure,Forwarding to " + targetUrl);
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.info("用户登陆失败！" + e.getMessage());
            }
        }

    }

    private void sendJson(HttpServletRequest request, HttpServletResponse response, State state) throws IOException, ServletException {

        String json = "{\"state\":" + mapper.writeValueAsString(state) + "}";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(defaultFailureUrl))
            throw new InitializationException("You must configure defaultFailureUrl");
    }

}
