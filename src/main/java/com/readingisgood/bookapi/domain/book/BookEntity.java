package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "book")
public class BookEntity extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "author is mandatory")
    private String author;

    private String isbn;

    private BigDecimal amount;

    private int stockCount;

    private String status = Status.ACTIVE.value;
}
