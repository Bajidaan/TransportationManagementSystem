package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
