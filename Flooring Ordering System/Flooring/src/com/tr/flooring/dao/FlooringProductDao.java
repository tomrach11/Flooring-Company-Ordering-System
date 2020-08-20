package com.tr.flooring.dao;

import com.tr.flooring.dto.Product;

import java.util.List;

public interface FlooringProductDao {

    List<Product> readAll() throws FlooringFilePersistenceException;

    Product readById(String productType) throws FlooringFilePersistenceException;
}
