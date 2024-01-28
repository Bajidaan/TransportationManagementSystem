package com.ingridprojectsix.transportation_management_system.exception;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException() {
        this("Driver Not Found");
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
