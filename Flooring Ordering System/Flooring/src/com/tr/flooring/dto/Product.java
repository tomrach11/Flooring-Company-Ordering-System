package com.tr.flooring.dto;

import java.math.BigDecimal;

public class Product {
    private String productType;
    private BigDecimal costPerSq;
    private BigDecimal laborCostPerSq;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!getProductType().equals(product.getProductType())) return false;
        if (!getCostPerSq().equals(product.getCostPerSq())) return false;
        return getLaborCostPerSq().equals(product.getLaborCostPerSq());
    }

    @Override
    public int hashCode() {
        int result = getProductType().hashCode();
        result = 31 * result + getCostPerSq().hashCode();
        result = 31 * result + getLaborCostPerSq().hashCode();
        return result;
    }
}
