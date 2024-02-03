package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.RideRequestStatus;
import lombok.Data;

@Data
public class UpdateRideRequest {
    RideRequestStatus status;
}
