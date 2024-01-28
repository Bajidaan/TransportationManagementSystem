package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long passengerId;
    private LocalDate date;
    private String startLocation;
    private String endLocation;
    private RideRequestStatus status;
    private LocalTime time;
}
