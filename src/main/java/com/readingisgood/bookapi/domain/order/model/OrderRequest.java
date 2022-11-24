package com.readingisgood.bookapi.domain.order.model;

import com.readingisgood.bookapi.domain.book.model.BookRequest;
import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderRequest extends BaseRequest {
    private UUID id;
    private BookRequest book;
    private Timestamp orderTime;
}
