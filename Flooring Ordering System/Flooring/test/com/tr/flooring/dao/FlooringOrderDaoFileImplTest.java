package com.tr.flooring.dao;

import com.tr.flooring.dto.Order;
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

class FlooringOrderDaoFileImplTest {

    FlooringOrderDaoFileImpl dao = new FlooringOrderDaoFileImpl();

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
    void testCreateWithExistedOrderFile() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");

        Order order = new Order();
        order.setOrderID(4);
        order.setCustomerName("Jane");
        order.setState("OH");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("wood");
        order.setProductArea(Double.parseDouble("100.00"));
        order.setCostPerSq(new BigDecimal("5.15"));
        order.setLaborCostPerSq(new BigDecimal("4.75"));
        order.setTotalMaterialCost(new BigDecimal("515.00"));
        order.setTotalLaborCost(new BigDecimal("475.00"));
        order.setTotalTax(new BigDecimal("61.88"));
        order.setTotalCost(new BigDecimal("1051.88"));

        dao.create(orderDate, order);
        int id = order.getOrderID();
        Order fromDao = dao.readById(orderDate, id);

        assertEquals(order, fromDao);
        tearDown();
    }

    @Test
    void testCreateWithNewOrderFile() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-02-01");

        Order order = new Order();
        order.setOrderID(1);
        order.setCustomerName("Jane");
        order.setState("OH");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("wood");
        order.setProductArea(Double.parseDouble("100.00"));
        order.setCostPerSq(new BigDecimal("5.15"));
        order.setLaborCostPerSq(new BigDecimal("4.75"));
        order.setTotalMaterialCost(new BigDecimal("515.00"));
        order.setTotalLaborCost(new BigDecimal("475.00"));
        order.setTotalTax(new BigDecimal("61.88"));
        order.setTotalCost(new BigDecimal("1051.88"));

        dao.create(orderDate, order);
        int id = order.getOrderID();
        Order fromDao = dao.readById(orderDate, id);

        assertEquals(order, fromDao);
        tearDown();
    }

    @Test
    void testReadAll() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        List<Order> fromDao = dao.readAll(orderDate);
        assertEquals(3, fromDao.size());
        tearDown();
    }

    @Test
    void testReadById() throws FlooringFilePersistenceException {
        setUp();
        tearDown();
    }

    @Test
    void testUpdate() throws FlooringFilePersistenceException {
        LocalDate orderDate = LocalDate.parse("1000-01-01");

        Order order = new Order();
        order.setOrderID(3);
        order.setCustomerName("Jane");
        order.setState("OH");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("wood");
        order.setProductArea(Double.parseDouble("100.00"));
        order.setCostPerSq(new BigDecimal("5.15"));
        order.setLaborCostPerSq(new BigDecimal("4.75"));
        order.setTotalMaterialCost(new BigDecimal("515.00"));
        order.setTotalLaborCost(new BigDecimal("475.00"));
        order.setTotalTax(new BigDecimal("61.88"));
        order.setTotalCost(new BigDecimal("1051.88"));

        dao.readById(orderDate, order.getOrderID()); // to load the file
        dao.update(orderDate, order);
        Order fromDao = dao.readById(orderDate, order.getOrderID());

        assertEquals(order, fromDao);
    }

    @Test
    void testRemove() throws FlooringFilePersistenceException {
        setUp();
        LocalDate orderDate = LocalDate.parse("1000-01-01");
        dao.readAll(orderDate); // to load this order date
        dao.remove(orderDate, 3);

        List<Order> orderList = dao.readAll(orderDate);
        Order removedOrder = dao.readById(orderDate, 3);

        assertEquals(2, orderList.size());
        assertNull(removedOrder);
        tearDown();
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