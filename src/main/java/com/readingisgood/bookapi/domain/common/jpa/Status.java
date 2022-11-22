package com.readingisgood.bookapi.domain.common.jpa;

public enum Status {

    ACTIVE("ACTIVE"),
    PASSIVE("PASSIVE"),
    BLOCK("BLOCK");

    public final String value;

    Status(String value) {
        this.value = value;
    }
}
