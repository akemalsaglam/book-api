package com.readingisgood.bookapi.domain.customer.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerUpdateRequest extends BaseRequest {

    @NotNull(message = "id is mandatory")
    @ApiModelProperty(required = true)
    private UUID id;

    @NotNull(message = "name is mandatory")
    @ApiModelProperty(required = true)
    private String name;

    @NotNull(message = "name is mandatory")
    @ApiModelProperty(required = true)
    private String surname;
}
