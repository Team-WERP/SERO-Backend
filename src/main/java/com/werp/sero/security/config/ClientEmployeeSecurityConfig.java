package com.werp.sero.security.config;

import com.werp.sero.security.handler.CustomAccessDeniedHandler;
import com.werp.sero.security.handler.CustomAuthenticationEntryPoint;
import com.werp.sero.security.jwt.JwtAuthenticationFilter;
import com.werp.sero.security.jwt.JwtExceptionFilter;
import com.werp.sero.security.jwt.JwtTokenProvider;
import com.werp.sero.security.service.ClientEmployeeUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class ClientEmployeeSecurityConfig {
    private static final String AUTHORITY = "AC_CLI";

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final ClientEmployeeUserDetailsService clientEmployeeUserDetailsService;

    @Bean("clientEmployeeAuthenticationManager")
    public AuthenticationManager clientEmployeeAuthenticationManager() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(clientEmployeeUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Order(1)
    @Bean
    public SecurityFilterChain clientEmployFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .securityMatcher("/clients/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clients/auth/login").permitAll()
                        .requestMatchers("/clients/**").hasAuthority(AUTHORITY)
                        .anyRequest().authenticated()
                )
                .authenticationManager(clientEmployeeAuthenticationManager())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
        ;

        return http.build();
    }
}