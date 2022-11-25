package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import com.readingisgood.bookapi.domain.order.OrderService;
import com.readingisgood.bookapi.domain.order.model.OrderMapper;
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

    @ApiOperation(value = "Get all orders of a customer.", notes = "Returns all orders of a customer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = CustomerResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or @customerOwnerShipAccessChecker.check(#id)")
    @GetMapping("{id}/orders/")
    public ResponseEntity<Object> getCustomersOrders(@PathVariable(value = "id") @Valid UUID id,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("size") int size) {
        try {
            final CustomerEntity customer = customerService.findByEmail(SecurityContextUtil.getUserEmailFromContext());
            if (customer == null) {
                throw new ResourceNotFoundException();
            }
            final List<OrderEntity> orders = orderService.findAllByCustomer(customer, page, size);
            log.info("message='getting customers order by userid={}, user={}'", id,
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
