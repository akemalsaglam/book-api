package com.readingisgood.bookapi.domain.orderbook;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderBookService extends BaseDomainService<OrderBookEntity, UUID> {

    @Autowired
    public OrderBookService(OrderBookRepository orderBookRepository) {
        super(orderBookRepository);
    }

}
