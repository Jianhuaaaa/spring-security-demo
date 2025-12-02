package com.jsun.security.configuration;

import com.jsun.security.entity.Role;
import com.jsun.security.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.count() == 0) {
            log.info("Initializing default roles...");
            Role userRole = Role.builder()
                    .name(Role.RoleName.ROLE_USER)
                    .build();
            roleRepository.save(userRole);
            Role adminRole = Role.builder()
                    .name(Role.RoleName.ROLE_ADMIN)
                    .build();
            roleRepository.save(adminRole);

            log.info("Default roles initialized successfully");
        }
    }
}
