package com.uevitondev.deliveryapp.runner;

import com.uevitondev.deliveryapp.enums.TypeUser;
import com.uevitondev.deliveryapp.model.PhysicalUser;
import com.uevitondev.deliveryapp.model.Role;
import com.uevitondev.deliveryapp.repository.PhysicalUserRepository;
import com.uevitondev.deliveryapp.repository.RoleRepository;
import com.uevitondev.deliveryapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhysicalUserRepository physicalPersonRepository;


    public DatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository, PhysicalUserRepository physicalPersonRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.physicalPersonRepository = physicalPersonRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // role
        Role roleClient = new Role(1L, "ROLE_CLIENT");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        roleRepository.saveAll(List.of(roleClient, roleAdmin));


        // user - role
        PhysicalUser physicalUser = new PhysicalUser();
        physicalUser.setId(1L);
        physicalUser.setUsername("uevitondev");
        physicalUser.setName("Ueviton");
        physicalUser.setLastName("Santos");
        physicalUser.setEmail("ueviton@gmail.com");
        physicalUser.setPassword("$2a$10$Y7fk59/1Pg.ig0Goy0yTS.5RgKD18N5J3MYCo5bPYzVpslJqfr4uu");
        physicalUser.setCreatedAt(LocalDateTime.now());
        physicalUser.setTypeUser(TypeUser.PHYSICAL);
        physicalUser.setCpf("02000514236");
        physicalUser.setEnabled(true);
        physicalUser.getRoles().addAll(List.of(roleClient, roleAdmin));

        physicalPersonRepository.save(physicalUser);


    }
}
