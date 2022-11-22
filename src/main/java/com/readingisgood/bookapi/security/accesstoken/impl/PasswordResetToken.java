package com.readingisgood.bookapi.security.accesstoken.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetToken extends BaseToken {
    public static final long TOKEN_VALIDITY_SECONDS = 2 * 60 * 60; //2 HOURS

    @Autowired
    public PasswordResetToken() {
        super(TOKEN_VALIDITY_SECONDS);
    }
}
