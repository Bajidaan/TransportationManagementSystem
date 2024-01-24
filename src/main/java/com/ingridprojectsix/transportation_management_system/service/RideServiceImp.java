package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import com.ingridprojectsix.transportation_management_system.repository.RidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return ridesRepository.findAll();
    }

    @Override
    public Optional<Rides> getRideById(long rideId) {
        return ridesRepository.findById(rideId);
    }

    @Override
    public List<Rides> getRidesByPassenger(long passengerId) {
        return ridesRepository.findRidesByPassengers(passengerId);
    }

    @Override
    public List<Rides> getRidesByDriver(long driverId) {
        return ridesRepository.findRidesByDrivers(driverId);
    }

    @Override
    public List<Rides> getRidesByStatus(RequestStatus status) {
        return ridesRepository.findRidesByStatus((status));
    }

    @Override
    public List<Rides> getRidesPerDay(LocalDateTime date) {
        return null;
    }

    @Override
    public List<Rides> getRidesByStartLocation(String startLocation) {
        return ridesRepository.findRidesByStartLocation(startLocation);
    }

    @Override
    public List<Rides> getRidesByEndLocation(String endLocation) {
        return ridesRepository.findRidesByEndLocation(endLocation);
    }

    @Override
    public Rides saveRide(Rides ride) {
        //call payment method
        return null;
    }

    @Override
    public String deleteRide(long id) {
        Optional<Rides> ride = getRideById(id);
        if(ride == null){
            return String.format("ride ingormation with id of %s is not available", +id);
        }else{
            return String.format("ride with id %s has been deleted successfully", + id);
        }
    }
}
