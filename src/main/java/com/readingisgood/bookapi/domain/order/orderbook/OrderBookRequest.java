package com.readingisgood.bookapi.domain.order.orderbook;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBookRequest extends BaseRequest {
    private UUID id;
    private int quantity;

}
