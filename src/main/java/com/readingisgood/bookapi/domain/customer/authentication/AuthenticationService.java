package com.readingisgood.bookapi.domain.customer.authentication;

import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.accesstoken.impl.AccessToken;
import com.readingisgood.bookapi.security.accesstoken.impl.MailValidationToken;
import com.readingisgood.bookapi.domain.common.exception.UserActivationNeededException;
import com.readingisgood.bookapi.domain.customer.authentication.model.LoginResponse;
import com.readingisgood.bookapi.domain.customer.authentication.model.UserRegistrationRequest;
import com.readingisgood.bookapi.domain.customer.authentication.model.UserRegistrationResponse;
import com.readingisgood.bookapi.security.userdetail.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class AuthenticationService {

    @Value("${api.security.admin_user}")
    private String adminUser;

    @Value("${api.security.admin_password}")
    private String adminPassword;

    private final PasswordEncoder bcryptEncoder;
    private final CustomerService userService;
    private final MailValidationToken mailValidationToken;

    private final UserDetailService userDetailService;
    private final AccessToken accessJWTToken;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder bcryptEncoder,
                                 CustomerService userService,
                                 MailValidationToken mailValidationToken,
                                 UserDetailService userDetailService,
                                 AccessToken accessJWTToken,
                                 AuthenticationManager authenticationManager) {
        this.bcryptEncoder = bcryptEncoder;
        this.userService = userService;
        this.mailValidationToken = mailValidationToken;
        this.userDetailService = userDetailService;
        this.accessJWTToken = accessJWTToken;
        this.authenticationManager = authenticationManager;
    }

    @PostConstruct
    private void init() {
        if (userService.findAll().isEmpty()) {
            userService.save(createAdminUser());
        }
    }


    private CustomerEntity createAdminUser() {
        CustomerEntity userEntity = new CustomerEntity();
        userEntity.setName("admin");
        userEntity.setSurname("admin");
        userEntity.setPassword(bcryptEncoder.encode(adminPassword));
        userEntity.setEmail(adminUser);
        userEntity.setActivated(true);
        userEntity.setStatus(Status.ACTIVE.value);
        userEntity.setRole(RoleType.ADMIN.value);
        return userEntity;
    }

    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        CustomerEntity userEntity = createUser(userRegistrationRequest);
        final CustomerEntity savedUserEntity = userService.save(userEntity);
        UserRegistrationResponse userRegistrationResponse = createUserResponse(savedUserEntity);
        sendRegisterMail(userRegistrationRequest.getEmail());
        return userRegistrationResponse;
    }

    @Transactional
    public LoginResponse login(String email, String password) throws AuthenticationException {
        Authentication authenticationResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authenticationResult.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authenticationResult.getPrincipal();
            final CustomerEntity userEntity = userService.findByEmail(userDetails.getUsername());
            if (!userEntity.isActivated() || !userEntity.getStatus().equals(Status.ACTIVE.toString())) {
                throw new UserActivationNeededException();
            }
            final RoleType userRole = getUserRole(email);
            userEntity.setRole(userRole.value);
            userService.save(userEntity);
            final CustomerResponse userResponse = CustomerMapper.INSTANCE.mapEntityToResponse(userEntity);
            final String token = accessJWTToken.generateToken(userDetails, userRole);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUser(userResponse);
            return loginResponse;
        }
        throw new BadCredentialsException("Incorrect Credentials");
    }

    private RoleType getUserRole(String email) {
        if (email.equalsIgnoreCase(adminUser)) {
            return RoleType.ADMIN;
        }
        return RoleType.USER;
    }

    public boolean activateAccount(String token, String userEmail) {
        final boolean activationResult = mailValidationToken.validateToken(token, userEmail);
        if (activationResult) {
            CustomerEntity userEntity = userService.findByEmail(userEmail);
            userEntity.setActivated(true);
            userEntity.setStatus(Status.ACTIVE.value);
            userService.save(userEntity);
        }
        return activationResult;
    }

    private void sendRegisterMail(String savingUserEmail) {
        final String activationToken = mailValidationToken.generateToken(userDetailService.loadUserByUsername(savingUserEmail), RoleType.USER);
        //registerMailSender.sendMail(savingUserEmail, activationToken);
    }

    private UserRegistrationResponse createUserResponse(CustomerEntity saveEntity) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setName(saveEntity.getName());
        userRegistrationResponse.setSurname(saveEntity.getSurname());
        userRegistrationResponse.setEmail(saveEntity.getEmail());
        userRegistrationResponse.setBirthDate(saveEntity.getBirthDate());
        userRegistrationResponse.setRole(saveEntity.getRole());
        return userRegistrationResponse;
    }

    private CustomerEntity createUser(UserRegistrationRequest userRegistrationRequest) {
        CustomerEntity userEntity = new CustomerEntity();
        userEntity.setName(userRegistrationRequest.getName());
        userEntity.setSurname(userRegistrationRequest.getSurname());
        userEntity.setEmail(userRegistrationRequest.getEmail());
        userEntity.setAddress(userRegistrationRequest.getAddress());
        userEntity.setBirthDate(userRegistrationRequest.getBirthDate());
        userEntity.setRole(RoleType.USER.value);
        userEntity.setActivated(false);
        userEntity.setPhone(userEntity.getPhone());
        userEntity.setPassword(bcryptEncoder.encode(userRegistrationRequest.getPassword()));
        return userEntity;
    }
}