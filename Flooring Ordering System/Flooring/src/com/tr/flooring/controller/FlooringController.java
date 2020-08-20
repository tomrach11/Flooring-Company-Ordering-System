package com.tr.flooring.controller;

import com.tr.flooring.dao.FlooringFilePersistenceException;
import com.tr.flooring.dto.Order;
import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;
import com.tr.flooring.service.*;
import com.tr.flooring.ui.FlooringView;

import java.time.LocalDate;
import java.util.List;

public class FlooringController {

    FlooringServiceLayer service;
    FlooringView view;

    public FlooringController(FlooringView view, FlooringServiceLayer service) {
        this.service = service;
        this.view = view;
    }
    boolean keepGoing = true;

    public void run() {
        try {
            while (keepGoing == true) {
                String userInput = displayGetMenuSelection();
                switch (userInput) {
                    case "1":
                        displayOrderByDate();
                        break;
                    case "2":
                        addOrder();
                        break;
                    case "3":
                        editOrder();
                        break;
                    case "4":
                        removeOrder();
                        break;
                    case "5":
                        saveData();
                        break;
                    case "6":
                        exit();
                        break;
                    default:
                        invalidInput(userInput);
                        break;
                }
            }
        } catch (FlooringFilePersistenceException e) {
            displayErrorMessage(e.getMessage());
        }

    }

    private String displayGetMenuSelection() {
        return view.getMenuChoice();
    }

    private void displayOrderByDate() throws FlooringFilePersistenceException {
        view.displayOrderBanner();
        LocalDate orderDate = view.getOrderDate();
        try {
            List<Order> orderList = service.getOrderByDate(orderDate); //if the order exist then print it out
            view.displayOrderByDate(orderList);
            view.pressEnterTOContinue();
        } catch (FlooringOrderDateNotFoundException e) {
            view.displayErrorMessage(e.getMessage()); // if the order does not exist yet
            view.pressEnterTOContinue();
        }
    }
    private void addOrder() throws FlooringFilePersistenceException {
        view.addOrderBanner();
        List<Product> productList = service.getAllProduct(); // get available options for user to choose from service
        List<TaxRate> taxRateList = service.getAllTaxRate();

        LocalDate orderDate = view.getOrderDate();
        Order order = view.getOrderInfo(productList, taxRateList); // user fill out order
        try {
            order = service.validateOrder(order); // fill out the rest of the order and validate user input
            order = service.calculateOrder(order);
            if (view.getConfirm(order, "Would you like to create this order ?")) {
                service.addOrder(orderDate, order);
                view.addSuccessBanner();
            }
        } catch (FlooringInvalidStateException | FlooringInvalidProductTypeException | FlooringInvalidAreaException e) {
            view.displayErrorMessage(e.getMessage());   // if validation fail show the message and back to menu
        }

    }
    private void editOrder() throws FlooringFilePersistenceException {
        view.editOrderBanner();

        List<Product> productList = service.getAllProduct(); // get available options for user to choose from service
        List<TaxRate> taxRateList = service.getAllTaxRate();

        LocalDate orderDate = view.getOrderDate(); // get order info to get from dao
        int orderID = view.getOrderId();
        try {
            Order order = service.getOrder(orderDate, orderID); //get order
            order = view.getEditInfo(order, productList, taxRateList); // edit order
            try {
                order = service.validateOrder(order);   // validate and fill out order
                order = service.calculateOrder(order);
                boolean confirm = view.getConfirm(order, "Would you like to make this changes?"); // show edited info and get confirm
                if (confirm) {
                    service.editOrder(orderDate, order); // send order to dao edit map
                    view.editSuccessBanner();
                }
            } catch (FlooringInvalidAreaException | FlooringInvalidProductTypeException | FlooringInvalidStateException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } catch (FlooringOrderDateNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    private void removeOrder() throws FlooringFilePersistenceException {
        view.removeOrderBanner();
        boolean hasError = true;
        while(hasError) {
            LocalDate orderDate = view.getOrderDate();
            int orderID = view.getOrderId();
            try {
                Order order = service.getOrder(orderDate, orderID);
                boolean confirm = view.getConfirm(order, "Would you like to remove this order?"); //show order that going to be remove and get confirm
                if (confirm) {
                    service.removeOrder(orderDate, orderID);
                    view.removeSuccessBanner();
                }
                hasError = false;
            } catch (FlooringOrderDateNotFoundException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }
    private void saveData() throws FlooringFilePersistenceException {
        view.saveBanner();
        boolean userInput = view.getConfirm("Please confirm to save current work");
        if (userInput) {
            service.saveData();
            view.saveSuccessBanner();
        }
    }
    private void exit() throws FlooringFilePersistenceException {
        saveData(); // ask user to save before close the program
        view.exitBanner();
        keepGoing = false;
    }
    private void invalidInput(String userInput) {
        view.invalidUserInput(userInput);
    }
    private void displayErrorMessage(String message){
        view.displayErrorMessage(message);
    }
}
