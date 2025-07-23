package com.clinica_hipocrates.user_service.service.impl;

import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.repository.UserRepository;
import com.clinica_hipocrates.user_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));
    }

    @Override
    public User create(User user) throws DuplicateResourceException {
        if (repository.existsByDni(user.getDni())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese DNI.");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese email.");
        }
        return repository.save(user);
    }

    @Override
    public User update(Long id, User user) throws DuplicateResourceException, ResourceNotFoundException {
        User updatedUser = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));

        if (!updatedUser.getDni().equals(user.getDni()) && repository.existsByDni(user.getDni())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese DNI.");
        }
        if (!updatedUser.getEmail().equals(user.getEmail()) && repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese email.");
        }

        setUserFields(updatedUser, user);
        repository.save(updatedUser);
        return updatedUser;
    }

    private static void setUserFields(User updatedUser, User user) {
        updatedUser.setName(user.getName());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setAge(user.getAge());
        updatedUser.setDni(user.getDni());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setProfilePic(user.getProfilePic());
        updatedUser.setSpecificFields(user);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("El usuario con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }

    @Override
    public void validate(User user) {
        System.out.println("USUARIO");
        System.out.println(user);
        System.out.println();

        if (repository.existsByDni(user.getDni())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese DNI.");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese email.");
        }

        if (user.getUserType().equals(UserType.ESPECIALISTA)) {
            if (user.getSpecialities() == null || user.getSpecialities().isEmpty()) {
                throw new BadRequestException("[VALIDATION_ERROR] Un especialista debe tener al menos una especialidad.");
            }

            specialityService.validateSpecialities(user.getSpecialities().stream().map(Speciality::getId).toList());
        }

        if (user.getUserType().equals(UserType.PACIENTE)) {
            if (user.getHealthInsurance() == null) {
                throw new BadRequestException("[VALIDATION_ERROR] Un paciente debe tener obra social.");
            }
        }
    }


}
