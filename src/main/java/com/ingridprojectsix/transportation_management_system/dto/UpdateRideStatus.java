package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.domain.RequestStatus;
import lombok.Data;

@Data
public class UpdateRideStatus {
    RequestStatus status;
}
