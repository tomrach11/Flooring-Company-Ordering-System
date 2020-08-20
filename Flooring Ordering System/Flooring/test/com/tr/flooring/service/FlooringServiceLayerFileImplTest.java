package com.tr.flooring.service;

import com.tr.flooring.dao.*;
import com.tr.flooring.dto.Order;
import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringServiceLayerFileImplTest {

    FlooringTaxDao taxDao = new FlooringTaxDaoFileImpl("testTax.txt");
    FlooringProductDao productDao = new FlooringProductDaoFileImpl("testProducts.txt");
    FlooringOrderDao orderDao = new FlooringOrderDaoFileImpl();
    FlooringAuditDao audit = new FlooringAuditDaoFileImpl();
    FlooringServiceLayerFileImpl service = new FlooringServiceLayerFileImpl(orderDao, productDao, taxDao, audit);

    File file;

    @BeforeEach
    void setUp() throws FlooringFilePersistenceException {
        file = new File("Orders_01011000.txt");
        writeFile();
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    void testTetOrderByDateSuccess() throws FlooringOrderDateNotFoundException, FlooringFilePersistenceException {
        this.setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        List<Order> fromDao = service.getOrderByDate(orderDate);
        assertEquals(3, fromDao.size());
        this.tearDown();
    }

    @Test
    void testTetOrderByDateOrderNotFound() throws FlooringFilePersistenceException {
        this.setUp();
        LocalDate orderDate = LocalDate.parse("1000-02-01");
        try {
            service.getOrderByDate(orderDate);
            fail("Expected FlooringOrderDateNotFoundException was not thrown.");
        } catch (FlooringOrderDateNotFoundException e) {
            return;
        }
        this.tearDown();
    }

    @Test
    void testValidateOrderValidInput() throws FlooringFilePersistenceException, FlooringInvalidAreaException, FlooringInvalidStateException, FlooringInvalidProductTypeException {
        this.setUp();
        Order inputOrder = new Order();
        inputOrder.setCustomerName("Wise");
        inputOrder.setState("OH");
        inputOrder.setProductType("wood");
        inputOrder.setProductArea(100);

        Order expectedOrder = new Order();
        expectedOrder.setCustomerName("Wise");
        expectedOrder.setState("OH");
        expectedOrder.setProductType("wood");
        expectedOrder.setProductArea(100);
        expectedOrder.setTaxRate(new BigDecimal("6.25"));
        expectedOrder.setCostPerSq(new BigDecimal("5.15"));
        expectedOrder.setLaborCostPerSq(new BigDecimal("4.75"));

        Order fromService = service.validateOrder(inputOrder);
        assertEquals(expectedOrder, fromService);
        this.tearDown();
    }

    @Test
    void testValidateOrderInvalidState() throws FlooringFilePersistenceException, FlooringInvalidAreaException, FlooringInvalidStateException, FlooringInvalidProductTypeException {
        this.setUp();
        Order inputOrder = new Order();
        inputOrder.setCustomerName("Wise");
        inputOrder.setState("NY");
        inputOrder.setProductType("wood");
        inputOrder.setProductArea(100);
        try {
            service.validateOrder(inputOrder);
            fail("Expected FlooringInvalidStateException not thrown.");
        } catch (FlooringInvalidStateException e) {
            return;
        }
        this.tearDown();
    }

    @Test
    void testValidateOrderInvalidProductType() throws FlooringFilePersistenceException, FlooringInvalidAreaException, FlooringInvalidStateException, FlooringInvalidProductTypeException {
        this.setUp();
        Order inputOrder = new Order();
        inputOrder.setCustomerName("Wise");
        inputOrder.setState("OH");
        inputOrder.setProductType("steal");
        inputOrder.setProductArea(100);
        try {
            service.validateOrder(inputOrder);
            fail("Expected FlooringInvalidProductTypeException not thrown.");
        } catch (FlooringInvalidProductTypeException e) {
            return;
        }
        this.tearDown();
    }

    @Test
    void testValidateOrderInvalidArea() throws FlooringFilePersistenceException, FlooringInvalidAreaException, FlooringInvalidStateException, FlooringInvalidProductTypeException {
        this.setUp();
        Order inputOrder = new Order();
        inputOrder.setCustomerName("Wise");
        inputOrder.setState("OH");
        inputOrder.setProductType("wood");
        inputOrder.setProductArea(0);
        try {
            service.validateOrder(inputOrder);
            fail("Expected FlooringInvalidAreaException not thrown.");
        } catch (FlooringInvalidAreaException e) {
            return;
        }
        this.tearDown();
    }

    @Test
    void testCalculateOrder() throws FlooringFilePersistenceException {
        this.setUp();
        Order inputOrder = new Order();
        inputOrder.setCustomerName("Wise");
        inputOrder.setState("OH");
        inputOrder.setProductType("wood");
        inputOrder.setProductArea(100);
        inputOrder.setTaxRate(new BigDecimal("6.25"));
        inputOrder.setCostPerSq(new BigDecimal("5.15"));
        inputOrder.setLaborCostPerSq(new BigDecimal("4.75"));

        Order expectedOrder = new Order();
        expectedOrder.setCustomerName("Wise");
        expectedOrder.setState("OH");
        expectedOrder.setProductType("wood");
        expectedOrder.setProductArea(100);
        expectedOrder.setTaxRate(new BigDecimal("6.25"));
        expectedOrder.setCostPerSq(new BigDecimal("5.15"));
        expectedOrder.setLaborCostPerSq(new BigDecimal("4.75"));
        expectedOrder.setTotalMaterialCost(new BigDecimal("515.00"));
        expectedOrder.setTotalLaborCost(new BigDecimal("475.00"));
        expectedOrder.setTotalTax(new BigDecimal("61.88"));
        expectedOrder.setTotalCost(new BigDecimal("1051.88"));

        Order fromService = service.calculateOrder(inputOrder);

        assertEquals(expectedOrder, fromService);
        this.tearDown();
    }

    @Test
    void testAddOrder() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        Order expectedOrder = new Order();
        expectedOrder.setCustomerName("Wise");
        expectedOrder.setState("OH");
        expectedOrder.setProductType("wood");
        expectedOrder.setProductArea(100);
        expectedOrder.setTaxRate(new BigDecimal("6.25"));
        expectedOrder.setCostPerSq(new BigDecimal("5.15"));
        expectedOrder.setLaborCostPerSq(new BigDecimal("4.75"));
        expectedOrder.setTotalMaterialCost(new BigDecimal("515.00"));
        expectedOrder.setTotalLaborCost(new BigDecimal("475.00"));
        expectedOrder.setTotalTax(new BigDecimal("61.88"));
        expectedOrder.setTotalCost(new BigDecimal("1051.88"));

        Order fromDao = service.addOrder(orderDate, expectedOrder);
        expectedOrder.setOrderID(4);

        assertEquals(expectedOrder, fromDao);
        tearDown();
    }

    @Test
    void testEditOrder() throws FlooringFilePersistenceException, FlooringOrderDateNotFoundException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        service.getOrderByDate(orderDate); //load the file first otherwise will get null pointer exception
        Order expectedOrder = new Order();
        expectedOrder.setOrderID(1);
        expectedOrder.setCustomerName("Willy");
        expectedOrder.setState("OH");
        expectedOrder.setProductType("wood");
        expectedOrder.setProductArea(100);
        expectedOrder.setTaxRate(new BigDecimal("6.25"));
        expectedOrder.setCostPerSq(new BigDecimal("5.15"));
        expectedOrder.setLaborCostPerSq(new BigDecimal("4.75"));
        expectedOrder.setTotalMaterialCost(new BigDecimal("515.00"));
        expectedOrder.setTotalLaborCost(new BigDecimal("475.00"));
        expectedOrder.setTotalTax(new BigDecimal("61.88"));
        expectedOrder.setTotalCost(new BigDecimal("1051.88"));

        Order fromDao = service.editOrder(orderDate, expectedOrder);

        assertEquals(expectedOrder, fromDao);
        tearDown();
    }

    @Test
    void testGetOrderSuccess() throws FlooringFilePersistenceException, FlooringOrderDateNotFoundException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        Order expectedOrder = new Order();
        expectedOrder.setOrderID(1);
        expectedOrder.setCustomerName("Wise");
        expectedOrder.setState("OH");
        expectedOrder.setProductType("wood");
        expectedOrder.setProductArea(100);
        expectedOrder.setTaxRate(new BigDecimal("6.25"));
        expectedOrder.setCostPerSq(new BigDecimal("5.15"));
        expectedOrder.setLaborCostPerSq(new BigDecimal("4.75"));
        expectedOrder.setTotalMaterialCost(new BigDecimal("515.00"));
        expectedOrder.setTotalLaborCost(new BigDecimal("475.00"));
        expectedOrder.setTotalTax(new BigDecimal("61.88"));
        expectedOrder.setTotalCost(new BigDecimal("1051.88"));

        Order fromDao = service.getOrder(orderDate, 1);

        assertEquals(expectedOrder, fromDao);
        tearDown();
    }

    @Test
    void testGetOrderInvalidDate() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-02-01");

        try {
            Order fromDao = service.getOrder(orderDate, 1);
            fail("Expected OrderNotFoundException was not thrown.");
        } catch (FlooringOrderDateNotFoundException e) {
            return;
        }
        tearDown();
    }

    @Test
    void testGetOrderInvalidID() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");

        try {
            Order fromDao = service.getOrder(orderDate, 999);
            fail("Expected OrderNotFoundException was not thrown.");
        } catch (FlooringOrderDateNotFoundException e) {
            return;
        }
        tearDown();
    }

    @Test
    void testRemoveOrder() throws FlooringFilePersistenceException, FlooringOrderDateNotFoundException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        service.getOrderByDate(orderDate);

        service.removeOrder(orderDate, 1);
        List<Order> listFromDao = service.getOrderByDate(orderDate);

        assertEquals(2, listFromDao.size());

        try {
            service.getOrder(orderDate, 1);
        } catch (FlooringOrderDateNotFoundException e) {
            return;
        }
    }

    @Test
    void getAllProduct() throws FlooringFilePersistenceException {
        setUp();
        List<Product> fromDao = service.getAllProduct();
        assertEquals(4, fromDao.size());
    }

    @Test
    void getAllTaxRate() throws FlooringFilePersistenceException {
        setUp();
        List<TaxRate> fromDao = service.getAllTaxRate();
        assertEquals(4, fromDao.size());
    }

    private void writeFile() throws FlooringFilePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new FlooringFilePersistenceException("-_Could not save item data to file.");
        }

        String item1 = "1::Wise::OH::6.25::wood::100.00::5.15::4.75::515.00::475.00::61.88::1051.88";
        String item2 = "2::Tom::PA::6.75::carpet::100.0::2.25::2.10::225.00::210.00::29.36::464.36";
        String item3 = "3::Willy::OH::6.25::carpet::100.0::2.25::2.10::225.00::210.00::27.19::462.19";
        out.println(item1);
        out.flush();
        out.println(item2);
        out.flush();
        out.println(item3);
        out.flush();
        out.close();
    }
}