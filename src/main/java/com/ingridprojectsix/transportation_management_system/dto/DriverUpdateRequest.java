package com.ingridprojectsix.transportation_management_system.dto;

import lombok.Data;

@Data
public class DriverUpdateRequest {
    private Long driverId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String licenseNumber;
    private String plateNumber;
    private String carModel;
    private String address;
    private String registrationDate;
}
