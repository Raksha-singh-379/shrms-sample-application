package com.techvg.shrms.domain.enumeration;

/**
 * The ContactType enumeration.
 */
public enum ContactType {
    PRIMARY("Primary"),
    SECONDARY("Secondary");

    private final String value;

    ContactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
