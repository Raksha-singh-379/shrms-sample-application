package com.techvg.shrms.domain.enumeration;

/**
 * The Relationship enumeration.
 */
public enum Relationship {
    FATHER("Father"),
    MOTHER("Mother"),
    SPOUS("Spous"),
    CHILD("Child"),
    SISTER("Sister"),
    BROTHER("Brother");

    private final String value;

    Relationship(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
