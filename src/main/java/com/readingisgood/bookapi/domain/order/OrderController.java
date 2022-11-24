package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.book.BookService;
import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import com.readingisgood.bookapi.domain.orderbook.OrderBookService;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/orders/")
@Validated
@Slf4j
public class OrderController
        extends AbstractController<OrderEntity, OrderRequest, OrderResponse, UUID> {

    private final BookService bookService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderBookService orderBookService;

    public OrderController(OrderService orderService, BookService bookService,
                           CustomerService customerService, OrderBookService orderBookService) {
        super(orderService, OrderMapper.INSTANCE);
        this.orderService = orderService;
        this.bookService = bookService;
        this.customerService = customerService;
        this.orderBookService = orderBookService;
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
    public ResponseEntity<Object> getOrders() {
        try {
            final List<OrderResponse> allOrderResponses = super.getAll();
            return ResponseEntity.ok().body(allOrderResponses);
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
            OrderEntity orderEntity = new OrderEntity();
            List<OrderBookEntity> orderBookEntities = new ArrayList<>();
            request.forEach(orderBookRequest -> {
                final BookEntity bookEntity = bookService.findActiveById(orderBookRequest.getId()).get();
                OrderBookEntity orderBookEntity = new OrderBookEntity();
                orderBookEntity.setBook(bookEntity);
                orderBookEntity.setQuantity(orderBookRequest.getQuantity());
                orderBookEntity.setSalePrice(bookEntity.getAmount());
                orderBookEntities.add(orderBookEntity);
            });
            orderEntity.setOrderBooks(orderBookEntities);
            orderEntity.setOrderTime(ZonedDateTime.now());
            orderEntity.setCustomer(customerService.findByEmail(SecurityContextUtil.getUserEmailFromContext()));
            final OrderEntity savedOrderEntity = orderService.save(orderEntity);
            final OrderResponse orderResponse = OrderMapper.INSTANCE.mapEntityToResponse(savedOrderEntity);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while creating order.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Update an order by id.", notes = "Updates an order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update and return value.", response = OrderResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            final Optional<OrderResponse> orderResponse = super.update(orderRequest);
            return ResponseEntity.ok().body(orderResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='order not found id={} .'", orderRequest.getId());
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while updating order.'", exception);
            throw exception;
        }
    }

}
