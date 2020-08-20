package com.tr.flooring.dao;

import com.tr.flooring.dto.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringProductDaoFileImplTest {

    FlooringProductDaoFileImpl dao = new FlooringProductDaoFileImpl("testProducts.txt");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readAll() throws FlooringFilePersistenceException {
        List<Product> fromDao = dao.readAll();
        assertEquals(4, fromDao.size());
    }

    @Test
    void readById() throws FlooringFilePersistenceException {
        Product product = new Product();
        product.setProductType("wood");
        product.setCostPerSq(new BigDecimal("5.15"));
        product.setLaborCostPerSq(new BigDecimal("4.75"));
        Product fromDao = dao.readById("wood");
        assertEquals(product, fromDao);
    }
}