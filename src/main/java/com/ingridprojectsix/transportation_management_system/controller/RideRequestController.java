package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.RideRequestDto;
import com.ingridprojectsix.transportation_management_system.dto.RideRequestUpdate;
import com.ingridprojectsix.transportation_management_system.model.RideRequest;
import com.ingridprojectsix.transportation_management_system.service.RideRequestService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/rideRequest")
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService requestService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<RideRequest> getAllRequest() {
        return requestService.getAllRideRequest();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RideRequest findRideRequestById(@PathVariable Long id) {
        return requestService.getRideRequest(id);
    }

    @GetMapping("/passengerId/{passengerId}")
    @ResponseStatus(HttpStatus.OK)
    public RideRequest findRideRequestByPassengerId(@PathVariable Long passengerId) {
        return requestService.getRideRequestByPassengerId(passengerId);
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> makeRequest(@RequestBody RideRequestDto request) throws MessagingException {
        return requestService.saveRideRequest(request);
    }

    @PutMapping("{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateRideRequest(@PathVariable Long requestId, @RequestBody RideRequestUpdate request) {
        return requestService.updateRequestByPassenger(requestId, request);
    }

    @PutMapping("/updateStatus/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateStatus(@PathVariable Long requestId) throws MessagingException {
        return requestService.updateStatus(requestId);
    }
}
