package com.readingisgood.bookapi.domain.common.exception;

public class UserActivationNeededException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "User activation is needed!";

    public UserActivationNeededException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }

    public UserActivationNeededException(String exception) {
        super(exception);
    }

}
