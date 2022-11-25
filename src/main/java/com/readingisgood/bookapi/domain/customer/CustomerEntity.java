package com.readingisgood.bookapi.domain.customer;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "customer")
public class CustomerEntity extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "surname is mandatory")
    private String surname;

    @NotNull(message = "email is mandatory")
    @Email
    private String email;

    @NotNull(message = "role is mandatory")
    private String role;

    private String password;

    private boolean isActivated;

    private String status = Status.PASSIVE.value;
}
