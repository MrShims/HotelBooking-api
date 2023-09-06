package com.example.hotelbooking;

import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class HotelBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository)
    {
        return args -> {

            Role role=new Role();
            role.setName("ROLE_USER");
            Role role1=new Role();
            role1.setName("ROLE_ADMIN");





            roleRepository.save(role);
            roleRepository.save(role1);

        };
    }

}
