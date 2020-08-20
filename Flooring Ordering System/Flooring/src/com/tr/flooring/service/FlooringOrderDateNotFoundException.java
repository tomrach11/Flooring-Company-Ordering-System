package com.tr.flooring.service;

public class FlooringOrderDateNotFoundException extends Exception {

    public FlooringOrderDateNotFoundException (String message) {
        super(message);
    }
    public  FlooringOrderDateNotFoundException (String message, Throwable cause) {
        super(message, cause);
    }
}
