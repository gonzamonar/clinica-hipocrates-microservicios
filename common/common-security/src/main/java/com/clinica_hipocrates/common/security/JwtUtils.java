package com.clinica_hipocrates.common.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    private final String secret;

    public JwtUtils(@Value("${security.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Long id = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        Boolean enabled = claims.get("enabled", Boolean.class);
        Boolean verified = claims.get("verified", Boolean.class);
        String role = claims.get("role", String.class);

        AuthUser principal = new AuthUser(id, email, enabled, verified, role);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
