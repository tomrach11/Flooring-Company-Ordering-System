package com.tr.flooring.dao;

import com.tr.flooring.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringOrderDao {

    Order create(LocalDate orderDate, Order order) throws FlooringFilePersistenceException;

    List<Order> readAll(LocalDate orderDate) throws FlooringFilePersistenceException;

    Order readById(LocalDate orderDate, int id) throws FlooringFilePersistenceException;

    Order update(LocalDate date, Order order);

    Order remove(LocalDate orderDate, int id);

    void save() throws FlooringFilePersistenceException;
}
