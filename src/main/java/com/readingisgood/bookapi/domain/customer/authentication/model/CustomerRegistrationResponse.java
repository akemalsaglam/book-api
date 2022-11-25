package com.readingisgood.bookapi.domain.customer.authentication.model;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegistrationResponse extends BaseResponse {

    private String name;
    private String surname;
    private String email;
    private String role;
}
