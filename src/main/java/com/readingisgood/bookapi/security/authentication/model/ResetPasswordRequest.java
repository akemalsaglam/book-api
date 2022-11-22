package com.readingisgood.bookapi.security.authentication.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordRequest {

    @NotNull @Email String email;

    @NotNull String token;

    @NotNull
    @NotBlank(message = "password is required.")
    @Length(min = 5, message = "password length should be >=5")
    String password;

    @NotNull
    @NotBlank(message = "password is required.")
    @Length(min = 5, message = "password length should be >=5")
    String repassword;
}
