package com.techvg.shrms.domain.enumeration;

/**
 * The BloodGroup enumeration.
 */
public enum BloodGroup {
    A_POSITIVE("A_Positive"),
    B_POSITIVE("B_Positive"),
    O_POSITIVE("O_Positive"),
    O_NEGATIVE("O_Negative"),
    A_NEGATIVE("A_Negative"),
    B_NEGATIVE("B_Negative"),
    AB_POSITIVE("AB_Positive"),
    AB_NEGATIVE("AB_Negative");

    private final String value;

    BloodGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
