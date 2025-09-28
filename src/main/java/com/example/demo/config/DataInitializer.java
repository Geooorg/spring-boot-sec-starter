package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Create roles if they don't exist
        Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ADMIN")));
        Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> roleRepository.save(new Role("USER")));
        Role guestRole = roleRepository.findByName("GUEST")
            .orElseGet(() -> roleRepository.save(new Role("GUEST")));

        // Create admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(List.of(adminRole));
            userRepository.save(admin);
        }

        // Create regular user
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(List.of(userRole));
            userRepository.save(user);
        }

        // Create guest user
        if (!userRepository.existsByUsername("guest")) {
            User guest = new User();
            guest.setUsername("guest");
            guest.setPassword(passwordEncoder.encode("guest123"));
            guest.setRoles(List.of(guestRole));
            userRepository.save(guest);
        }
    }
}
