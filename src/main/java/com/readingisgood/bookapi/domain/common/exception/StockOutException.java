package com.readingisgood.bookapi.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockOutException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Stock out exception!";

    public StockOutException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }

    public StockOutException(String exception) {
        super(exception);
    }
}