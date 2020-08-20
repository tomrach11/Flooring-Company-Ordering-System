package com.tr.flooring.service;

public class FlooringInvalidIDException extends Exception {

    public FlooringInvalidIDException (String message) {
        super(message);
    }
    public  FlooringInvalidIDException (String message, Throwable cause) {
        super(message, cause);
    }
}
