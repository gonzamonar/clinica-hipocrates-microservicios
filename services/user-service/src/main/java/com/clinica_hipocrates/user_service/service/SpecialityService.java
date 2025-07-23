package com.clinica_hipocrates.user_service.service;

import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.User;

import java.util.List;

public interface SpecialityService {
    List<Speciality> findAll();
    List<Speciality> findAllByIds(List<Long> ids);
    Speciality findById(Long id);
    List<User> findDoctorsBySpeciality(Long id);
    Speciality create(Speciality speciality);
    Speciality update(Long id, Speciality speciality);
    void delete(Long id);
    void validateSpecialities(List<Long> specialitiesIds);
}
