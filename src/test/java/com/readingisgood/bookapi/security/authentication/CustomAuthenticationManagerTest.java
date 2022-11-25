package com.readingisgood.bookapi.security.authentication;

import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.userdetail.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomAuthenticationManagerTest {

    @Mock
    UserDetailServiceImpl userDetailService;

    @Mock
    PasswordEncoder bcryptEncoder;

    CustomAuthenticationManager customAuthenticationManager;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        customAuthenticationManager = new CustomAuthenticationManager(userDetailService, bcryptEncoder);
    }

    @Test
    void authenticate_tryExistingUser_ShouldReturnToken() {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleType.USER.value));

        User user = new User("ali@gmail.com", "PAaA1s01", grantedAuthorities);
        when(userDetailService.loadUserByUsername(any())).thenReturn(user);
        when(bcryptEncoder.matches(any(), any())).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("ali@gmail.com", "PAaA1s01");

        final Authentication authenticate = customAuthenticationManager.authenticate(authentication);
        Assertions.assertNotNull(authenticate.getCredentials());
    }

}