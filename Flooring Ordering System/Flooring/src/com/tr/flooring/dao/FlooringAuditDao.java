package com.tr.flooring.dao;

import java.util.ArrayList;

public interface FlooringAuditDao {
    void writeAuditEntry () throws FlooringFilePersistenceException;

    void writeToText (String entry);
}
