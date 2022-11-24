package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.book.BookService;
import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookRequest;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService extends BaseDomainService<OrderEntity, UUID> {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        BookService bookService,
                        CustomerService customerService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public OrderEntity createOrder(List<OrderBookRequest> request) {
        OrderEntity orderEntity = new OrderEntity();
        List<OrderBookEntity> orderBookEntities = new ArrayList<>();
        request.forEach(orderBookRequest -> {
            final BookEntity bookEntity = bookService.findActiveById(orderBookRequest.getId()).get();
            OrderBookEntity orderBookEntity = new OrderBookEntity();
            orderBookEntity.setBook(bookEntity);
            orderBookEntity.setQuantity(orderBookRequest.getQuantity());
            orderBookEntity.setSalePrice(bookEntity.getAmount());
            orderBookEntities.add(orderBookEntity);
        });
        final Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        orderEntity.setOrderTime(orderTime);
        orderEntity.setOrderBooks(orderBookEntities);
        orderEntity.setOrderTime(orderTime);
        orderEntity.setCustomer(customerService.findByEmail(SecurityContextUtil.getUserEmailFromContext()));
        return save(orderEntity);
    }

    public List<OrderEntity> findAllByCustomer(CustomerEntity customerEntity, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return orderRepository.findByCustomer(customerEntity, pageRequest);
    }

    public List<OrderEntity> findAllByStarAndEndTime(Timestamp startTime, Timestamp endTime) {
        return orderRepository.findAllByOrderTimeGreaterThanEqualAndAndOrderTimeLessThanEqual(startTime, endTime);
    }


}
