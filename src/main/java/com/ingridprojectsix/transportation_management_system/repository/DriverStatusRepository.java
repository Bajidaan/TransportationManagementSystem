package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverStatusRepository extends JpaRepository<DriverStatus, Long> {
    List<DriverStatus> findByAvailability(boolean isAvailable);
}
