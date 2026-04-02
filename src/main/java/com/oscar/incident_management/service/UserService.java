package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<User> findTechnicians() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role ->
                                role.getName().equalsIgnoreCase("TECHNICIAN")
                                        || role.getName().equalsIgnoreCase("ROLE_TECHNICIAN")
                                        || role.getName().equalsIgnoreCase("TECNICO")
                                        || role.getName().equalsIgnoreCase("ROLE_TECNICO")
                        ))
                .toList();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}