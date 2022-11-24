package com.readingisgood.bookapi.domain.order.model;

import com.readingisgood.bookapi.domain.book.model.BookRequest;
import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderCreateRequest extends BaseRequest {
    private UUID id;

    private List<BookRequest> books;
    private long orderTime;

    private CustomerEntity customer;
}
