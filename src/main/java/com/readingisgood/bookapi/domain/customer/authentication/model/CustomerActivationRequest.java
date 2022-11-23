package com.readingisgood.bookapi.domain.customer.authentication.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CustomerActivationRequest {

    @NotNull @Email String email;

    @NotNull String token;
}
