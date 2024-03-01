package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.AccountBalanceDTO;
import com.ingridprojectsix.transportation_management_system.dto.PassengerResponse;
import com.ingridprojectsix.transportation_management_system.dto.PassengerUpdateInfo;
import com.ingridprojectsix.transportation_management_system.dto.UpdateBalance;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping("/save")
    public Map<String, String> savePassengerInfo(@RequestBody PassengerResponse response) {
        return passengerService.savePassenger(response);
    }

    @GetMapping("/getAll")
    public List<Passenger> getAllPassenger() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/find/{id}")
    public Passenger findPassengerById(@PathVariable Long id) {
        return passengerService.findPassengerById(id);
    }

    @PutMapping("accountBalance/{id}")
    public Map<String, String> updateAccountBalance(@PathVariable Long id,
                                                    @RequestBody AccountBalanceDTO balance) {
        return passengerService.updateAccountBalance(id, balance);
    }

    @PutMapping("/{id}")
    public Map<String, String> updatePassenger(@PathVariable Long id,
                                               @RequestBody PassengerUpdateInfo updateInfo) {
        return passengerService.updatePassenger(id, updateInfo);
    }

}
