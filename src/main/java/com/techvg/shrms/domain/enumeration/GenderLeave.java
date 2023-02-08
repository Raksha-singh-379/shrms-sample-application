package com.techvg.shrms.domain.enumeration;

/**
 * The GenderLeave enumeration.
 */
public enum GenderLeave {
    MALE_ONLY("Male_Only"),
    FEMALE_ONLY("Female_Only"),
    FOR_ALL("For_All");

    private final String value;

    GenderLeave(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
