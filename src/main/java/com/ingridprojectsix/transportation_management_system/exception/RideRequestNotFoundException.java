package com.ingridprojectsix.transportation_management_system.exception;

public class RideRequestNotFoundException extends RuntimeException {

    public RideRequestNotFoundException() {
        this("Ride Request not found");
    }

    public RideRequestNotFoundException(String message) {
        super(message);
    }
}
