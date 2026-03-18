package com.oscar.incident_management.config;

import com.oscar.incident_management.entity.Role;
import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.RoleRepository;
import com.oscar.incident_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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

            Role clientRole = roleRepository.findByName("ROLE_CLIENT")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_CLIENT")));

            Role technicianRole = roleRepository.findByName("ROLE_TECHNICIAN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_TECHNICIAN")));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@incidencias.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrador");
                admin.setEnabled(true);
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("cliente").isEmpty()) {
                User client = new User();
                client.setUsername("cliente");
                client.setEmail("cliente@incidencias.com");
                client.setPassword(passwordEncoder.encode("cliente123"));
                client.setFullName("Cliente Demo");
                client.setEnabled(true);
                client.setRoles(Set.of(clientRole));
                userRepository.save(client);
            }

            if (userRepository.findByUsername("tecnico").isEmpty()) {
                User technician = new User();
                technician.setUsername("tecnico");
                technician.setEmail("tecnico@incidencias.com");
                technician.setPassword(passwordEncoder.encode("tecnico123"));
                technician.setFullName("Técnico Demo");
                technician.setEnabled(true);
                technician.setRoles(Set.of(technicianRole));
                userRepository.save(technician);
            }
        };
    }
}