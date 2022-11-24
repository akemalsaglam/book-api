package com.readingisgood.bookapi.domain.orderbook.model;

import com.readingisgood.bookapi.domain.book.model.BookResponse;
import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBookResponse extends BaseResponse {
    private UUID id;
    private BookResponse book;

    private int quantity;

    private BigDecimal salePrice;

}
