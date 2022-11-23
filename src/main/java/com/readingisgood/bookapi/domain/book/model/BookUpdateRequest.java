package com.readingisgood.bookapi.domain.book.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookUpdateRequest extends BaseRequest {

    @NotNull(message = "id is mandatory")
    @ApiModelProperty(required = true)
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

    @NotNull(message = "stockCount is mandatory")
    @ApiModelProperty(required = true)
    @Min(value=0)
    private int stockCount;
}
