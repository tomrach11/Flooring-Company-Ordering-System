package com.tr.flooring.service;

import com.tr.flooring.dao.FlooringFilePersistenceException;
import com.tr.flooring.dto.Order;
import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;

import java.time.LocalDate;
import java.util.List;

public interface FlooringServiceLayer {
    List<Order> getOrderByDate(LocalDate orderDate) throws FlooringOrderDateNotFoundException, FlooringFilePersistenceException;
    Order validateOrder(Order order) throws FlooringInvalidStateException, FlooringInvalidProductTypeException, FlooringInvalidAreaException, FlooringFilePersistenceException;
    Order calculateOrder(Order order);
    Order addOrder(LocalDate orderDate, Order order) throws FlooringFilePersistenceException;
    Order editOrder(LocalDate orderDate, Order order) ;
    Order getOrder(LocalDate orderDate, int id) throws FlooringOrderDateNotFoundException, FlooringFilePersistenceException;
    Order removeOrder(LocalDate date, int orderID);
    void saveData() throws FlooringFilePersistenceException;
    List<Product> getAllProduct() throws FlooringFilePersistenceException;
    List<TaxRate> getAllTaxRate() throws FlooringFilePersistenceException;
}
