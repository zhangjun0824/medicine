package com.medicine.framework.security;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -5238414788950511199L;

    public MyAuthenticationException(String msg) {
        super(msg);
    }

}
