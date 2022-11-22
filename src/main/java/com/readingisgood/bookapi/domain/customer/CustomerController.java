package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
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
            return ResponseEntity.ok().body(customerResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting customer by id.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getCustomers() {
        try {
            final List<CustomerResponse> allCustomers = super.getAll();
            return ResponseEntity.ok().body(allCustomers);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all customers.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#customerRequest.id)")
    @PutMapping("")
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        try {
            final Optional<CustomerResponse> customerResponse = super.update(customerRequest);
            return ResponseEntity.ok().body(customerResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all customers.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") @Valid UUID id) {
        try {
            //final Optional<CustomerResponse> customerResponse = super.update(customerRequest);
            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all customers.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }
}
