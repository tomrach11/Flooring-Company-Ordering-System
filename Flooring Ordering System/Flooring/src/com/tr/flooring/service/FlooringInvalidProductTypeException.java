package com.tr.flooring.service;

public class FlooringInvalidProductTypeException extends Exception {

    public FlooringInvalidProductTypeException (String message) {
        super(message);
    }
    public  FlooringInvalidProductTypeException (String message, Throwable cause) {
        super(message, cause);
    }

}
