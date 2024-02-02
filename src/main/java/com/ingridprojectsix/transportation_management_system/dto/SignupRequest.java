package com.ingridprojectsix.transportation_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
