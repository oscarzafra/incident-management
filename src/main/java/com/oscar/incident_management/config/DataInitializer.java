package com.oscar.incident_management.config;

import com.oscar.incident_management.entity.Role;
import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.RoleRepository;
import com.oscar.incident_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
            Role technicianRole = roleRepository.findByName("ROLE_TECHNICIAN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_TECHNICIAN")));
            Role clientRole = roleRepository.findByName("ROLE_CLIENT")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_CLIENT")));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrador del sistema");
                admin.setEnabled(true);
                admin.setCreatedAt(LocalDateTime.now());
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("tecnico1").isEmpty()) {
                User tech = new User();
                tech.setUsername("tecnico1");
                tech.setPassword(passwordEncoder.encode("tecnico123"));
                tech.setFullName("Técnico Principal");
                tech.setEnabled(true);
                tech.setCreatedAt(LocalDateTime.now());
                tech.setRoles(Set.of(technicianRole));
                userRepository.save(tech);
            }

            if (userRepository.findByUsername("cliente1").isEmpty()) {
                User client = new User();
                client.setUsername("cliente1");
                client.setPassword(passwordEncoder.encode("cliente123"));
                client.setFullName("Cliente Demo");
                client.setEnabled(true);
                client.setCreatedAt(LocalDateTime.now());
                client.setRoles(Set.of(clientRole));
                userRepository.save(client);
            }
        };
    }
}