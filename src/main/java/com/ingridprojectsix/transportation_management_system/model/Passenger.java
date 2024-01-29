package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passengers")
    private List<Rides> rides;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "passenger")
    private Users user;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
