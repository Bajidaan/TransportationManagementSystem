package com.ingridprojectsix.transportation_management_system.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class PassengerUpdateInfo {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;;
    private String address;
}
