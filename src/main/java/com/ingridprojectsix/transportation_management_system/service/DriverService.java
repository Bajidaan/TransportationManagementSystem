package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.DriverRegistrationRequest;
import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updateDriverBalance(Long driverId, double amount) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setAccountBalance(driver.getAccountBalance() + amount);
        driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }
}
