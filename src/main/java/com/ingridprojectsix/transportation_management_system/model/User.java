package com.ingridprojectsix.transportation_management_system.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
