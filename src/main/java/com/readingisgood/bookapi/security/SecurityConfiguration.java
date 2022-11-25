package com.readingisgood.bookapi.security;

import com.readingisgood.bookapi.security.accessfilter.AccessTokenFilter;
import com.readingisgood.bookapi.security.userdetail.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTHENTICATION_WHITELIST = {
            // -- Swagger UI
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",

            "/h2-ui/**",

            // other public endpoints of  API
            "/api/login",
            "/api/admin-login",
            "/api/register",
            "/api/activate-account",
            "/api/reset-password",
            "/api/send-reset-password-mail"
    };

    private final UserDetailServiceImpl jwtUserDetailService;
    private final AccessTokenFilter accessTokenFilter;

    public SecurityConfiguration(
            UserDetailServiceImpl jwtUserDetailService,
            AccessTokenFilter accessTokenFilter) {
        this.jwtUserDetailService = jwtUserDetailService;
        this.accessTokenFilter = accessTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailService::loadUserByUsername);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http =
                http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and();

        http = setUnauthorizedExceptionHandler(http);
        authorizeUrls(http);
        applyFilters(http);
        http.headers().frameOptions().disable();
    }

    private HttpSecurity setUnauthorizedExceptionHandler(HttpSecurity http) throws Exception {
        http =
                http.exceptionHandling()
                        .authenticationEntryPoint(
                                (request, response, ex) ->
                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED,
                                                ex.getMessage()))
                        .and();
        return http;
    }

    private void authorizeUrls(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTHENTICATION_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    private void applyFilters(HttpSecurity http) {
        http.addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Set password encoding schema
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
