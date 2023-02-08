package com.techvg.shrms.domain.enumeration;

/**
 * The SalaryBasis enumeration.
 */
public enum SalaryBasis {
    HOURLY("Hourly"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");

    private final String value;

    SalaryBasis(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
