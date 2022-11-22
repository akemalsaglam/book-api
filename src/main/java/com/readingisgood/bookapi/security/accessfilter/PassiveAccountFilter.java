package com.readingisgood.bookapi.security.accessfilter;

import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.security.accesstoken.impl.AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PassiveAccountFilter extends OncePerRequestFilter {

    private final AccessToken accessJWTToken;
    private final CustomerService userService;

    public PassiveAccountFilter(AccessToken accessJWTToken, CustomerService userService) {
        this.accessJWTToken = accessJWTToken;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        final String userEmail = accessJWTToken.getUsernameFromToken(token);
        final CustomerEntity userEntity = userService.findByEmail(userEmail);
        if (!userEntity.isActivated()
                || userEntity.getStatus() == null
                || !userEntity.getStatus().equals(Status.ACTIVE.value)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        chain.doFilter(request, response);
    }
}
