package com.readingisgood.bookapi.domain.book.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookRequest extends BaseRequest {
    private UUID id;
    private String name;
    private String author;
    private String isbn;
    private BigDecimal amount;
    private int stockCount;
}
