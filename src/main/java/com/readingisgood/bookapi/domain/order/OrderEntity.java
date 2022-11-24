package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "book_order")
public class OrderEntity extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_order_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<OrderBookEntity> orderBooks;

    private ZonedDateTime orderTime;

    private String status = Status.ACTIVE.value;
}
