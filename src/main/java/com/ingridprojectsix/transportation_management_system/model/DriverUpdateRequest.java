package com.ingridprojectsix.transportation_management_system.model;

import lombok.Data;

@Data
public class DriverUpdateRequest {

    private Long driverId;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String plateNumber;
    private String carModel;
    private String location;
}
