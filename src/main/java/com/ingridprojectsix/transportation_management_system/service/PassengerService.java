package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.AccountBalanceDTO;
import com.ingridprojectsix.transportation_management_system.dto.PassengerResponse;
import com.ingridprojectsix.transportation_management_system.dto.PassengerUpdateInfo;
import com.ingridprojectsix.transportation_management_system.dto.UpdateBalance;
import com.ingridprojectsix.transportation_management_system.exception.PassengerNotFoundException;
import com.ingridprojectsix.transportation_management_system.exception.UsersNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.repository.PassengerRepository;
import com.ingridprojectsix.transportation_management_system.repository.UserRepository;
import com.ingridprojectsix.transportation_management_system.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;

    public Map<String, String> savePassenger(PassengerResponse response) {
        Passenger passenger = new Passenger();
        Users user =  userRepository.findById(response.getUserId())
                .orElseThrow(UsersNotFoundException::new);

        passenger.setFirstName(user.getFirstName());
        passenger.setLastName(user.getLastName());
        passenger.setEmail(user.getEmail());
        passenger.setAddress(response.getAddress());
        passenger.setPhoneNumber(response.getPhoneNumber());
        passenger.setUser(user);

        passengerRepository.save(passenger);

        return MessageResponse.displayMessage("successfully save information");
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger findPassengerById(Long id) {
        return passengerRepository.findById(id).orElseThrow(PassengerNotFoundException::new);
    }

    public Map<String, String> updateAccountBalance(Long id, AccountBalanceDTO balance) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(PassengerNotFoundException::new);

        passenger.setAccountBalance(passenger.getAccountBalance() + balance.amount());
        passengerRepository.save(passenger);

        return MessageResponse.displayMessage("account balance updated");
    }

    public Map<String, String> updatePassenger(Long id, PassengerUpdateInfo updateInfo) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(PassengerNotFoundException::new);

        passenger.setFirstName(updateInfo.getFirstName());
        passenger.setLastName(updateInfo.getLastName());
        passenger.setEmail(updateInfo.getEmail());
        passenger.setAddress(updateInfo.getAddress());
        passenger.setPhoneNumber(updateInfo.getPhoneNumber());

        passengerRepository.save(passenger);

        return MessageResponse.displayMessage("updated successfully");
    }
}
