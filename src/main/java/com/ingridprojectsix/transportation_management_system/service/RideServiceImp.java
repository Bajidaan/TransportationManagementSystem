package com.ingridprojectsix.transportation_management_system.service;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import com.ingridprojectsix.transportation_management_system.repository.RidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImp implements RidesService {

    private final RidesRepository ridesRepository;

    @Autowired
    public RideServiceImp(RidesRepository ridesRepository) {
        this.ridesRepository = ridesRepository;
    }

    @Override
    public List<Rides> getAllRides() {
        List<Rides> rides = ridesRepository.findAll();
        if(rides.isEmpty()){
            return Collections.emptyList();
        }
        return rides;
    }

    @Override
    public Optional<Rides> getRideById(long rideId) {
        return ridesRepository.findById(rideId);
    }

    @Override
    public List<Rides> getRidesByPassenger(long passengerId) {
        List<Rides> rides = ridesRepository.findRidesByPassengers(passengerId);
        if(rides.isEmpty()){
            return Collections.emptyList();
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByDriver(long driverId) {
        List<Rides> rides = ridesRepository.findRidesByDrivers(driverId);
        if(rides.isEmpty()){
            return Collections.emptyList();
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByStatus(RequestStatus status) {
        List<Rides> rides = ridesRepository.findRidesByStatus(status);
        if(rides.isEmpty()){
            return Collections.emptyList();
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesPerDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23,59,59);

        List<Rides> rides = ridesRepository.findRidesPerDay(startOfDay, endOfDay);
        if(rides.isEmpty()){
            return Collections.emptyList();
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByStartLocation(String startLocation) {
        List<Rides> rides = ridesRepository.findRidesByStartLocation(startLocation);
        if (rides.isEmpty()){
            return  Collections.emptyList();
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByEndLocation(String endLocation) {
        List<Rides> rides = ridesRepository.findRidesByEndLocation(endLocation);
        if (rides.isEmpty()){
            return  Collections.emptyList();
        }
        return rides;
    }

    @Override
    public Rides updateRide(Rides ride, long rideId) {

        Optional<Rides> rideExist = getRideById(rideId);
        if(rideExist.isPresent()){
            Rides existingRide = rideExist.get();
          //if passenger order for a ride, update the status to pending
            //if driver accept the ride request, update the status to inprogress
            //if pssenger pay for the ride, update status to completed

            existingRide.setStatus(ride.getStatus());

        }

        return null;
    }

    @Override
    public String deleteRide(long rideId) {
        Optional<Rides> ride = getRideById(rideId);
        if(ride.isEmpty()){
            return String.format("ride information with id of %s is not available", +rideId);
        }else{
            return String.format("ride with id %s has been deleted successfully", + rideId);
        }
    }
}
