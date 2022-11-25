package com.readingisgood.bookapi.domain.customer.authentication;

import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.customer.authentication.model.CustomerRegistrationRequest;
import com.readingisgood.bookapi.domain.customer.authentication.model.CustomerRegistrationResponse;
import com.readingisgood.bookapi.domain.customer.authentication.model.LoginResponse;
import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.accesstoken.impl.AccessToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {


    @Mock
    PasswordEncoder bcryptEncoder;

    @Mock
    CustomerService customerService;

    @Mock
    AccessToken accessJWTToken;

    @Mock
    AuthenticationManager authenticationManager;

    AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        authenticationService = new AuthenticationService(bcryptEncoder, customerService, accessJWTToken, authenticationManager);
    }

    @Test
    void register_ShouldReturnRegisteredCustomerResponse() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("ali");
        customerEntity.setSurname("sağlam");
        customerEntity.setPassword("ali");
        customerEntity.setEmail("ali@mail.com");
        when(customerService.save(any())).thenReturn(customerEntity);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setName("ali");
        request.setSurname("sağlam");
        request.setPassword("ali");
        request.setRepassword("ali");
        request.setEmail("ali@mail.com");
        final CustomerRegistrationResponse customerRegistrationResponse = authenticationService.register(request);
        Assertions.assertEquals(request.getEmail(), customerRegistrationResponse.getEmail());
    }

    @Test
    void login_ShouldReturnLoginResponse() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("ali");
        customerEntity.setSurname("sağlam");
        customerEntity.setPassword("ali");
        customerEntity.setEmail("ali@mail.com");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleType.USER.value));

        UserDetails userDetails=new User("ali@mail.com", "pass", grantedAuthorities);

        Authentication authenticationResult = new UsernamePasswordAuthenticationToken(userDetails, "pass", grantedAuthorities);
        when(authenticationManager.authenticate(any())).thenReturn(authenticationResult);
        when(customerService.findByEmail(any())).thenReturn(customerEntity);
        when(accessJWTToken.generateToken(any(), any())).thenReturn("token");


        final LoginResponse loginResponse = authenticationService.login("ali@mail.com", "pass");
        Assertions.assertEquals("ali", loginResponse.getUser().getName());
        Assertions.assertEquals("token", loginResponse.getToken());
    }
}