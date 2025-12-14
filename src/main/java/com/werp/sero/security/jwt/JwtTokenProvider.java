package com.werp.sero.security.jwt;

import com.werp.sero.security.dto.CustomUserDetails;
import com.werp.sero.security.dto.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKeyString;

    @Value("${jwt.access-token-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private static final String GRANT_TYPE = "Bearer ";

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateAccessToken(final Authentication authentication) {
        final CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final String accessToken = Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim("auth", authorities)
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return new JwtToken(GRANT_TYPE + accessToken, authorities, ACCESS_TOKEN_EXPIRATION_TIME);
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
}