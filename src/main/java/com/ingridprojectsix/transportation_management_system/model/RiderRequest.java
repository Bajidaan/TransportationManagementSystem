package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class RiderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    private LocalDate date;
    private double startLocation;
    private double endLocation;
    private RequestStatus status;
    private LocalTime time;

}
