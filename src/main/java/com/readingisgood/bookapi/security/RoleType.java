package com.readingisgood.bookapi.security;

public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER");


    public final String value;

    RoleType(String value) {
        this.value = value;
    }
}
