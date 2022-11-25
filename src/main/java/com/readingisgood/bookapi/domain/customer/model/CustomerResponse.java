package com.readingisgood.bookapi.domain.customer.model;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerResponse extends BaseResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String status;
    private String role;
    private boolean isActivated;
}
