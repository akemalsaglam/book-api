package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.service.BaseService;
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
@RequestMapping(path = "api/orders/")
@Validated
@Slf4j
public class OrderController
        extends AbstractController<OrderEntity, OrderRequest, OrderResponse, UUID> {

    public OrderController(BaseService<OrderEntity, UUID> service) {
        super(service, OrderMapper.INSTANCE);
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("{id}")
    public ResponseEntity<Object> getOrder(@PathVariable(value = "id") @Valid UUID id) {
        try {
            final Optional<OrderResponse> orderResponse = super.getById(id);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting order by id.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getOrders() {
        try {
            final List<OrderResponse> allOrderResponses = super.getAll();
            return ResponseEntity.ok().body(allOrderResponses);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all orders.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderRequest bookRequest) {
        try {
            final Optional<OrderResponse> orderResponse = super.insert(bookRequest);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while creating order.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody OrderRequest bookRequest) {
        try {
            final Optional<OrderResponse> orderResponse = super.update(bookRequest);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while updating order.'", exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

}
