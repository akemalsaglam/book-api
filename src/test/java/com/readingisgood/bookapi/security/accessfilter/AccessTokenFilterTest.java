package com.readingisgood.bookapi.security.accessfilter;

import com.readingisgood.bookapi.domain.order.OrderService;
import com.readingisgood.bookapi.security.accesstoken.impl.AccessToken;
import com.readingisgood.bookapi.security.userdetail.UserDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenFilterTest {

    @Mock
    AccessToken accessToken;

    @Mock
    UserDetailServiceImpl jwtUserDetailService;

    /*@BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, bookService, customerService);
    }*/

    @Test
    void doFilterInternal() {
    }
}