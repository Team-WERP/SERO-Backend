package com.werp.sero.security.config;

import com.werp.sero.security.service.EmployeeUserDetailsService;
import com.werp.sero.security.service.ClientEmployeeUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ClientEmployeeUserDetailsService clientEmployeeUserDetailsService;
    private final EmployeeUserDetailsService employeeUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public AuthenticationManager employeeAuthenticationManager(final PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(employeeUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public AuthenticationManager clientEmployeeAuthenticationManager(final PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(clientEmployeeUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Order(1)
    @Bean
    public SecurityFilterChain clientEmployeeSecurityFilterChain(final HttpSecurity http,
                                                                 @Qualifier("clientEmployeeAuthenticationManager") final AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .securityMatcher("/clients/**")
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clients/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
        ;

        return http.build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain employeeSecurityFilterChain(final HttpSecurity http,
                                                           final AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
        ;

        return http.build();
    }
}