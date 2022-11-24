package com.readingisgood.bookapi.domain.orderbook.model;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBookResponse extends BaseResponse {
    private UUID id;
    private String name;
    private String author;
    private String isbn;
    private int stockCount;
}
