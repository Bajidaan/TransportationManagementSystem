package com.ingridprojectsix.transportation_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "driver")
    private Driver driver;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "passenger")
    private Passenger passenger;
}
