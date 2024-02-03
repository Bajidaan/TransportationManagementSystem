package com.ingridprojectsix.transportation_management_system.model;

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
    private String location;
}
