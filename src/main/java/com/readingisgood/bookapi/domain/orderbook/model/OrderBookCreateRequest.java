package com.readingisgood.bookapi.domain.orderbook.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBookCreateRequest extends BaseRequest {
    private UUID id;
    private int quantity;

}
