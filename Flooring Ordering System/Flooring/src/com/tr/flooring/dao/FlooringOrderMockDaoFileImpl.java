package com.tr.flooring.dao;

import com.tr.flooring.dto.Order;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class FlooringOrderMockDaoFileImpl implements FlooringOrderDao {

    String FILE_NAME;
    private static final String DELIMITER = "::";
    LocalDate orderDate;

    private Map<LocalDate, Map<Integer, Order>> orderList = new HashMap<>();

    public FlooringOrderMockDaoFileImpl(String FILE_NAME) throws FlooringFilePersistenceException {
        this.FILE_NAME = FILE_NAME;
        this.load();
    }

    @Override
    public Order create(LocalDate orderDate, Order order) throws FlooringFilePersistenceException {

        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);
        order.setOrderID(getLatestOrderID());
        if (orders == null) {
            Map<Integer, Order> newMap = new HashMap<>(); // create new inner map too add to outer map
            newMap.put(order.getOrderID(), order); // add order to new map
            orderList.put(orderDate, newMap); // add to outer map
        } else {

            orders.put(order.getOrderID(), order);
            orderList.put(orderDate, orders);
        }

        return order;
    }

    @Override
    public List<Order> readAll(LocalDate orderDate) throws FlooringFilePersistenceException {
        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);
        if (orders == null) {
            return null;
        } else {
            return new ArrayList<Order>(orders.values());
        }
    }

    @Override
    public Order readById(LocalDate orderDate, int id) throws FlooringFilePersistenceException {
        this.orderDate = orderDate;
        Map<Integer, Order> orders = orderList.get(orderDate);
        if (orders == null) {
            return null;
        } else {
            return orders.get(id);
        }
    }

    @Override
    public Order update(LocalDate date, Order order) {
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

    @Override
    public void save() throws FlooringFilePersistenceException {

    }

    private void load() throws FlooringFilePersistenceException {
        LocalDate date = LocalDate.now();
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_Could not load Items data into memory.");
        }
        Map<Integer, Order> orderMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            String orderText = scanner.nextLine();
            Order order = unmarshallOrder(orderText);
            orderMap.put(order.getOrderID(), order); //add to inner map
        }
        System.out.println(date);
        orderList.put(date, orderMap); // add to outer map
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

    private BigDecimal stringToBigDecimal (String string) {
        return new BigDecimal(string);
    }

}
