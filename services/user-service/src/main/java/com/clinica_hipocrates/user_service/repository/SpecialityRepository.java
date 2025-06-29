package com.clinica_hipocrates.user_service.repository;

import com.clinica_hipocrates.user_service.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    boolean existsByNameIgnoreCase(String name);
}
