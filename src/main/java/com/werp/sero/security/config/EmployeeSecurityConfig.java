package com.werp.sero.security.config;

import com.werp.sero.security.handler.CustomAccessDeniedHandler;
import com.werp.sero.security.handler.CustomAuthenticationEntryPoint;
import com.werp.sero.security.jwt.JwtAuthenticationFilter;
import com.werp.sero.security.jwt.JwtExceptionFilter;
import com.werp.sero.security.jwt.JwtTokenProvider;
import com.werp.sero.security.service.EmployeeUserDetailsService;
import com.werp.sero.util.RedisUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class EmployeeSecurityConfig {
    private static final String[] WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/auth/login",
            "/actuator/health", // ★ AWS 로드밸런서 헬스체크용 (필수 추가)
            "/actuator/**",     // (선택) 다른 액추에이터 정보도 허용하려면 추가
            "/health"           // (혹시 몰라 기본 경로도 추가)            

    };

    private static final String[] AUTHORITY_LIST = {
            "AC_SYS",
            "AC_SAL",
            "AC_PRO",
            "AC_WHS"
    };

    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final EmployeeUserDetailsService employeeUserDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    @Primary
    @Bean
    public AuthenticationManager employeeAuthenticationManager() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(employeeUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Order(2)
    @Bean
    public SecurityFilterChain employeeFilterChain(final HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().hasAnyAuthority(AUTHORITY_LIST)
                )
                .authenticationManager(employeeAuthenticationManager())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
        ;

        
        return http.build();
    }
}
