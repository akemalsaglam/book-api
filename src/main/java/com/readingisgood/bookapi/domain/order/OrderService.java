package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService extends BaseDomainService<OrderEntity, UUID> {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> findAllByCustomer(CustomerEntity customerEntity, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return orderRepository.findByCustomer(customerEntity, pageRequest);
    }


}
