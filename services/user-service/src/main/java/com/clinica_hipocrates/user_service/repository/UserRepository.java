package com.clinica_hipocrates.user_service.repository;

import com.clinica_hipocrates.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByDni(Integer dni);
    boolean existsByEmail(String email);
}
