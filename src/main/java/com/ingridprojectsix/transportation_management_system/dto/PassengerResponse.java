package com.ingridprojectsix.transportation_management_system.dto;

import com.ingridprojectsix.transportation_management_system.model.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
public class PassengerResponse {
    private Long userId;
    private String phoneNumber;
    private String address;

}
