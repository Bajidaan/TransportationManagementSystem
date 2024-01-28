package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.exception.RideNotFoundException;
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
        List<Rides> rides = ridesRepository.findAll();
        if(rides.isEmpty()){
            throw  new RideNotFoundException("Rides with not found" );
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
            throw  new RideNotFoundException("Ride with id " +passengerId +" not found" );
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByDriver(long driverId) {
        List<Rides> rides = ridesRepository.findRidesByDrivers(driverId);
        if(rides.isEmpty()){
            throw  new RideNotFoundException("Ride by driver with id " +driverId +" not found" );
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByStatus(RequestStatus status) {
        List<Rides> rides = ridesRepository.findRidesByStatus(status);
        if(rides.isEmpty()){
            throw  new RideNotFoundException("Rides with status " +status +" not found" );
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesPerDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23,59,59);

        List<Rides> rides = ridesRepository.findRidesPerDay(startOfDay, endOfDay);
        if(rides.isEmpty()){
            throw  new RideNotFoundException("Rides on " +date +" not found" );
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByStartLocation(String startLocation) {
        List<Rides> rides = ridesRepository.findRidesByStartLocation(startLocation);
        if (rides.isEmpty()){
            throw  new RideNotFoundException("Ride with location " +startLocation +" not found" );
        }
        return rides;
    }

    @Override
    public List<Rides> getRidesByEndLocation(String endLocation) {
        List<Rides> rides = ridesRepository.findRidesByEndLocation(endLocation);
        if (rides.isEmpty()){
            throw  new RideNotFoundException("Ride with location " +endLocation +" not found" );
        }
        return rides;
    }

    @Override
    public Rides updateStatusToPending(long rideId) {
        return updateRideStatus(rideId, RequestStatus.PENDING);
    }

    @Override
    public Rides updateStatusToInProgress(long rideId) {
        return updateRideStatus(rideId, RequestStatus.IN_PROGRESS);
    }

    @Override
    public Rides updateStatusToCompleted(long rideId) {
        return updateRideStatus(rideId, RequestStatus.COMPLETED);
    }


    @Override
    public Rides updateRideStatus(long rideId, RequestStatus newStatus) {
        Optional<Rides> existingRide = this.getRideById(rideId);

        if(existingRide.isPresent()){
            Rides ride = existingRide.get();

            ride.setStatus(newStatus);

            return ridesRepository.save(ride);
        }else{
           throw  new RideNotFoundException("Ride with id " +rideId +" not found" );
        }

    }

    @Override
    public String deleteRide(long rideId) {
        Optional<Rides> ride = getRideById(rideId);
        if(ride.isEmpty()){
            throw  new RideNotFoundException("Ride with id " +rideId +" not found" );
        }else{
            return String.format("ride with id %s has been deleted successfully", + rideId);
        }
    }
}
