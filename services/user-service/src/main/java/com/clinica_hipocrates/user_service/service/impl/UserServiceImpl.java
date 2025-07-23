package com.clinica_hipocrates.user_service.service.impl;

import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.clinica_hipocrates.user_service.event.UserCreatedEvent;
import com.clinica_hipocrates.user_service.messaging.publisher.UserCreatedEventPublisher;
import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.model.UserType;
import com.clinica_hipocrates.user_service.repository.SpecialityRepository;
import com.clinica_hipocrates.user_service.repository.UserRepository;
import com.clinica_hipocrates.user_service.service.SpecialityService;
import com.clinica_hipocrates.user_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final SpecialityService specialityService;
    private final UserCreatedEventPublisher userEventPublisher;

    public UserServiceImpl(UserRepository repository,
                           SpecialityService specialityService,
                           UserCreatedEventPublisher userEventPublisher) {
        this.repository = repository;
        this.specialityService = specialityService;
        this.userEventPublisher = userEventPublisher;
    }


    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
         return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));
    }

    @Override
    public User create(User user) throws DuplicateResourceException {
        validate(user);
        User newUser = repository.save(user);
        userEventPublisher.publishUserCreatedEvent(UserCreatedEvent.loadEvent(newUser));
        return newUser;
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
