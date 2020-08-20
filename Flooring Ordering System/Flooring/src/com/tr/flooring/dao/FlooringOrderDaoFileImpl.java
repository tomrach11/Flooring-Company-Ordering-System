package com.tr.flooring.dao;

import com.tr.flooring.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    //Orders_06012013.txt (mmddyyyy)
    String fileName;
    LocalDate orderDate;
    private static final String DELIMITER = "::";

    private Map<LocalDate, Map<Integer, Order>> orderList = new HashMap<>();

    @Override
    public Order create(LocalDate orderDate, Order order) {
        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);

        if (orders == null) {
            try {
                // case of not load from file yet, have to load it first
                load();
                orders = orderList.get(orderDate); //retrieve list of order's map from again
                order.setOrderID(getLatestOrderID()); //set order id

                orders.put(order.getOrderID(), order); //add to new order to order's map (inner map)
                orderList.put(orderDate, orders); // add inner map to outer map
            } catch (FlooringFilePersistenceException e) {
                // case of file does not have for this date yet
                order.setOrderID(getLatestOrderID());

                Map<Integer, Order> newMap = new HashMap<>(); // create new inner map too add to outer map
                newMap.put(order.getOrderID(), order); // add order to new map
                orderList.put(orderDate, newMap); // add to outer map
            }
        } else {
            //case of already load from file
            order.setOrderID(getLatestOrderID());

            orders.put(order.getOrderID(), order);
            orderList.put(orderDate, orders);
        }
        return order;
    }

    @Override
    public List<Order> readAll(LocalDate orderDate) {
        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);
        if (orders == null) {
            try {
                load(); // if load every time it will overwrite what in the HashMap that does not save yet
                orders = orderList.get(orderDate);
            } catch (FlooringFilePersistenceException e) {
                return null;
            }
        }
        return new ArrayList<Order>(orders.values());
    }

    @Override
    public Order readById(LocalDate orderDate, int id) {
        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);
        if (orders == null) {
            try {
                load(); // if load every time it will overwrite what in the HashMap that does not save yet
                orders = orderList.get(orderDate);
            } catch (FlooringFilePersistenceException e) {
                return null;
            }
        }
        return orders.get(id);
    }

    @Override
    public Order update(LocalDate orderDate, Order order) {
        Map<Integer, Order> orders = orderList.get(orderDate); //get inner HashMap
        orders.put(order.getOrderID(), order); // save to inner HashMap
        orderList.put(orderDate, orders); // save inner to outer HashMap
        return order;
    }

    @Override
    public Order remove(LocalDate orderDate, int id) {
        Map<Integer, Order> orders = orderList.get(orderDate);
        return orders.remove(id);
    }


    private int getLatestOrderID() {
        int latestID = 0;
        Map<Integer, Order> orders = orderList.get(orderDate);
        if (orders != null) {
            ArrayList<Integer> keys = new ArrayList<>(orders.keySet());
            for (int key : keys) {
                if (key > latestID) {
                    latestID = key;
                }
            }
            return latestID + 1;
        } else return 1; // orders is null when have to create new orderDate file, orderID have to start from 1
    }

    @Override
    public void save() throws FlooringFilePersistenceException {
        File file;
        List<LocalDate> orderDateList = new ArrayList<>(orderList.keySet()); // to loop all the date that is in the HashMap
        for (LocalDate orderDate : orderDateList) {
            String stringDate = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            fileName = "Orders_" + stringDate + ".txt"; // edit file name according to orderDate

            file = new File(fileName);
            file.exists();
            file.delete();

            List<Order> orders = this.readAll(orderDate);
            PrintWriter out;
            if (orders.size() != 0) {
                try {
                    file = new File(fileName);
                    out = new PrintWriter(new FileWriter(fileName, true));
                } catch (IOException ex) {
                    throw new FlooringFilePersistenceException("-_Could not save date for Order Date: " + stringDate + ".");
                }
                for (Order order : orders) {
                    String orderText = marshallOrder(order);
                    out.println(orderText);
                    out.flush();
                }
                out.close();
            }
        }
    }

    private void load() throws FlooringFilePersistenceException{
        String stringDate = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        fileName = "Orders_" + stringDate + ".txt"; // edit file name according to orderDate
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_Could not load Items data into memory.");
        }
        Map<Integer, Order> orderMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            String orderText = scanner.nextLine();
            Order order = unmarshallOrder(orderText);
            orderMap.put(order.getOrderID(), order); //add to inner map
        }
        scanner.close();
        orderList.put(orderDate, orderMap); // add to outer map
    }

    private Order unmarshallOrder(String orderText) {
        String orderArray[] = orderText.split(DELIMITER);

        Order order = new Order();
        order.setOrderID(Integer.parseInt(orderArray[0]));
        order.setCustomerName(orderArray[1]);
        order.setState(orderArray[2]);
        order.setTaxRate(stringToBigDecimal(orderArray[3]));
        order.setProductType(orderArray[4]);
        order.setProductArea(Double.parseDouble(orderArray[5]));
        order.setCostPerSq(stringToBigDecimal(orderArray[6]));
        order.setLaborCostPerSq(stringToBigDecimal(orderArray[7]));
        order.setTotalMaterialCost(stringToBigDecimal(orderArray[8]));
        order.setTotalLaborCost(stringToBigDecimal(orderArray[9]));
        order.setTotalTax(stringToBigDecimal(orderArray[10]));
        order.setTotalCost(stringToBigDecimal(orderArray[11]));

        return order;
    }

    private String marshallOrder(Order order) {
        String orderText = "";
        orderText += order.getOrderID() + DELIMITER;
        orderText += order.getCustomerName() + DELIMITER;
        orderText += order.getState() + DELIMITER;
        orderText += order.getTaxRate() + DELIMITER;
        orderText += order.getProductType() + DELIMITER;
        orderText += order.getProductArea() + DELIMITER;
        orderText += order.getCostPerSq() + DELIMITER;
        orderText += order.getLaborCostPerSq() + DELIMITER;
        orderText += order.getTotalMaterialCost() + DELIMITER;
        orderText += order.getTotalLaborCost() + DELIMITER;
        orderText += order.getTotalTax() + DELIMITER;
        orderText += order.getTotalCost();

        return orderText;
    }

    private BigDecimal stringToBigDecimal (String string) {
        return new BigDecimal(string);
    }

}
