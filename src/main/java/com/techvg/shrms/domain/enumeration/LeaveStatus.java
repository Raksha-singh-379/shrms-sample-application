package com.techvg.shrms.domain.enumeration;

/**
 * The LeaveStatus enumeration.
 */
public enum LeaveStatus {
    APPROVED("Approved"),
    PENDING("Pending"),
    REJECTED("Rejected"),
    CANCELLED("cancelled"),
    AVAILED("Availed");

    private final String value;

    LeaveStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
