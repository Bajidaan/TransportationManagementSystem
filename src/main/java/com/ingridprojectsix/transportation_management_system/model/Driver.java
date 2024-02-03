package com.ingridprojectsix.transportation_management_system.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DriverId")
    private Long driverId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_id", unique = true)
    private Users user;

    @NotBlank(message = "first name is required")
//    @Size(min = 6, max = 50, message = "first name must be within the range of 6 - 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "first name is required")
//    @Size(min = 6, max = 50, message = "last name must be within the range of 6 - 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "License number is required")
    @Column(name = "licence_number", unique = true)
    private String licenseNumber;

    @NotBlank(message = "Plate number is required")
    @Column(name = "plate_number", unique = true)
    private String plateNumber;

//    @NotBlank(message = "car model name is required")
    @Column(name = "car_model")
    private String carModel;

    @Column(name = "AvailabilityStatus")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "account_balance")
    private Double accountBalance;

//    @NotBlank
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

//    @NotBlank
    @Column(name = "location")
    private String location;

}