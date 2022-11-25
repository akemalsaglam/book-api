package com.readingisgood.bookapi.security.userdetail;

import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.security.RoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailServiceImplTest {

    private CustomerService customerService;

    private UserDetailServiceImpl userDetailService;

    @BeforeEach
    void init() {
        customerService = mock(CustomerService.class);
        userDetailService = new UserDetailServiceImpl(customerService);
    }

    @Test
    void loadUserByUsername_whenTryNonExistedMail_ShouldReturnBadCredentialsException() {
        when(customerService.findByEmail(any())).thenReturn(null);
        Assertions.assertThrows(BadCredentialsException.class,
                () -> userDetailService.loadUserByUsername("mail"));
    }

    @Test
    void loadUserByUsername_whenTryExistingUser_ShouldReturnUser() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setPassword("tBh5OLvw");
        customerEntity.setEmail("1y97lxL");
        customerEntity.setRole(RoleType.USER.value);
        when(customerService.findByEmail(any())).thenReturn(customerEntity);

        Assertions.assertEquals(customerEntity.getEmail(),
                userDetailService.loadUserByUsername(customerEntity.getEmail()).getUsername());
    }
}