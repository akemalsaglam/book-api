package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.domain.customer.model.CustomerUpdateRequest;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import com.readingisgood.bookapi.domain.order.OrderMapper;
import com.readingisgood.bookapi.domain.order.OrderService;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    private final OrderService orderService;
    private final CustomerService customerService;

    public CustomerController(BaseService<CustomerEntity, UUID> service,
                              CustomerOwnerShipAccessChecker customerOwnerShipAccessChecker,
                              OrderService orderService, CustomerService customerService) {
        super(service, CustomerMapper.INSTANCE);
        this.customerOwnerShipAccessChecker = customerOwnerShipAccessChecker;
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get a customer by id.", notes = "Returns a customer by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = CustomerResponse.class),
            @ApiResponse(code = 404, message = "Customer not found."),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#id)")
    @GetMapping("{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable(value = "id") @Valid UUID id) {
        try {
            final Optional<CustomerResponse> customerResponse = super.getById(id);
            log.info("message='getting customer by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(customerResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='customer not found.', id={}", id);
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while getting customer by id={}.'", id, exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Get all customers.", notes = "Returns all customers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = CustomerResponse.class,
                    responseContainer = "List"),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getCustomers() {
        try {
            final List<CustomerResponse> allCustomers = super.getAll();
            log.info("message='getting all customers.', user={}", SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(allCustomers);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all customers.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Update a customer by id.", notes = "Updates a customer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update and return value.", response = CustomerResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#customerUpdateRequest.id)")
    @PutMapping("")
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        try {
            final Optional<CustomerResponse> customerResponse = super.update(CustomerMapper.INSTANCE
                    .mapUpdateRequestToRequest(customerUpdateRequest));
            log.info("message='customer was updated.', id={}, user={}", customerUpdateRequest.getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(customerResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='customer not found.', id={}", customerUpdateRequest.getId());
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while updating customer, id={}.'",
                    customerUpdateRequest.getId(),
                    exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Soft delete a customer by id.", notes = "Soft deletes a customer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deletes customer and return value.", response = boolean.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") @Valid UUID id) {
        try {
            super.softDeleteById(id);
            log.info("message='customer was deleted by id.', id={}, user={}", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok(true);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='customer not found.', id={}", id);
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while soft deleting customer by id={}.'", id, exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Get all orders of a customer.", notes = "Returns all orders of a customer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = CustomerResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#id)")
    @GetMapping("{id}/orders/")
    public ResponseEntity<Object> getCustomersOrders(@PathVariable(value = "id") @Valid UUID id) {
        try {
            final CustomerEntity customer = customerService.findByEmail(SecurityContextUtil.getUserEmailFromContext());
            if (customer == null) {
                throw new ResourceNotFoundException();
            }
            final List<OrderEntity> orders = orderService.findAllByCustomer(customer);
            log.info("message='getting customer by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(OrderMapper.INSTANCE.mapEntityListToResponseList(orders));
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='customer not found.', id={}", id);
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while getting customer by id={}.'", id, exception);
            throw exception;
        }
    }
}
