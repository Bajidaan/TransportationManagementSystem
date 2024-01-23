package com.ingridprojectsix.transportation_management_system.model;

import com.ingridprojectsix.transportation_management_system.dto.AvailabilityStatus;
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

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "LicenceNumber", unique = true)
    private String licenseNumber;

    @Column(name = "PlateNumber", unique = true)
    private String plateNumber;

    @Column(name = "CarDetails")
    private String carDetails;

    @Column(name = "AvailabilityStatus")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "AccountBalance")
    private Double accountBalance;

    @Column(name = "RegistrationDate")
    private LocalDateTime registrationDate;

    @Column(name = "Location")
    private String location;
}
