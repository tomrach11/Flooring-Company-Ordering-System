package com.tr.flooring.dao;

import com.tr.flooring.dto.TaxRate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringTaxDaoFileImplTest {

    FlooringTaxDaoFileImpl dao = new FlooringTaxDaoFileImpl("testTax.txt");

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void readAll() throws FlooringFilePersistenceException {
        List<TaxRate> taxRateList = dao.readAll();
        assertEquals(4, taxRateList.size());
    }

    @Test
    void readById() throws FlooringFilePersistenceException {
        TaxRate expected = new TaxRate();
        expected.setState("OH");
        expected.setTaxRate(new BigDecimal("6.25"));
        TaxRate fromDao = dao.readById("OH");
        assertEquals(expected, fromDao);
    }
}