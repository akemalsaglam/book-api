package com.readingisgood.bookapi.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Resource not found!";

    public ResourceNotFoundException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }

    public ResourceNotFoundException(String exception) {
        super(exception);
    }
}