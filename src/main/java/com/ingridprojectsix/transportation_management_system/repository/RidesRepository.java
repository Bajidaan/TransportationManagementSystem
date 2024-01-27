package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RidesRepository extends JpaRepository<Rides, Long> {
    List<Rides> findRidesByPassengers(long passengerId);

    List<Rides> findRidesByDrivers(long driverId);

    List<Rides> findRidesByStatus(RequestStatus requestStatus);

    List<Rides> findRidesByStartLocation(String startLocation);

    List<Rides> findRidesByEndLocation(String endLocation);

    List<Rides> findRidesPerDay(LocalDateTime date);
}
