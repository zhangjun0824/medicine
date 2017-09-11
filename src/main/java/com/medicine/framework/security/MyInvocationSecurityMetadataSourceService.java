package com.medicine.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.medicine.framework.service.user.UserService;

public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UserService userService;

    private volatile Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public synchronized void clearCache() {
        resourceMap = null;
    }

    private synchronized void loadResourceDefine() {

        List<String> query = this.userService.getAllAuthorityName();
        /*
		 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		 * sparta
		 */
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (String auth : query) {
            ConfigAttribute ca = new SecurityConfig(String.valueOf(auth));

            List<String> query1 = this.userService.getResource(auth);

            for (String res : query1) {
                String url = "/" + res;

				/*
				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，
				 * 将权限增加到权限集合中。 sparta
				 */
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
            }
        }

        {// 不需权限验证的
            ConfigAttribute noAuthCA = new SecurityConfig("NoAuthResource");
            List<String> query1 = this.userService.getNoAuthResource();
            for (String res : query1) {
                String url = "/" + res;
				/*
				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，
				 * 将权限增加到权限集合中。 sparta
				 */
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(noAuthCA);
                    resourceMap.put(url, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(noAuthCA);
                    resourceMap.put(url, atts);
                }
            }
        }

    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {

        return null;
    }

    //根据URL，找到相关的权限配置。
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {

        if (resourceMap == null) {
            loadResourceDefine();
        }

        Iterator<String> ite = resourceMap.keySet().iterator();

        FilterInvocation filterInvocation = (FilterInvocation) object;

        while (ite.hasNext()) {

            String url = ite.next();

            RequestMatcher requestMatcher = new AntPathRequestMatcher(url);

            //System.out.println(url+" "+filterInvocation.getHttpRequest().getRequestURL());

            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(url);
            }
        }

        return null;
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}
