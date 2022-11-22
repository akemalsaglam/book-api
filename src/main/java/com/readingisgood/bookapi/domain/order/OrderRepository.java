package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, UUID> {

}
