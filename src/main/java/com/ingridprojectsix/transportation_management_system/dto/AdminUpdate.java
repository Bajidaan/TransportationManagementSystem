package com.ingridprojectsix.transportation_management_system.dto;

import lombok.Data;

@Data
public class AdminUpdate {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
}
