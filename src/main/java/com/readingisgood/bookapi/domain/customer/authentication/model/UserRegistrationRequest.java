package com.readingisgood.bookapi.domain.customer.authentication.model;

import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegistrationRequest extends BaseRequest {

    @NotNull
    @NotBlank(message = "name is required.")
    private String name;

    @NotNull
    @NotBlank(message = "surname is required.")
    private String surname;

    @NotNull
    @NotBlank(message = "email is required.")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "password is required.")
    @Length(min = 5, message = "password length should be >=5")
    private String password;

    @NotNull
    @NotBlank(message = "repassword is required.")
    @Length(min = 5, message = "repassword length should be >=5")
    private String repassword;

    private String phone;

    private String birthDate;

    private String address;

}
