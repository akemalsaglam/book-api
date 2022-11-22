package com.readingisgood.bookapi.security.authentication.model;

import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import lombok.Data;

@Data
public class LoginResponse {

    CustomerResponse user;

    String token;
}
