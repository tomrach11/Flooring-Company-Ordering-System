package com.tr.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {

    void print(String message);

    void println(String message);

    double readDouble(String prompt);

    String readString(String prompt);

    LocalDate readLocalDate(String prompt);

    int readInt(String prompt);

}
