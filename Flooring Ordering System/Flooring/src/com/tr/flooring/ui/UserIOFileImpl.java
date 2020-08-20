package com.tr.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOFileImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        if (prompt.equalsIgnoreCase("")) {
            return 0;
        }
        else {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                this.println("Invalid Input. Please try again.");
                return readDouble(prompt);
            }
        }
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        print(prompt);
        try {
            return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            this.println("\nInvalid Date format. Please follow the format (MM/DD/YYYY)\n");
            return readLocalDate(prompt);
        }
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            this.println("Invalid Input. Please enter number for ID.");
            return readInt(prompt);
        }
    }

}
