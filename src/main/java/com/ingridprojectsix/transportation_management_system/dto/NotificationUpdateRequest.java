package com.ingridprojectsix.transportation_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationUpdateRequest {

    private String message;
    private Boolean isRead;
    private Boolean isAccepted;
    //private NotificationStatus status;
}
