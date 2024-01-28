package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import com.ingridprojectsix.transportation_management_system.repository.RidesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RidesService {

    List<Rides> getAllRides();

    Optional<Rides> getRideById(long rideId);

    List<Rides> getRidesByPassenger(long passengerId);

    List<Rides> getRidesByDriver(long driverId);

    List<Rides> getRidesByStatus(RequestStatus status);

    List<Rides> getRidesPerDay(LocalDateTime date);

    List<Rides> getRidesByStartLocation(String startLocation);

    List<Rides> getRidesByEndLocation(String endLocation);

    Rides updateRideStatus(long rideId, RequestStatus newStatus);

    Rides updateStatusToPending(long rideId);

    Rides updateStatusToInProgress(long rideId);

    Rides updateStatusToCompleted(long rideId);


    String deleteRide(long rideId);
}
