package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.model.Rides;
import com.ingridprojectsix.transportation_management_system.service.RideServiceImp;
import com.ingridprojectsix.transportation_management_system.service.RidesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
