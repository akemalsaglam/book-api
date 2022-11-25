package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.book.BookService;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.exception.StockOutException;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import com.readingisgood.bookapi.domain.orderbook.model.OrderBookRequest;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    BookService bookService;

    @Mock
    CustomerService customerService;

    OrderService orderService;

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
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, bookService, customerService);
    }

    @Test
    void createOrder_whenTryForNonExistBook_ShouldReturnResourceNotFoundException() {
        doThrow(ResourceNotFoundException.class)
                .when(bookService)
                .updateStockCount(any(), anyInt());

        OrderBookRequest orderBookRequest=new OrderBookRequest();
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> orderService.createOrder(Arrays.asList(orderBookRequest)));
    }

    @Test
    void createOrder_whenTryForStockOutBook_ShouldReturnStockOutException() {
        doThrow(StockOutException.class)
                .when(bookService)
                .updateStockCount(any(), anyInt());

        OrderBookRequest orderBookRequest=new OrderBookRequest();
        Assertions.assertThrows(StockOutException.class,
                () -> orderService.createOrder(Arrays.asList(orderBookRequest)));
    }

    @Test
    void createOrder_whenTryOrderMuchMoreStock_ShouldReturnStockOutException() {
        doThrow(StockOutException.class)
                .when(bookService)
                .updateStockCount(any(), anyInt());

        OrderBookRequest orderBookRequest=new OrderBookRequest();
        Assertions.assertThrows(StockOutException.class,
                () -> orderService.createOrder(Arrays.asList(orderBookRequest)));
    }

    @Test
    void createOrder_whenTryExistingBook_ShouldReturnOrderedEntity() {
        BookEntity bookEntity1=new BookEntity();
        bookEntity1.setId(UUID.randomUUID());
        bookEntity1.setStockCount(12);

        BookEntity bookEntity2=new BookEntity();
        bookEntity2.setId(UUID.randomUUID());
        bookEntity2.setStockCount(12);

        CustomerEntity customerEntity=new CustomerEntity();
        customerEntity.setEmail("ali@mail.com");

        OrderEntity orderEntity=new OrderEntity();
        OrderBookEntity orderBookEntity1=new OrderBookEntity();
        orderBookEntity1.setBook(bookEntity1);

        OrderBookEntity orderBookEntity2=new OrderBookEntity();
        orderBookEntity2.setBook(bookEntity2);

        orderEntity.setOrderBooks(Arrays.asList(orderBookEntity1, orderBookEntity2));

        when(bookService.findActiveById(bookEntity1.getId())).thenReturn(Optional.of(bookEntity1));
        when(bookService.findActiveById(bookEntity2.getId())).thenReturn(Optional.of(bookEntity2));

        when(customerService.findByEmail("ali@mail.com")).thenReturn(customerEntity);

        when(orderService.save(any())).thenReturn(orderEntity);

        OrderBookRequest orderBookRequest1=new OrderBookRequest();
        orderBookRequest1.setId(bookEntity1.getId());
        orderBookRequest1.setQuantity(1);

        OrderBookRequest orderBookRequest2=new OrderBookRequest();
        orderBookRequest2.setId(bookEntity2.getId());
        orderBookRequest2.setQuantity(1);

        final OrderEntity savedOrderEntity = orderService.createOrder(Arrays.asList(orderBookRequest1, orderBookRequest2));
        Assertions.assertEquals(2, savedOrderEntity.getOrderBooks().size());
    }
}