package com.readingisgood.bookapi.domain.customer;

import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import com.readingisgood.bookapi.domain.order.OrderService;
import com.readingisgood.bookapi.domain.order.model.OrderResponse;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Mock
    CustomerOwnerShipAccessChecker customerOwnerShipAccessChecker;

    @Mock
    OrderService orderService;

    @Mock
    CustomerService customerService;

    CustomerController customerController;

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
        customerController = new CustomerController(customerOwnerShipAccessChecker, orderService, customerService);
    }

    @Test
    void getCustomersOrders_ShouldReturnCustomerOrders() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("ali");
        customerEntity.setSurname("sağlam");
        customerEntity.setPassword("ali");
        customerEntity.setEmail("ali@mail.com");

        final UUID order1Id = UUID.randomUUID();
        final UUID order2Id = UUID.randomUUID();

        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(order1Id);

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(order2Id);

        when(customerService.findByEmail(any())).thenReturn(customerEntity);
        when(orderService.findAllByCustomer(customerEntity, 1, 1)).thenReturn(Arrays.asList(orderEntity1, orderEntity2));

        final ResponseEntity<Object> orderResponse = customerController.getCustomersOrders(UUID.randomUUID(), 1, 1);
        Assertions.assertEquals(order1Id, ((List<OrderResponse>) orderResponse.getBody()).get(0).getId());
    }

    @Test
    void getCustomersOrders_whenTryForNonExistUser_ShouldReturnResourceNotFoundException() {
        when(customerService.findByEmail(any())).thenReturn(null);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerController.getCustomersOrders(UUID.randomUUID(), 1, 1));
    }
}