package com.readingisgood.bookapi.security.authentication;

import com.readingisgood.bookapi.security.userdetail.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public CustomAuthenticationManager(
            UserDetailServiceImpl userDetailService, PasswordEncoder bcryptEncoder) {
        this.userDetailService = userDetailService;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String providedUsername = authentication.getPrincipal().toString();
        UserDetails user = this.userDetailService.loadUserByUsername(providedUsername);

        String providedPassword = authentication.getCredentials().toString();
        String hashedPassword = user.getPassword();

        if (!bcryptEncoder.matches(providedPassword, hashedPassword))
            throw new BadCredentialsException("Incorrect Credentials");

        return new UsernamePasswordAuthenticationToken(
                user, authentication.getCredentials(), user.getAuthorities());
    }
}
