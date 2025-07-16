package com.clinica_hipocrates.auth_service.security;

import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository repository;

    public AuthUserDetailsService(AuthUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AuthUserDetails(user);
    }
}
