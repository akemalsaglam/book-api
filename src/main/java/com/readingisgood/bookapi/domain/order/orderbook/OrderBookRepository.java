package com.readingisgood.bookapi.domain.order.orderbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderBookRepository extends JpaRepository<OrderBookEntity, UUID> {

}
