package com.techvg.shrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalarySettings.
 */
@Entity
@Table(name = "salary_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalarySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "da")
    private Double da;

    @Column(name = "hra")
    private Double hra;

    @Column(name = "employeeshare")
    private Double employeeshare;

    @Column(name = "companyshare")
    private Double companyshare;

    @Column(name = "salary_from")
    private Instant salaryFrom;

    @Column(name = "salary_to")
    private Instant salaryTo;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalarySettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDa() {
        return this.da;
    }

    public SalarySettings da(Double da) {
        this.setDa(da);
        return this;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public Double getHra() {
        return this.hra;
    }

    public SalarySettings hra(Double hra) {
        this.setHra(hra);
        return this;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getEmployeeshare() {
        return this.employeeshare;
    }

    public SalarySettings employeeshare(Double employeeshare) {
        this.setEmployeeshare(employeeshare);
        return this;
    }

    public void setEmployeeshare(Double employeeshare) {
        this.employeeshare = employeeshare;
    }

    public Double getCompanyshare() {
        return this.companyshare;
    }

    public SalarySettings companyshare(Double companyshare) {
        this.setCompanyshare(companyshare);
        return this;
    }

    public void setCompanyshare(Double companyshare) {
        this.companyshare = companyshare;
    }

    public Instant getSalaryFrom() {
        return this.salaryFrom;
    }

    public SalarySettings salaryFrom(Instant salaryFrom) {
        this.setSalaryFrom(salaryFrom);
        return this;
    }

    public void setSalaryFrom(Instant salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Instant getSalaryTo() {
        return this.salaryTo;
    }

    public SalarySettings salaryTo(Instant salaryTo) {
        this.setSalaryTo(salaryTo);
        return this;
    }

    public void setSalaryTo(Instant salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SalarySettings lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SalarySettings lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SalarySettings createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SalarySettings createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public SalarySettings deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalarySettings)) {
            return false;
        }
        return id != null && id.equals(((SalarySettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettings{" +
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
