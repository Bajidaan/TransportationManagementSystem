package com.ingridprojectsix.transportation_management_system.model;

import com.ingridprojectsix.transportation_management_system.dto.RideRequestDto;
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

    @Enumerated(EnumType.STRING)
    private RideRequestStatus status;
    private LocalTime time;

    public RideRequest(RideRequestDto requestDto) {
        this.date = LocalDate.now();
        this.startLocation = requestDto.getStartLocation();
        this.endLocation = requestDto.getEndLocation();
        this.time = LocalTime.now();
    }
}
