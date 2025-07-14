package com.clinica_hipocrates.appointment_service.repository;

import com.clinica_hipocrates.appointment_service.model.CachedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachedUserRepository extends JpaRepository<CachedUser, Long> {
    boolean existsByIdAndUserType(Long id, String userType);
}
