package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.domain.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RidesRepository extends JpaRepository<Rides, Long> {
    @Query("SELECT r FROM Rides r WHERE r.passengers.passengerId = ?1")
    List<Rides> findRidesByPassengers(long passengerId);

    @Query("SELECT r FROM Rides r WHERE r.drivers.driverId = ?1")
    List<Rides> findRidesByDrivers(long driverId);

    List<Rides> findRidesByStatus(RequestStatus requestStatus);

    List<Rides> findRidesByStartLocation(String startLocation);

    List<Rides> findRidesByEndLocation(String endLocation);

    @Query("SELECT r FROM Rides r WHERE r.startTime >= ?1 AND r.endTime <= ?2")
    List<Rides> findRidesPerDay(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
