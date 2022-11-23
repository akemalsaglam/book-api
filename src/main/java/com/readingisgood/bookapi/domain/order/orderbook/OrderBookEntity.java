package com.readingisgood.bookapi.domain.order.orderbook;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "book_order_items")
public class OrderBookEntity extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BookEntity book;

    @NotNull(message = "quantity is mandatory")
    @Min(value = 1)
    private int quantity;

    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "book_order_id", insertable = false, updatable = false)
    private OrderEntity order;


    private String status = Status.ACTIVE.value;
}
