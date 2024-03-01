package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.UpdateRideStatus;
import com.ingridprojectsix.transportation_management_system.exception.RideNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.domain.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.Rides;
import com.ingridprojectsix.transportation_management_system.service.RidesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rides")
public class RidesController {

    private final RidesService ridesService;

    @Autowired
    public RidesController(RidesService ridesService) {
        this.ridesService = ridesService;
    }

    @GetMapping
    public ResponseEntity<List<Rides>> getAllRides(){
        List<Rides> rides = ridesService.getAllRides();
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<Rides> getRideById(@PathVariable long rideId){
        Optional<Rides> rides = ridesService.getRideById(rideId);
       return  rides.map(ride ->new ResponseEntity<>(ride, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{passengerId}")
    public  ResponseEntity<List<Rides>> getRidesByPassenger(@PathVariable long passengerId){
        List<Rides> rides = ridesService.getRidesByPassenger(passengerId);
        return  new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<List<Rides>> getRidesByDriver(@PathVariable long driverId){
        List<Rides> rides = ridesService.getRidesByDriver(driverId);

        return  new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Rides>> getRidesByStatus(@PathVariable RequestStatus status) {
        List<Rides> rides = ridesService.getRidesByStatus(status);

        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    public  ResponseEntity<List<Rides>> getRidesPerDay(@PathVariable LocalDateTime date){
        List<Rides> rides = ridesService.getRidesPerDay(date);

        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{startLocation}")
    public  ResponseEntity<List<Rides>> getByStartLocation(@PathVariable String startLocation){
        List<Rides> rides = ridesService.getRidesByStartLocation(startLocation);

        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @GetMapping("/{endLocation}")
    public  ResponseEntity<List<Rides>> getByEndLocation(@PathVariable String endLocation){
        List<Rides> rides = ridesService.getRidesByEndLocation(endLocation);

        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{rideId}")
    public ResponseEntity<Rides> updateStatus(@PathVariable long rideId,
                                              @RequestBody UpdateRideStatus newStatus){

        Rides rideDetails = ridesService.updateStatus(rideId, newStatus);
        return new ResponseEntity<>(rideDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<String> deleteRide(@PathVariable long rideId){
        String response = ridesService.deleteRide(rideId);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<String> handleRidesNotFoundException(RideNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
