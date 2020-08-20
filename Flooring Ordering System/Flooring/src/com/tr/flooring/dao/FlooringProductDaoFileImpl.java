package com.tr.flooring.dao;

import com.tr.flooring.dto.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringProductDaoFileImpl implements FlooringProductDao {

    String FILE_NAME;
    private static final String DELIMITER = "::";

    public FlooringProductDaoFileImpl(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    private Map<String, Product> productList = new HashMap<>();

    @Override
    public List<Product> readAll() throws FlooringFilePersistenceException {
        load();
        return new ArrayList<Product>(productList.values());
    }

    @Override
    public Product readById(String productType) throws FlooringFilePersistenceException {
        load();
        return productList.get(productType);
    }

    private void load() throws FlooringFilePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FILE_NAME)));
        } catch (FileNotFoundException e) {
            throw new FlooringFilePersistenceException("-_Could not load Items data into memory.");
        }

        while (scanner.hasNextLine()) {
            String productText = scanner.nextLine();
            Product product = ummarshallProduct(productText);
            productList.put(product.getProductType(), product);
        }
    }

    private Product ummarshallProduct (String productText) {

        String[] productArray = productText.split(DELIMITER);

        Product product = new Product();
        product.setProductType(productArray[0]);
        product.setCostPerSq(new BigDecimal(productArray[1]));
        product.setLaborCostPerSq(new BigDecimal(productArray[2]));

        return product;
    }
}
