package com.clinica_hipocrates.user_service.repository;

import com.clinica_hipocrates.user_service.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    List<Specialist> findBySpecialities_Id(Long specialityId);
}