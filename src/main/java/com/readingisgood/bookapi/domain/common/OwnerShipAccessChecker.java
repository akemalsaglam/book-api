package com.readingisgood.bookapi.domain.common;


import java.util.UUID;

public interface OwnerShipAccessChecker {

    boolean check(UUID id);
}
