package com.tr.flooring.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FlooringAuditDaoFileImpl implements FlooringAuditDao {

    public static final String AUDIT_FILE = "audit.txt";
    ArrayList<String> entryText = new ArrayList<>();

    @Override
    public void writeAuditEntry() throws FlooringFilePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE));
        } catch (IOException e) {
            throw new FlooringFilePersistenceException("Could not persist audit information.", e);
        }

        for (String entry : entryText) {
            out.println(entry);
            out.flush();
        }
        entryText = new ArrayList<>();
        out.close();
    }

    @Override
    public void writeToText(String entry) {
        LocalDateTime date = LocalDateTime.now();
        entryText.add(date + ":" + entry);
    }
}
