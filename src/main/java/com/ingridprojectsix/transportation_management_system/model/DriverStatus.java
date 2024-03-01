package com.ingridprojectsix.transportation_management_system.model;

import com.ingridprojectsix.transportation_management_system.dto.DriverStatusDto;
import com.ingridprojectsix.transportation_management_system.model.domain.DriverResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private double latitude;

    private double longitude;

    private boolean availability;

    private LocalDateTime lastRejectionTime;

    public DriverStatus(DriverStatusDto driverStatusDto) {
        this.latitude = driverStatusDto.getLatitude();
        this.longitude = driverStatusDto.getLongitude();
        this.availability = driverStatusDto.isAvailability();
    }
}
