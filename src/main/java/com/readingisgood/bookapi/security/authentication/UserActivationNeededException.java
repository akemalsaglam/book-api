package com.readingisgood.bookapi.security.authentication;

public class UserActivationNeededException extends RuntimeException {

    public UserActivationNeededException(String message) {
        super(message, null, false, false);
    }

}
