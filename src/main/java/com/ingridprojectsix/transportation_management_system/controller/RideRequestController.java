package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.RideRequestDto;
import com.ingridprojectsix.transportation_management_system.model.RideRequest;
import com.ingridprojectsix.transportation_management_system.service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rideRequest")
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> makeRequest(@RequestBody RideRequestDto request) {
        return requestService.saveRideRequest(request);
    }

    @PutMapping("{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateRideRequest(@PathVariable Long requestId, RideRequest request) {
        return requestService.updateRequest(requestId, request);
    }



}
