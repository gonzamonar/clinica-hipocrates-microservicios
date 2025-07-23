package com.clinica_hipocrates.user_service.service.impl;

import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.repository.SpecialityRepository;
import com.clinica_hipocrates.user_service.repository.UserRepository;
import com.clinica_hipocrates.user_service.service.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository repository;
    private final UserRepository userRepository;

    public SpecialityServiceImpl(SpecialityRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

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
    public List<Speciality> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<User> findDoctorsBySpeciality(Long id) {
        return userRepository.findBySpecialities_Id(id);
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

    @Override
    public void validateSpecialities(List<Long> specialitiesIds) {
        specialitiesIds.forEach(id -> {
            if (!repository.existsById(id)) {
                throw new BadRequestException("[NOT_FOUND] La especialidad con id " + id + " no existe.");
            }
        });
    }
}
