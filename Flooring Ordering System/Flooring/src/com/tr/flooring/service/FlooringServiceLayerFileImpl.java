package com.tr.flooring.service;

import com.tr.flooring.dao.*;
import com.tr.flooring.dto.Order;
import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FlooringServiceLayerFileImpl implements FlooringServiceLayer {
    FlooringOrderDao orderDao;
    FlooringProductDao productDao;
    FlooringTaxDao taxDao;
    FlooringAuditDao audit;

    public FlooringServiceLayerFileImpl(FlooringOrderDao orderDao, FlooringProductDao productDao, FlooringTaxDao taxDao, FlooringAuditDao audit) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.audit = audit;
    }

    @Override
    public List<Order> getOrderByDate(LocalDate orderDate) throws FlooringOrderDateNotFoundException, FlooringFilePersistenceException {
        List<Order> orderList = orderDao.readAll(orderDate);
        if (orderList == null || orderList.size() < 1) {
            throw new FlooringOrderDateNotFoundException("Order not found."); //check if the order date is valid or not
        } else {
            return orderList;
        }
    }

    @Override
    public Order validateOrder(Order order) throws FlooringInvalidStateException, FlooringInvalidProductTypeException, FlooringInvalidAreaException, FlooringFilePersistenceException {
        TaxRate taxrate = taxDao.readById(order.getState());
        Product product = productDao.readById(order.getProductType());

        if (product == null) {
            throw new FlooringInvalidProductTypeException ("Error: Invalid Product Type."); //validate
        }
        else if (taxrate == null) {
            throw new FlooringInvalidStateException("Error: Invalid State.");
        }
        else if (order.getProductArea() < 100) {
            throw new FlooringInvalidAreaException("Error: Invalid Product Area, Area need to be more than 100Sq.");
        } else {
            order.setTaxRate(taxrate.getTaxRate()); //fill out order
            order.setCostPerSq(product.getCostPerSq());
            order.setLaborCostPerSq(product.getLaborCostPerSq());
        }
        return order;
    }

    @Override
    public Order calculateOrder(Order order) {

        BigDecimal area = new BigDecimal(order.getProductArea());
        BigDecimal taxRateInPercentage = order.getTaxRate().divide(new BigDecimal(100)).setScale(4); // setup tax rate to calculate

        BigDecimal materialCost = area.multiply(order.getCostPerSq()).setScale(2, RoundingMode.HALF_UP); //calculate everything
        BigDecimal laborCost = area.multiply(order.getLaborCostPerSq()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal costBeforeTax = materialCost.add(laborCost);
        BigDecimal tax = costBeforeTax.multiply(taxRateInPercentage).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = costBeforeTax.add(tax);

        order.setTotalMaterialCost(materialCost); //fill out to order
        order.setTotalLaborCost(laborCost);
        order.setTotalTax(tax);
        order.setTotalCost(total);

        return order;
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws FlooringFilePersistenceException {
        Order addedOrder = orderDao.create(orderDate, order);
        audit.writeToText("Add::orderDate-" + orderDate + ":orderID-" + addedOrder.getOrderID());
        return addedOrder;
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order order) {
        Order editedOrder = orderDao.update(orderDate, order);
        audit.writeToText("Edit::orderDate-" + orderDate + ":orderID-" + editedOrder.getOrderID());
        return editedOrder;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderID) throws FlooringOrderDateNotFoundException, FlooringFilePersistenceException {
        Order order = orderDao.readById(orderDate, orderID);
        if (order == null) {
            throw new FlooringOrderDateNotFoundException("Order not found.");
        } else return order;
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderID) {
        Order removedOrder = orderDao.remove(orderDate, orderID);
        audit.writeToText("Remove::orderDate-" + orderDate + ":orderID-" + removedOrder.getOrderID());
        return removedOrder;
    }

    @Override
    public void saveData() throws FlooringFilePersistenceException {
        orderDao.save();
        audit.writeToText("Save Date");
        audit.writeAuditEntry();
    }

    @Override
    public List<Product> getAllProduct() throws FlooringFilePersistenceException {
        return productDao.readAll();
    }

    @Override
    public List<TaxRate> getAllTaxRate() throws FlooringFilePersistenceException {
        return taxDao.readAll();
    }
}
