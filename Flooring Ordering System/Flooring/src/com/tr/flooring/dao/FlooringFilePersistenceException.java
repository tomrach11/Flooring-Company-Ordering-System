package com.tr.flooring.dao;

public class FlooringFilePersistenceException extends Exception {

    public FlooringFilePersistenceException (String message) {
        super(message);
    }
    public  FlooringFilePersistenceException (String message, Throwable cause) {
        super(message, cause);
    }
}
