package com.tr.flooring.service;

public class FlooringInvalidStateException extends Exception {

    public FlooringInvalidStateException (String message) {
        super(message);
    }
    public  FlooringInvalidStateException (String message, Throwable cause) {
        super(message, cause);
    }
}
