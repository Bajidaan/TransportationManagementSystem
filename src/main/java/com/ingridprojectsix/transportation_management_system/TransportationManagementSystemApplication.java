package com.ingridprojectsix.transportation_management_system;

import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.repository.PassengerRepository;
import com.ingridprojectsix.transportation_management_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class TransportationManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportationManagementSystemApplication.class, args);
    }

}
