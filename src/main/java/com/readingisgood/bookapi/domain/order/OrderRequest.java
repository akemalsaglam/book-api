package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.book.model.BookRequest;
import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderRequest extends BaseRequest {
    private UUID id;
    private CustomerRequest customer;
    private BookRequest book;
    private BigDecimal saleAmount;

    private ZonedDateTime orderTime;
}
