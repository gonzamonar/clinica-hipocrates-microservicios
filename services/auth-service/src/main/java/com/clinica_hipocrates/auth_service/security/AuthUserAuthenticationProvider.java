package com.clinica_hipocrates.auth_service.security;

import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.repository.AuthUserRepository;
import com.clinica_hipocrates.common.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthUserAuthenticationProvider implements AuthenticationProvider {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthUserAuthenticationProvider(
            AuthUserRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        AuthUser user = repository.findByEmail(email).orElse(null);

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPwd())) {
            throw new UnauthorizedException("Usuario o contraseña incorrectos.");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getType().name()));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
