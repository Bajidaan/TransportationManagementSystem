package com.ingridprojectsix.transportation_management_system.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException() {
        this("Admin not found");
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}
