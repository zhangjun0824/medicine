package com.medicine.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

public class LoginEntryPoint implements AuthenticationEntryPoint {

    private String defaultTargetUrl;

    private String mobileTargetUrl;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String url = request.getRequestURI();
        String targetUrl = null;
        HttpSession s=request.getSession();
        if (url.indexOf("/server/") > 0) {
            request.getRequestDispatcher(mobileTargetUrl).forward(request, response);
        } else {
            // response.sendRedirect(request.getContextPath()+"/" +targetUrl);
            this.redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
        }

    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }
    
    public void setMobileTargetUrl(String mobileTargetUrl) {
    	this.mobileTargetUrl = mobileTargetUrl;
    }

}
