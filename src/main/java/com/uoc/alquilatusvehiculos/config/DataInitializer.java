package com.uoc.alquilatusvehiculos.config;

import com.uoc.alquilatusvehiculos.model.Role;
import com.uoc.alquilatusvehiculos.model.User;
import com.uoc.alquilatusvehiculos.repository.RoleRepository;
import com.uoc.alquilatusvehiculos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
        }

        if (userRepository.count() == 0) {
            User admin = new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "admin@admin.com",
                    true
            );
            admin.getRoles().add(roleRepository.findByName("ROLE_ADMIN").get());
            userRepository.save(admin);
        }
    }
}
