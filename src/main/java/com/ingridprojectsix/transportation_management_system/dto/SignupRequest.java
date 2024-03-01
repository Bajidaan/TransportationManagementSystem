package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank
    @Length(min = 3, max = 20,
            message = "First name can be less than 3 characters and more than 20 characters")
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20,
            message = "Lastname name can be less than 3 characters and more than 20 characters")
    private String lastName;

    @Email(message = "Kindly enter a valid email")
    private String email;

    private String password;

    private Role role;
}
