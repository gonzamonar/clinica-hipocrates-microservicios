package com.clinica_hipocrates.auth_service.repository;

import com.clinica_hipocrates.auth_service.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    boolean existsByEmail(String email);
    Optional<AuthUser> findByEmail(String email);
    Optional<AuthUser> findByAlias(Long alias);

    @Query(value = "SELECT nextval('user_alias_seq')", nativeQuery = true)
    Long getNextAlias();
}
