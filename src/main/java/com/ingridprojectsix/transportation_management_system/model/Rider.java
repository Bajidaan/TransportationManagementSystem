package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rider")
public class Rider {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "rider_id")
    private long riderId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passenger")
    private List<Passenger> passengers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "driver")
    private List<Driver> drivers;
    @Column(name = "start_location")
    private  String startLocation;
    @Column(name = "end_location")
    private String endLocation;
    @Column(name = "status")
    private  RequestStatus status;
    @Column(name = "fare")
    private double fare;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;

}
