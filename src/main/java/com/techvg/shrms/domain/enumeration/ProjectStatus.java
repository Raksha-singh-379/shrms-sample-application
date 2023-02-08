package com.techvg.shrms.domain.enumeration;

/**
 * The ProjectStatus enumeration.
 */
public enum ProjectStatus {
    INPROGRESS("inProgress"),
    COMPLETED("completed"),
    ONHOLD("Onhold");

    private final String value;

    ProjectStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
