package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    private LocalDate date;
    private String startLocation;
    private String endLocation;
    private RequestStatus status;
    private LocalTime time;







}
