package com.clinica_hipocrates.user_service.service;

import com.clinica_hipocrates.user_service.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findAllByIds(List<Long> ids);
    User findById(Long id);
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);
    void validate(User user);
}
