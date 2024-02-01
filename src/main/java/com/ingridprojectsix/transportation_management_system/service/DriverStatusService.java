package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.DriverStatusDto;
import com.ingridprojectsix.transportation_management_system.exception.DriverNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.Driver;
import com.ingridprojectsix.transportation_management_system.model.DriverStatus;
import com.ingridprojectsix.transportation_management_system.repository.DriverRepository;
import com.ingridprojectsix.transportation_management_system.repository.DriverStatusRepository;
import com.ingridprojectsix.transportation_management_system.util.MessageResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service

public class DriverStatusService {

    private final DriverStatusRepository driverStatusRepository;
    private final DriverRepository driverRepository;

    public DriverStatusService(DriverStatusRepository driverStatusRepository, DriverRepository driverRepository) {
        this.driverStatusRepository = driverStatusRepository;
        this.driverRepository = driverRepository;
    }

   // @CacheEvict(value = {"getAll, getById, getByStatus"})
    public Map<String, String> saveDriverStatus(DriverStatusDto status) {
        Driver driver = driverRepository.findById(status.getDriverId()).orElseThrow(DriverNotFoundException::new);
        DriverStatus driverStatus = new DriverStatus(status);
        driverStatus.setDriver(driver);

        driverStatusRepository.save(driverStatus);

        return MessageResponse.displayMessage("Driver Status successfully saved");
    }

    //@Cacheable(cacheNames = "getAll")
    public List<DriverStatus> getAllStatus() {
        return driverStatusRepository.findAll();
    }

    //@Cacheable(cacheNames = "getById", key = "#statusId")
    public DriverStatus getStatusById(Long statusId) {
        return driverStatusRepository.findById(statusId).orElseThrow(DriverNotFoundException::new);
    }

    //@Cacheable(cacheNames = "getByStatus", key = "#isAvailable")
    public List<DriverStatus> getStatusByAvailability(boolean isAvailable) {
        return driverStatusRepository.findByAvailability(isAvailable);
    }

    //@CacheEvict(value = {"getAll, getById, getByStatus"}, key = "#status")
    public Map<String, String> updateDriverAvailability(Long statusId, boolean status) {
       DriverStatus driverStatus = driverStatusRepository.findById(statusId)
               .orElseThrow(DriverNotFoundException::new);

       driverStatus.setAvailability(status);
       driverStatusRepository.save(driverStatus);
       return MessageResponse.displayMessage("status updated successfully");
    }


    //@CacheEvict(value = {"getAll, getById, getByStatus"})
    public Map<String, String> updateDriverLocation(Long id, double latitude, double longitude) {
        DriverStatus driverStatus = driverStatusRepository.findById(id)
                .orElseThrow(DriverNotFoundException::new);

        driverStatus.setLatitude(latitude);
        driverStatus.setLongitude(longitude);

        driverStatusRepository.save(driverStatus);
        return MessageResponse.displayMessage("status updated successfully");

    }

    //@CacheEvict(value = {"getAll, getById, getByStatus"}, key = "#id")
    public Map<String, String> deleteById(Long id) {
        driverStatusRepository.deleteById(id);
        return MessageResponse.displayMessage("deleted");
    }
}
