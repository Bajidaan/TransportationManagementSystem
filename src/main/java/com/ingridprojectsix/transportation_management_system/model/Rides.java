package com.ingridprojectsix.transportation_management_system.model;

import com.ingridprojectsix.transportation_management_system.model.domain.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rides")
public class Rides {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ride_id")
    private long riderId;

    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "passenger_id")
    private Passenger passengers;

    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "driver_id")
    private Driver drivers;

    @Column(name = "start_location")
    private  String startLocation;

    @Column(name = "end_location")
    private String endLocation;

    @Column(name = "status")  @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "fare")
    private double fare;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

}
