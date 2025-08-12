package com.clinica_hipocrates.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilsConfig {

    @Bean
    public JwtUtils jwtUtils(@Value("${security.jwt.secret}") String secret) {
        return new JwtUtils(secret);
    }

    @Bean
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
}
