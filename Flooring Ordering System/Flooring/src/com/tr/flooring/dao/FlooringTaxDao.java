package com.tr.flooring.dao;

import com.tr.flooring.dto.TaxRate;

import java.util.List;

public interface FlooringTaxDao {

    List<TaxRate> readAll() throws FlooringFilePersistenceException;

    TaxRate readById(String state) throws FlooringFilePersistenceException;
}
