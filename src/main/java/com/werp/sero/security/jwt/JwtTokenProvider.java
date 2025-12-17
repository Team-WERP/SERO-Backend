package com.werp.sero.security.jwt;

import com.werp.sero.security.principal.CustomUserDetails;
import com.werp.sero.security.dto.JwtToken;
import com.werp.sero.security.jwt.exception.ExpiredTokenException;
import com.werp.sero.security.jwt.exception.InvalidTokenException;
import com.werp.sero.security.service.ClientEmployeeUserDetailsService;
import com.werp.sero.security.service.EmployeeUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String CLIENT_PERMISSION ="AC_CLI";

    private static long ACCESS_TOKEN_EXPIRATION_TIME;
    private static long REFRESH_TOKEN_EXPIRATION_TIME;

    private final SecretKey secretKey;
    private final EmployeeUserDetailsService employeeUserDetailsService;
    private final ClientEmployeeUserDetailsService clientEmployeeUserDetailsService;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret-key}") final String secretKeyString,
                            @Value("${jwt.access-token-expiration-time}") final long accessTokenExpirationTime,
                            @Value("${jwt.refresh-token-expiration-time}") final long refreshTokenExpirationTime,
                            final EmployeeUserDetailsService employeeUserDetailsService,
                            @Qualifier("clientEmployeeUserDetailsService") final ClientEmployeeUserDetailsService clientEmployeeUserDetailsService) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.ACCESS_TOKEN_EXPIRATION_TIME = accessTokenExpirationTime;
        this.REFRESH_TOKEN_EXPIRATION_TIME = refreshTokenExpirationTime;

        this.employeeUserDetailsService = employeeUserDetailsService;
        this.clientEmployeeUserDetailsService = clientEmployeeUserDetailsService;
    }

    public JwtToken generateAccessToken(final Authentication authentication) {
        final CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final String accessToken = Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return new JwtToken(accessToken, authorities, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public JwtToken generateRefreshToken(final Authentication authentication) {
        final CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        final String refreshToken = Jwts.builder()
                .subject(customUserDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return new JwtToken(refreshToken, null, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public Claims parseClaims(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new InvalidTokenException();
        }

        List<GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        final String username = claims.getSubject();

        UserDetails userDetails;

        if (claims.get(AUTHORITIES_KEY).toString().equals(CLIENT_PERMISSION)) {
            userDetails = clientEmployeeUserDetailsService.loadUserByUsername(username);
        } else {
            userDetails = employeeUserDetailsService.loadUserByUsername(username);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    public void validateToken(final String accessToken) {
        try {
            parseClaims(accessToken);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT Token: {}", e);
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token: {}", e);
            throw new ExpiredTokenException();
        } catch (SignatureException e) {
            log.error("Signature Exception: {}", e);
            throw new InvalidTokenException();
        } catch (JwtException e) {
            log.error("Jwt exception: {}", e);
            throw new InvalidTokenException();
        }
    }
}