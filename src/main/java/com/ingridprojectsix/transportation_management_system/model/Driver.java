package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @OneToOne
    @JoinColumn(name = "User_id", unique = true)
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "licence_number", unique = true)
    private String licenseNumber;

    @Column(name = "plate_number", unique = true)
    private String plateNumber;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "AvailabilityStatus")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "account_balance")
    private Double accountBalance;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "location")
    private String location;
}
