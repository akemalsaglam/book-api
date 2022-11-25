package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.book.BookRepository;
import com.readingisgood.bookapi.domain.book.BookService;
import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.order.model.OrderMapper;
import com.readingisgood.bookapi.domain.order.model.OrderResponse;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookMapper;
import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @Mock
    OrderService orderService;

    AbstractController abstractController;

    OrderController orderController;

    static MockedStatic<SecurityContextUtil> mockedSecurityContextUtil;

    @BeforeAll
    public static void before() {
        mockedSecurityContextUtil = Mockito.mockStatic(SecurityContextUtil.class);
        mockedSecurityContextUtil.when(SecurityContextUtil::getUserEmailFromContext).thenReturn("ali@mail.com");
    }

    @AfterAll
    public static void close() {
        mockedSecurityContextUtil.close();
    }

    @BeforeEach
    void init() {
        orderService = new OrderService(mock(OrderRepository.class), mock(BookService.class), mock(CustomerService.class));
        abstractController = mock(AbstractController.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void getBook_whenTryToGetExistingOrder_ShouldReturnOrder() {
        final UUID orderId = UUID.randomUUID();
        final UUID customerId = UUID.randomUUID();
        final UUID bookId = UUID.randomUUID();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setName("GwvC0VU");
        customer.setEmail("fj0Wg4");
        customer.setAddress("Gzh4");
        customer.setRole(RoleType.USER.value);
        customer.setSurname("928y");
        customer.setBirthDate("IB7r");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("rEH9");
        bookEntity.setId(bookId);
        bookEntity.setIsbn("Ys3Dn");
        bookEntity.setAmount(BigDecimal.valueOf(100.0));
        bookEntity.setStockCount(10);


        OrderBookEntity orderBookEntity = new OrderBookEntity();
        orderBookEntity.setBook(bookEntity);
        orderBookEntity.setQuantity(1);
        orderBookEntity.setSalePrice(BigDecimal.valueOf(100.0));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderId);
        orderResponse.setOrderTime(new Timestamp(System.currentTimeMillis()));
        orderResponse.setStatus(Status.ACTIVE.value);
        orderResponse.setCustomer(CustomerMapper.INSTANCE.mapEntityToResponse(customer));
        orderResponse.setOrderBooks(Arrays.asList(OrderBookMapper.INSTANCE.mapEntityToResponse(orderBookEntity)));

        when(orderService.findActiveById(orderId)).thenReturn(Optional.ofNullable(OrderMapper.INSTANCE.mapResponseToEntity(orderResponse)));
        final ResponseEntity<Object> orderResponseEntity = orderController.getOrder(orderId);
        Assertions.assertEquals(orderId, ((Optional<OrderResponse>) orderResponseEntity.getBody()).get().getId());
    }

    @Test
    void getBook_whenTryToGetNotExistedOrder_ShouldReturnResourceNotFoundException() {
        final UUID orderId = UUID.randomUUID();
        when(orderService.findActiveById(orderId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderController.getOrder(orderId));
    }

}