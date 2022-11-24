package com.readingisgood.bookapi.domain.customer.authentication;

import com.readingisgood.bookapi.domain.common.exception.PassworsNotMatchException;
import com.readingisgood.bookapi.domain.common.exception.UserActivationNeededException;
import com.readingisgood.bookapi.domain.common.exception.UserAlreadyExistException;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.customer.authentication.model.LoginRequest;
import com.readingisgood.bookapi.domain.customer.authentication.model.LoginResponse;
import com.readingisgood.bookapi.domain.customer.authentication.model.UserRegistrationRequest;
import com.readingisgood.bookapi.domain.customer.authentication.model.UserRegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/")
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CustomerService customerService;

    public AuthenticationController(AuthenticationService authenticationService, CustomerService customerService) {
        this.authenticationService = authenticationService;
        this.customerService = customerService;
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getRepassword())) {
                throw new PassworsNotMatchException();
            }
            final CustomerEntity userCheck = customerService.findByEmail(userRegistrationRequest.getEmail());
            if (userCheck != null) {
                throw new UserAlreadyExistException();
            }
            final UserRegistrationResponse userRegistrationResponse = authenticationService.register(userRegistrationRequest);
            log.info("message='user registered successfully.', user={}", userRegistrationRequest.getEmail());
            return ResponseEntity.ok().body(userRegistrationResponse);
        } catch (PassworsNotMatchException exception) {
            log.warn("message='getting PassworsNotMatchException while user registered.', user={}", userRegistrationRequest.getEmail());
            throw exception;
        } catch (UserAlreadyExistException exception) {
            log.warn("message='getting UserAlreadyExistException while user registered.', user={}", userRegistrationRequest.getEmail());
            throw exception;
        } catch (Exception exception) {
            log.error("message='getting error while user registered.', user={}", userRegistrationRequest.getEmail(), exception);
            throw exception;
        }
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        try {
            final LoginResponse loginResponse = authenticationService.login(request.getEmail(), request.getPassword());
            if (loginResponse != null) {
                log.info("message='user logged in.', user={}", loginResponse.getUser().getEmail());
                return ResponseEntity.ok().body(loginResponse);
            } else {
                throw new BadCredentialsException("Incorrect Credentials");
            }
        } catch (BadCredentialsException exception) {
            log.warn("message='user or password incorrect to log in.', user={}", request.getEmail());
            throw exception;
        } catch (UserActivationNeededException exception) {
            log.warn("message='user activation is needed.', user={}", request.getEmail());
            throw exception;
        } catch (Exception exception) {
            log.error("message='getting error while user logged in.', user={}", request.getEmail(), exception);
            throw exception;
        }
    }
}
