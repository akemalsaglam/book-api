package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService extends BaseDomainService<CustomerEntity, UUID> {

    private final CustomerRepository userRepository;

    @Autowired
    public CustomerService(CustomerRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public CustomerEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
