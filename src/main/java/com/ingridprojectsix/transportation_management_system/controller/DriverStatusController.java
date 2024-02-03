package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.DriverStatusDto;
import com.ingridprojectsix.transportation_management_system.model.DriverStatus;
import com.ingridprojectsix.transportation_management_system.service.DriverStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DriverStatusController {

    private final DriverStatusService driverStatusService;
    @PostMapping("addDriverStatus")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> saveDriverStatus(@RequestBody DriverStatusDto status) {
        return driverStatusService.saveDriverStatus(status);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DriverStatus> getAllStatus() {
        return driverStatusService.getAllStatus();
    }

    @GetMapping("/getById/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public DriverStatus getStatusById(@PathVariable Long statusId) {
        return driverStatusService.getStatusById(statusId);
    }

    @GetMapping("/findByAvailability")
    @ResponseStatus(HttpStatus.OK)
    public List<DriverStatus> findStatusByAvailability(@RequestParam boolean isAvailable) {
        return driverStatusService.getStatusByAvailability(isAvailable);
    }

    @PutMapping("updateAvailability")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateDriverAvailability(@RequestParam Long statusId,
                                                        @RequestParam boolean status) {
        return driverStatusService.updateDriverAvailability(statusId, status);
    }

    @PutMapping("updateLocation")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateDriverLocation(@RequestParam Long id, @RequestParam double latitude,
                                                    @RequestParam double longitude) {
        return driverStatusService.updateDriverLocation(id, latitude, longitude);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> deleteById(@PathVariable Long id) {
        return driverStatusService.deleteById(id);
    }
}
