package com.ingridprojectsix.transportation_management_system.exception;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException() {
        this("passenger not found");
    }

    public PassengerNotFoundException(String message) {
        super(message);
    }
}
