package com.krs.knowledgerevisingsystem.config;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitRolesConfig {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("STUDENT") == null) {
                Role studentRole = new Role();
                studentRole.setName("STUDENT");
                studentRole.setStatus(true);
                studentRole.setSpeciality("User Role");
                studentRole.setPriority(4L);
                roleRepository.save(studentRole);
            }

            if (roleRepository.findByName("TEACHER") == null) {
                Role teacherRole = new Role();
                teacherRole.setName("TEACHER");
                teacherRole.setStatus(true);
                teacherRole.setSpeciality("User Role");
                teacherRole.setPriority(3L);
                roleRepository.save(teacherRole);
            }

            if (roleRepository.findByName("MANAGER") == null) {
                Role managerRole = new Role();
                managerRole.setName("MANAGER");
                managerRole.setStatus(true);
                managerRole.setSpeciality("User Role");
                managerRole.setPriority(2L);
                roleRepository.save(managerRole);
            }

            if (roleRepository.findByName("ADMIN") == null) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole.setStatus(true);
                adminRole.setSpeciality("User Role");
                adminRole.setPriority(1L);
                roleRepository.save(adminRole);
            }
        };
    }
}