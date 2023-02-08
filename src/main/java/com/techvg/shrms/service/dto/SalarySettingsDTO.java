package com.techvg.shrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.SalarySettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalarySettingsDTO implements Serializable {

    private Long id;

    private Double da;

    private Double hra;

    private Double employeeshare;

    private Double companyshare;

    private Instant salaryFrom;

    private Instant salaryTo;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDa() {
        return da;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public Double getHra() {
        return hra;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getEmployeeshare() {
        return employeeshare;
    }

    public void setEmployeeshare(Double employeeshare) {
        this.employeeshare = employeeshare;
    }

    public Double getCompanyshare() {
        return companyshare;
    }

    public void setCompanyshare(Double companyshare) {
        this.companyshare = companyshare;
    }

    public Instant getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Instant salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Instant getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Instant salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalarySettingsDTO)) {
            return false;
        }

        SalarySettingsDTO salarySettingsDTO = (SalarySettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salarySettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettingsDTO{" +
            "id=" + getId() +
            ", da=" + getDa() +
            ", hra=" + getHra() +
            ", employeeshare=" + getEmployeeshare() +
            ", companyshare=" + getCompanyshare() +
            ", salaryFrom='" + getSalaryFrom() + "'" +
            ", salaryTo='" + getSalaryTo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
