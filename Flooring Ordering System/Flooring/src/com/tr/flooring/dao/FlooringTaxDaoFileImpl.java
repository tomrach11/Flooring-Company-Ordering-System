package com.tr.flooring.dao;

import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringTaxDaoFileImpl implements FlooringTaxDao {

    String FILE_NAME;
    private static final String DELIMITER = "::";

    public FlooringTaxDaoFileImpl(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    private Map<String, TaxRate> taxList = new HashMap<>();

    @Override
    public List<TaxRate> readAll() throws FlooringFilePersistenceException {
        load();
        return new ArrayList<TaxRate>(taxList.values());
    }

    @Override
    public TaxRate readById(String state) throws FlooringFilePersistenceException {
        load();
        return taxList.get(state);
    }

    private void load() throws FlooringFilePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_Could not load Items data into memory.");
        }

        while (scanner.hasNextLine()) {
            String taxRateText = scanner.nextLine();
            TaxRate taxRate = unmarshallTaxRate(taxRateText);
            taxList.put(taxRate.getState(), taxRate);
        }
    }

    private TaxRate unmarshallTaxRate(String taxRateText) {

        String[] taxRateArray = taxRateText.split(DELIMITER);

        TaxRate taxRate = new TaxRate();
        taxRate.setState(taxRateArray[0]);
        taxRate.setTaxRate(new BigDecimal(taxRateArray[1]));

        return taxRate;
    }
}
