package com.readingisgood.bookapi.domain.common.exception;


import java.util.UUID;

public interface OwnerShipAccessChecker {

    boolean check(UUID id);
}
