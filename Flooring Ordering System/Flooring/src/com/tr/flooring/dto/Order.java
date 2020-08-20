package com.tr.flooring.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
    private int orderID;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private double productArea;
    private BigDecimal costPerSq;
    private BigDecimal laborCostPerSq;
    private BigDecimal totalMaterialCost;
    private BigDecimal totalLaborCost;
    private BigDecimal totalTax;
    private BigDecimal totalCost;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getProductArea() {
        return productArea;
    }

    public void setProductArea(double productArea) {
        this.productArea = productArea;
    }

    public BigDecimal getCostPerSq() {
        return costPerSq;
    }

    public void setCostPerSq(BigDecimal costPerSq) {
        this.costPerSq = costPerSq;
    }

    public BigDecimal getLaborCostPerSq() {
        return laborCostPerSq;
    }

    public void setLaborCostPerSq(BigDecimal laborCostPerSq) {
        this.laborCostPerSq = laborCostPerSq;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getOrderID() != order.getOrderID()) return false;
        if (Double.compare(order.getProductArea(), getProductArea()) != 0) return false;
        if (getCustomerName() != null ? !getCustomerName().equals(order.getCustomerName()) : order.getCustomerName() != null)
            return false;
        if (getState() != null ? !getState().equals(order.getState()) : order.getState() != null) return false;
        if (getTaxRate() != null ? !getTaxRate().equals(order.getTaxRate()) : order.getTaxRate() != null) return false;
        if (getProductType() != null ? !getProductType().equals(order.getProductType()) : order.getProductType() != null)
            return false;
        if (getCostPerSq() != null ? !getCostPerSq().equals(order.getCostPerSq()) : order.getCostPerSq() != null)
            return false;
        if (getLaborCostPerSq() != null ? !getLaborCostPerSq().equals(order.getLaborCostPerSq()) : order.getLaborCostPerSq() != null)
            return false;
        if (getTotalMaterialCost() != null ? !getTotalMaterialCost().equals(order.getTotalMaterialCost()) : order.getTotalMaterialCost() != null)
            return false;
        if (getTotalLaborCost() != null ? !getTotalLaborCost().equals(order.getTotalLaborCost()) : order.getTotalLaborCost() != null)
            return false;
        if (getTotalTax() != null ? !getTotalTax().equals(order.getTotalTax()) : order.getTotalTax() != null)
            return false;
        return getTotalCost() != null ? getTotalCost().equals(order.getTotalCost()) : order.getTotalCost() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getOrderID();
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getTaxRate() != null ? getTaxRate().hashCode() : 0);
        result = 31 * result + (getProductType() != null ? getProductType().hashCode() : 0);
        temp = Double.doubleToLongBits(getProductArea());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getCostPerSq() != null ? getCostPerSq().hashCode() : 0);
        result = 31 * result + (getLaborCostPerSq() != null ? getLaborCostPerSq().hashCode() : 0);
        result = 31 * result + (getTotalMaterialCost() != null ? getTotalMaterialCost().hashCode() : 0);
        result = 31 * result + (getTotalLaborCost() != null ? getTotalLaborCost().hashCode() : 0);
        result = 31 * result + (getTotalTax() != null ? getTotalTax().hashCode() : 0);
        result = 31 * result + (getTotalCost() != null ? getTotalCost().hashCode() : 0);
        return result;
    }
}
