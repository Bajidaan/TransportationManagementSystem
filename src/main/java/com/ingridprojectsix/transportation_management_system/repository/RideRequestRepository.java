package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    Optional<RideRequest> findByPassenger_PassengerId(Long Id);
}
