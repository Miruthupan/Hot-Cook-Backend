package com.resturent.resturen_spring.service.auth;

public class ResourceNotFound extends RuntimeException {

    // Constructor that accepts a message
    public ResourceNotFound(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public ResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
