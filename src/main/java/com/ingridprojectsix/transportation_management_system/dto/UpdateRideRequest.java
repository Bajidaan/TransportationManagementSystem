package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdateRideRequest {
    RideRequestStatus status;
}
