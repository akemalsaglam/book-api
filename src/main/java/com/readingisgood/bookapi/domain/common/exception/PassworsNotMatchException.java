package com.readingisgood.bookapi.domain.common.exception;

public class PassworsNotMatchException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Passwords not match!";

    public PassworsNotMatchException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }

    public PassworsNotMatchException(String exception) {
        super(exception);
    }

}
