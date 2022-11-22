package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.UUID;

@Repository
public interface CustomerRepository extends BaseRepository<CustomerEntity, UUID> {

    CustomerEntity findByEmail(@Email String email);

}
