package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passengers")
    private List<Rides> rides;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", unique = true)
    private Users user;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "account_balance")
    private double accountBalance = 0.0;
}
