package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, UUID> {

    List<OrderEntity> findByCustomer(CustomerEntity customer, Pageable pageable);

}
