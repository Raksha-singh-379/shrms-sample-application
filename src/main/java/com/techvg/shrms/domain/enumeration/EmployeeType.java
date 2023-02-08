package com.techvg.shrms.domain.enumeration;

/**
 * The EmployeeType enumeration.
 */
public enum EmployeeType {
    PERMANENT("Permanent"),
    CONTRACT("Contract"),
    TRAINEE("Trainee"),
    TEMPORARY("Temporary");

    private final String value;

    EmployeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
