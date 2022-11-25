package com.readingisgood.bookapi.domain.customer.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRequest extends BaseRequest {
    private UUID id;
    private String name;
    private String surname;
    private String email;
}
