package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.domain.customer.model.CustomerUpdateRequest;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import com.readingisgood.bookapi.security.authentication.ServiceErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/customers/")
@Validated
@Slf4j
public class CustomerController
        extends AbstractController<CustomerEntity, CustomerRequest, CustomerResponse, UUID> {

    public static final String DEFAULT_ERROR_MESSAGE = "Bir hata oluştu!";
    private final CustomerOwnerShipAccessChecker customerOwnerShipAccessChecker;

    public CustomerController(BaseService<CustomerEntity, UUID> service,
                              CustomerOwnerShipAccessChecker customerOwnerShipAccessChecker) {
        super(service, CustomerMapper.INSTANCE);
        this.customerOwnerShipAccessChecker = customerOwnerShipAccessChecker;
    }

    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#id)")
    @GetMapping("{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable(value = "id") @Valid UUID id) {
        try {
            final Optional<CustomerResponse> customerResponse = super.getById(id);
            log.info("message='getting customer by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(customerResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting customer by id={}.'", id, exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getCustomers() {
        try {
            final List<CustomerResponse> allCustomers = super.getAll();
            log.info("message='getting all customers, user={}'", SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(allCustomers);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all customers.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#customerUpdateRequest.id)")
    @PutMapping("")
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        try {
            final Optional<CustomerResponse> customerResponse = super.update(CustomerMapper.INSTANCE
                    .mapUpdateRequestToRequest(customerUpdateRequest));
            log.info("message='customer was updated id={}, user={}'", customerUpdateRequest.getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(customerResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while updating customer, id={}.'", customerUpdateRequest.getId(),
                    exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") @Valid UUID id) {
        try {
            super.softDeleteById(id);
            log.info("message=' customer by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            log.error("message='error has occurred while soft deleting customer by id={}.'", id, exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }
}
