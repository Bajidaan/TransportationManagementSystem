package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PassengerRepository  extends JpaRepository<Passenger, Long> {
}
