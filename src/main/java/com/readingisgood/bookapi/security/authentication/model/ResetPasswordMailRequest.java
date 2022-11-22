package com.readingisgood.bookapi.security.authentication.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordMailRequest {

    @NotNull @Email String email;
}
