package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.order.model.OrderMapper;
import com.readingisgood.bookapi.domain.order.model.OrderRequest;
import com.readingisgood.bookapi.domain.order.model.OrderResponse;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookRequest;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/orders/")
@Validated
@Slf4j
public class OrderController
        extends AbstractController<OrderEntity, OrderRequest, OrderResponse, UUID> {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        super(orderService, OrderMapper.INSTANCE);
        this.orderService = orderService;
    }

    @ApiOperation(value = "Get an order by id.", notes = "Returns an order by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = OrderResponse.class),
            @ApiResponse(code = 404, message = "Order not found."),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("{id}")
    public ResponseEntity<Object> getOrder(@PathVariable(value = "id") @Valid UUID id) {
        try {
            final Optional<OrderResponse> orderResponse = super.getById(id);
            log.info("message='getting order by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(orderResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='order not found id={} .'", id);
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while getting order by id.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Get all orders.", notes = "Returns all orders.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = OrderResponse.class,
                    responseContainer = "List"),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getOrders(@RequestParam("startTime") Timestamp startTime,
                                            @RequestParam("endTime") Timestamp endTime) {
        try {
            final List<OrderEntity> orders = orderService.findAllByStarAndEndTime(startTime, endTime);
            final List<OrderResponse> orderResponses = OrderMapper.INSTANCE.mapEntityListToResponseList(orders);
            log.info("message='getting orders startTime and endTime, user={}'",
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(orderResponses);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all orders.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Create an order.", notes = "Creates an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create and return value.", response = OrderResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @Transactional
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody List<OrderBookRequest> request) {
        try {
            final OrderEntity savedOrderEntity = orderService.createOrder(request);
            final OrderResponse orderResponse = OrderMapper.INSTANCE.mapEntityToResponse(savedOrderEntity);
            log.info("message='order was created, id={}, user={}'", orderResponse.getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while creating order.'", exception);
            throw exception;
        }
    }

}
