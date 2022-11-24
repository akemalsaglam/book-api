package com.readingisgood.bookapi.domain.order.model;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponse extends BaseResponse {
    private UUID id;

    private CustomerResponse customer;

    private List<OrderBookResponse> orderBooks;
    private Timestamp orderTime;

    private String status;
}
