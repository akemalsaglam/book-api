package com.readingisgood.bookapi.domain.book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookCreateRequest extends BaseRequest {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "name is mandatory")
    @ApiModelProperty(required = true)
    private String name;

    @NotNull(message = "author is mandatory")
    @ApiModelProperty(required = true)
    private String author;
    private String isbn;

    @NotNull(message = "amount is mandatory")
    @ApiModelProperty(required = true)
    private BigDecimal amount;

    private int stockCount;
}
