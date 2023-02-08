package com.techvg.shrms.domain.enumeration;

/**
 * The Degree enumeration.
 */
public enum Degree {
    PG("Post_Graduation"),
    GRADUATION("Graduation"),
    HSC("Hsc"),
    SSC("Ssc"),
    DIPLOMA("Diploma"),
    OTHER("Other");

    private final String value;

    Degree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
