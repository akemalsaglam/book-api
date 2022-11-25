package com.readingisgood.bookapi.domain.customer.authentication;

import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import com.readingisgood.bookapi.domain.customer.CustomerService;
import com.readingisgood.bookapi.domain.customer.model.CustomerMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerResponse;
import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.accesstoken.impl.AccessToken;
import com.readingisgood.bookapi.domain.common.exception.UserActivationNeededException;
import com.readingisgood.bookapi.domain.customer.authentication.model.LoginResponse;
import com.readingisgood.bookapi.domain.customer.authentication.model.CustomerRegistrationRequest;
import com.readingisgood.bookapi.domain.customer.authentication.model.CustomerRegistrationResponse;
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
    private final CustomerService customerService;
    private final AccessToken accessJWTToken;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder bcryptEncoder,
                                 CustomerService userService,
                                 AccessToken accessJWTToken,
                                 AuthenticationManager authenticationManager) {
        this.bcryptEncoder = bcryptEncoder;
        this.customerService = userService;
        this.accessJWTToken = accessJWTToken;
        this.authenticationManager = authenticationManager;
    }

    @PostConstruct
    private void init() {
        if (customerService.findAll().isEmpty()) {
            customerService.save(createAdminUser());
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
    public CustomerRegistrationResponse register(CustomerRegistrationRequest userRegistrationRequest) {
        CustomerEntity userEntity = createUser(userRegistrationRequest);
        final CustomerEntity savedUserEntity = customerService.save(userEntity);
        return createUserResponse(savedUserEntity);
    }

    private CustomerEntity createUser(CustomerRegistrationRequest userRegistrationRequest) {
        CustomerEntity userEntity = new CustomerEntity();
        userEntity.setName(userRegistrationRequest.getName());
        userEntity.setSurname(userRegistrationRequest.getSurname());
        userEntity.setEmail(userRegistrationRequest.getEmail());
        userEntity.setRole(RoleType.USER.value);

        /**
         * marked it as true, but we can set it to false for initial step,
         * then we can try to send activation mail to user e-mail adress in order to validate user mail.
         **/
        userEntity.setActivated(true);
        userEntity.setStatus(Status.ACTIVE.value);

        userEntity.setPassword(bcryptEncoder.encode(userRegistrationRequest.getPassword()));
        return userEntity;
    }

    private CustomerRegistrationResponse createUserResponse(CustomerEntity saveEntity) {
        CustomerRegistrationResponse userRegistrationResponse = new CustomerRegistrationResponse();
        userRegistrationResponse.setName(saveEntity.getName());
        userRegistrationResponse.setSurname(saveEntity.getSurname());
        userRegistrationResponse.setEmail(saveEntity.getEmail());
        userRegistrationResponse.setRole(saveEntity.getRole());
        return userRegistrationResponse;
    }

    @Transactional
    public LoginResponse login(String email, String password) throws AuthenticationException {
        Authentication authenticationResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authenticationResult.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authenticationResult.getPrincipal();
            final CustomerEntity userEntity = customerService.findByEmail(userDetails.getUsername());
            final RoleType userRole = getUserRole(email);
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
}
