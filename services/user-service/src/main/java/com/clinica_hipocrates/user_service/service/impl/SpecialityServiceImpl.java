package com.clinica_hipocrates.user_service.service.impl;

import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.clinica_hipocrates.user_service.model.Specialist;
import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.repository.SpecialistRepository;
import com.clinica_hipocrates.user_service.repository.SpecialityRepository;
import com.clinica_hipocrates.user_service.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    private SpecialityRepository repository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public List<Speciality> findAll() {
        return repository.findAll();
    }

    @Override
    public Speciality findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La Especialidad con id " + id + " no existe."));
    }

    @Override
    public List<Specialist> findDoctorsBySpeciality(Long id) {
        return specialistRepository.findBySpecialities_Id(id);
    }

    @Override
    public Speciality create(Speciality speciality) {
        if (repository.existsByNameIgnoreCase(speciality.getName())) {
            throw new DuplicateResourceException("La Especialidad ya existe.");
        }
        return repository.save(speciality);
    }

    @Override
    public Speciality update(Long id, Speciality speciality) {
        if (repository.existsByNameIgnoreCase(speciality.getName())) {
            throw new DuplicateResourceException("La Especialidad ya existe.");
        }
        Speciality updatedSpeciality = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La Especialidad con id " + id + " no existe."));
        updatedSpeciality.setName(speciality.getName());
        repository.save(updatedSpeciality);
        return updatedSpeciality;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("La Especialidad con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}
