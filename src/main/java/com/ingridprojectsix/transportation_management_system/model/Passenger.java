package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @OneToMany(fetch = FetchType.LAZY) @JoinColumn(name = "passenger")
    private User user;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
