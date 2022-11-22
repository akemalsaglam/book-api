package com.readingisgood.bookapi.security.authentication;

import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.security.authentication.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
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

    private static final String DEFAULT_ERROR_MESSAGE = "Bir hata oluştu!";
    private static final String USER_OR_PASSWORD_INVALID = "E-posta veya şifre hatalı!";
    public static final String PASSWORDS_NOT_MATCHING_ERROR_MESSAGE = "Şifreler uyumlu değil!";
    public static final String ACTIVATION_MAIL_SUCCESS_MESSAGE = "Aktivasyon maili gönderildi. Lütfen mailinize gelen aktivasyon butonuna tıklayınız.";
    public static final String ACCOUNT_ACTIVATION_SUCCESS_MESSAGE = "Hesabınız aktive edildi. Giriş yapabilirsiniz.";
    public static final String PASSWORD_RESET_MAIL_SUCCESS_MESSAGE = "Şifre yenileme maili gönderildi!";
    public static final String PASSWORD_CHANGED_SUCCESS_MESSAGE = "Şifreniz değiştirildi!";
    private static final String REGISTERED_USER_ALREADY_EXIST = "Girmiş olduğunuz mail adresi sistemde kayıtlı.";

    private final AuthenticationService authenticationService;
    private final CustomerService userService;

    public AuthenticationController(AuthenticationService authenticationService, CustomerService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest request) {
        try {
            final LoginResponse loginResponse = authenticationService.login(request.getEmail(), request.getPassword());
            if (loginResponse != null) {
                log.info("message='user logged in.', user={}", loginResponse.getUser().getEmail());
                return ResponseEntity.ok().body(loginResponse);
            } else {
                throw new AuthenticationServiceException(HttpStatus.UNAUTHORIZED.toString());
            }
        } catch (BadCredentialsException exception) {
            log.error("message='user or password incorrect to log in.', user={}", request.getEmail(), exception);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServiceErrorMessage(USER_OR_PASSWORD_INVALID));
        } catch (UserActivationNeededException exception) {
            log.error("message='user activation is needed.', user={}", request.getEmail(), exception);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServiceErrorMessage(exception.getMessage()));
        } catch (Exception exception) {
            log.error("message='getting error while user logged in.', user={}", request.getEmail(), exception);
            return ResponseEntity.internalServerError().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getRepassword())) {
                return ResponseEntity.badRequest().body(new ServiceErrorMessage(PASSWORDS_NOT_MATCHING_ERROR_MESSAGE));
            }
            final CustomerEntity userCheck = userService.findByEmail(userRegistrationRequest.getEmail());
            if (userCheck != null) {
                return ResponseEntity.badRequest().body(new ServiceErrorMessage(REGISTERED_USER_ALREADY_EXIST));
            }
            authenticationService.register(userRegistrationRequest);
            log.info("message='user registered.', user={}", userRegistrationRequest.getEmail());
            return ResponseEntity.ok().body(new SuccessMessage(ACTIVATION_MAIL_SUCCESS_MESSAGE));
        } catch (Exception exception) {
            log.error("message='getting error while user registered.', user={}", userRegistrationRequest.getEmail(), exception);
            return ResponseEntity.internalServerError().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PostMapping("activate-account")
    public ResponseEntity<Object> activateAccount(@RequestBody @Valid UserActivationRequest userActivationRequest) {
        try {
            final boolean activationResult = authenticationService.activateAccount(userActivationRequest.getToken(), userActivationRequest.getEmail());
            if (activationResult) {
                log.info("message='user activated.', user={}", userActivationRequest.getEmail());
                return ResponseEntity.ok().body(new SuccessMessage(ACCOUNT_ACTIVATION_SUCCESS_MESSAGE));
            }
            log.error("message='getting error while user activated.', user={}", userActivationRequest.getEmail());
            return ResponseEntity.badRequest().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        } catch (Exception exception) {
            log.error("message='getting error while user activated.', user={}", userActivationRequest.getEmail());
            return ResponseEntity.internalServerError().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PostMapping("send-reset-password-mail")
    public ResponseEntity<Object> sendResetPasswordMail(@RequestBody @Valid ResetPasswordMailRequest resetPasswordMailRequest) {
        try {
            authenticationService.sendPasswordResetMail(resetPasswordMailRequest.getEmail());
            log.info("message='sending reset password mail.', user={}", resetPasswordMailRequest.getEmail());
            return ResponseEntity.ok().body(new SuccessMessage(PASSWORD_RESET_MAIL_SUCCESS_MESSAGE));
        } catch (Exception exception) {
            log.error("message='getting error while sending reset password mail.', user={}", resetPasswordMailRequest.getEmail());
            return ResponseEntity.internalServerError().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        try {
            final boolean result = authenticationService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getEmail(),
                    resetPasswordRequest.getPassword(), resetPasswordRequest.getRepassword());
            if (result) {
                log.info("message='password has changed.', user={}", resetPasswordRequest.getEmail());
                return ResponseEntity.ok().body(new SuccessMessage(PASSWORD_CHANGED_SUCCESS_MESSAGE));
            }
            log.error("message='getting error while changing password.', user={}", resetPasswordRequest.getEmail());
            return ResponseEntity.badRequest().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        } catch (Exception exception) {
            log.error("message='getting error while changing password.', user={}", resetPasswordRequest.getEmail());
            return ResponseEntity.internalServerError().body(new ServiceErrorMessage(DEFAULT_ERROR_MESSAGE));
        }
    }
}
