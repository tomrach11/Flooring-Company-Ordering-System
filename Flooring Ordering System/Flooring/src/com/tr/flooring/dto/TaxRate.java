package com.tr.flooring.dto;

import java.math.BigDecimal;

public class TaxRate {
    private String state;

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    private BigDecimal taxRate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxRate)) return false;

        TaxRate taxRate1 = (TaxRate) o;

        if (!getState().equals(taxRate1.getState())) return false;
        return getTaxRate().equals(taxRate1.getTaxRate());
    }

    @Override
    public int hashCode() {
        int result = getState().hashCode();
        result = 31 * result + getTaxRate().hashCode();
        return result;
    }
}
