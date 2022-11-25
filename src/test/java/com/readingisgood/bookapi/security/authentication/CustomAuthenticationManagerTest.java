package com.readingisgood.bookapi.security.authentication;

import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerRepository;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.userdetail.UserDetailService;
import com.readingisgood.bookapi.security.userdetail.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomAuthenticationManagerTest {

    UserDetailServiceImpl userDetailService;

    PasswordEncoder bcryptEncoder;

    CustomAuthenticationManager customAuthenticationManager;

    @BeforeEach
    void init() {
        customAuthenticationManager = new CustomAuthenticationManager(mock(UserDetailServiceImpl.class), mock(PasswordEncoder.class));
        userDetailService =mock(UserDetailServiceImpl.class);
        bcryptEncoder=mock(PasswordEncoder.class);
    }

    /*@Test
    void authenticate_tryExistingUser_ShouldReturnToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("ali@gmail.com", "PAaA1s01");

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.randomUUID());
        customerEntity.setRole(RoleType.USER.value);
        customerEntity.setEmail("ali@gmail.com");
        customerEntity.setPassword("PAaA1s01");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleType.USER.value));

        when(userDetailService.loadUserByUsername(any())).thenReturn(new UserdE(customerEntity.getEmail(), customerEntity.getPassword(), grantedAuthorities));
        when(bcryptEncoder.matches(any(), any())).thenReturn(true);

        final Authentication authenticate = customAuthenticationManager.authenticate(authentication);
        Assertions.assertNotNull(authenticate);
    }*/
}