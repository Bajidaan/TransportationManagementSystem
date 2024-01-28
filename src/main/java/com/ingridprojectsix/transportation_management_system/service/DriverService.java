package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.AccountBalanceDTO;
import com.ingridprojectsix.transportation_management_system.exception.DriverNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.DriverRegistrationRequest;
import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.model.DriverUpdateRequest;
import com.ingridprojectsix.transportation_management_system.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public void createDriverDetails(DriverRegistrationRequest driverRequest){
        Driver driver = convertToDriverEntity(driverRequest);
        driverRepository.save(driver);
    }

    public Driver convertToDriverEntity(DriverRegistrationRequest driverRequest) {
        Driver driver = new Driver();
        driver.setFirstName(driverRequest.getFirstName());
        driver.setLastName(driverRequest.getLastName());
        driver.setLicenseNumber(driverRequest.getLicenseNumber());
        driver.setPlateNumber(driverRequest.getPlateNumber());
        driver.setCarModel(driverRequest.getCarModel());
        driver.setLocation(driverRequest.getLocation());
        return driver;
    }

    public void updateDriverBalance(Long driverId, AccountBalanceDTO amount) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(DriverNotFoundException::new);
        driver.setAccountBalance(driver.getAccountBalance() + amount.amount());
        driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public Driver updateDriverDetails(DriverUpdateRequest updateRequest) {
        Optional<Driver> optionalDriver = driverRepository.findById(updateRequest.getDriverId());

        if (optionalDriver.isPresent()) {
            Driver existingDriver = getDriver(updateRequest, optionalDriver);

            return driverRepository.save(existingDriver);
        } else {

            throw new EntityNotFoundException("Driver not found with ID: " + updateRequest.getDriverId());
        }
    }

    private static Driver getDriver(DriverUpdateRequest updateRequest, Optional<Driver> optionalDriver) {
        Driver existingDriver = optionalDriver.get();
        // Update driver details based on the request
        existingDriver.setFirstName(updateRequest.getFirstName());
        existingDriver.setLastName(updateRequest.getLastName());
        existingDriver.setLicenseNumber(updateRequest.getLicenseNumber());
        existingDriver.setPlateNumber(updateRequest.getPlateNumber());
        existingDriver.setCarModel(updateRequest.getCarModel());
        existingDriver.setLocation(updateRequest.getLocation());
        return existingDriver;
    }
}
