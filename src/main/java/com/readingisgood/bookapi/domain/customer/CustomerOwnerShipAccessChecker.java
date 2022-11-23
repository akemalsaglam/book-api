package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.exception.OwnerShipAccessChecker;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerOwnerShipAccessChecker implements OwnerShipAccessChecker {

    private final CustomerService customerService;

    public CustomerOwnerShipAccessChecker(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean check(UUID id) {
        return customerService.findById(id).get().getEmail()
                .equals(SecurityContextUtil.getUserEmailFromContext());
    }
}
