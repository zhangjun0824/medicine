package com.medicine.framework.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.user.UserService;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        User user = userService.getUserByUserName(username);

        if (user != null)
        {
            List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            List<String> authorityName = userService.getAuthorityName(user.getId());

            for (String roleName : authorityName) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
                auths.add(authority);
            }

            String pwd = this.userService.getPWD(user.getUsername());

            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonLocked(true);
            user.setAuthorities(auths);
            user.setPassword(pwd);
        }
        return user;
    }

}
