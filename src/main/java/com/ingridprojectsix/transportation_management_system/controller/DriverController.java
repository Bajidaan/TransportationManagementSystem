package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.AccountBalanceDTO;
import com.ingridprojectsix.transportation_management_system.dto.DriverRegistrationRequest;
import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.dto.DriverUpdateRequest;
import com.ingridprojectsix.transportation_management_system.service.DriverService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    public ResponseEntity<String> createDriverDetails(@RequestBody DriverRegistrationRequest driverRequest) throws Exception {
        driverService.createDriverDetails(driverRequest);
        return new ResponseEntity<>("Driver details created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateBalance/{driverId}")
    public ResponseEntity<String> updateDriverBalance(@PathVariable Long driverId, @RequestBody AccountBalanceDTO amount) {
        driverService.updateDriverBalance(driverId, amount);
        return new ResponseEntity<>("Driver balance updated successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> allDrivers = driverService.getAllDrivers();
        return new ResponseEntity<>(allDrivers, HttpStatus.OK);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long driverId) {
        Driver driver = driverService.getDriverById(driverId);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PutMapping("/updateDriver")
    public ResponseEntity<Driver> updateDriverDetails(@RequestBody DriverUpdateRequest updateRequest) {
        try {
            Driver updatedDriver = driverService.updateDriverDetails(updateRequest);
            return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
