package com.ingridprojectsix.transportation_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestUpdate {
    private String startLocation;
    private String endLocation;
}
