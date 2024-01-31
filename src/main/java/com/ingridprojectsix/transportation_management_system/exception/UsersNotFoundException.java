package com.ingridprojectsix.transportation_management_system.exception;

public class UsersNotFoundException extends RuntimeException{

    public UsersNotFoundException() {
        this("user not found");
    }

    public UsersNotFoundException(String message) {
        super(message);
    }
}
