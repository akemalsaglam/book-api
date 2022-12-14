package com.readingisgood.bookapi.security.userdetail;

import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final CustomerService customerService;

    public UserDetailServiceImpl(CustomerService userService) {
        this.customerService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
        CustomerEntity user = customerService.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("User not found.");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
