package com.readingisgood.bookapi.domain.common.exception;

public class UserAlreadyExistException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "User already exist!";

    public UserAlreadyExistException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }

    public UserAlreadyExistException(String exception) {
        super(exception);
    }

}
