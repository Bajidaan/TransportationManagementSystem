package com.ingridprojectsix.transportation_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DriverRegistrationRequest {
    private Long userId;
    private String phoneNumber;
    private String licenseNumber;
    private String plateNumber;
    private String carModel;
    private String address;
}
