package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponse extends BaseResponse {
    private UUID id;

    private CustomerResponse customer;

    private List<OrderBookResponse> orderBooks;
    private ZonedDateTime orderTime;

    private String status;
}
