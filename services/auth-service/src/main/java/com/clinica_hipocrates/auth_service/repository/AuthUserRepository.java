package com.clinica_hipocrates.auth_service.repository;

import com.clinica_hipocrates.auth_service.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    boolean existsByEmail(String email);
    Optional<AuthUser> findByEmail(String email);
}
