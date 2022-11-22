package com.readingisgood.bookapi.domain.customer.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerUpdateRequest extends BaseRequest {

    @NotNull(message = "id is mandatory")
    private UUID id;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "name is mandatory")
    private String surname;
    private String birthDate;
    private String address;
    private String phone;
}
