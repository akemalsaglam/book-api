package com.readingisgood.bookapi.domain.book.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookCreateRequest extends BaseRequest {

    private UUID id;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "author is mandatory")
    private String author;
    private String isbn;
    private BigDecimal amount;
    private int stockCount;
}
