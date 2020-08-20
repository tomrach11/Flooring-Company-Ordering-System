package com.tr.flooring.service;

public class FlooringInvalidAreaException extends Exception {

    public FlooringInvalidAreaException (String message) {
        super(message);
    }
    public  FlooringInvalidAreaException (String message, Throwable cause) {
        super(message, cause);
    }
}
