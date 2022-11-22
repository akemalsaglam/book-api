package com.readingisgood.bookapi.security.accesstoken.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessToken extends BaseToken {

    public static final long TOKEN_VALIDITY_SECONDS = 15 * 24 * 60 * 60; //15 DAYs

    @Autowired
    public AccessToken() {
        super(TOKEN_VALIDITY_SECONDS);
    }
}
