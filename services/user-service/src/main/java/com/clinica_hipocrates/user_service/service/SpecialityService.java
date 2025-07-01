package com.clinica_hipocrates.user_service.service;

import com.clinica_hipocrates.user_service.model.Specialist;
import com.clinica_hipocrates.user_service.model.Speciality;
import java.util.List;
import java.util.Optional;

public interface SpecialityService {
    List<Speciality> findAll();
    Speciality findById(Long id);
    List<Specialist> findDoctorsBySpeciality(Long id);
    Speciality create(Speciality speciality);
    Speciality update(Long id, Speciality speciality);
    void delete(Long id);
}
