package com.ingridprojectsix.transportation_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverStatusDto {
    private Long driverId;
    private double latitude;
    private double longitude;
    private boolean availability;
}
