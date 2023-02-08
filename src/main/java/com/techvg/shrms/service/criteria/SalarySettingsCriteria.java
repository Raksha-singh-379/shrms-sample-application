package com.techvg.shrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.SalarySettings} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.SalarySettingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /salary-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalarySettingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter da;

    private DoubleFilter hra;

    private DoubleFilter employeeshare;

    private DoubleFilter companyshare;

    private InstantFilter salaryFrom;

    private InstantFilter salaryTo;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private Boolean distinct;

    public SalarySettingsCriteria() {}

    public SalarySettingsCriteria(SalarySettingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.da = other.da == null ? null : other.da.copy();
        this.hra = other.hra == null ? null : other.hra.copy();
        this.employeeshare = other.employeeshare == null ? null : other.employeeshare.copy();
        this.companyshare = other.companyshare == null ? null : other.companyshare.copy();
        this.salaryFrom = other.salaryFrom == null ? null : other.salaryFrom.copy();
        this.salaryTo = other.salaryTo == null ? null : other.salaryTo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SalarySettingsCriteria copy() {
        return new SalarySettingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getDa() {
        return da;
    }

    public DoubleFilter da() {
        if (da == null) {
            da = new DoubleFilter();
        }
        return da;
    }

    public void setDa(DoubleFilter da) {
        this.da = da;
    }

    public DoubleFilter getHra() {
        return hra;
    }

    public DoubleFilter hra() {
        if (hra == null) {
            hra = new DoubleFilter();
        }
        return hra;
    }

    public void setHra(DoubleFilter hra) {
        this.hra = hra;
    }

    public DoubleFilter getEmployeeshare() {
        return employeeshare;
    }

    public DoubleFilter employeeshare() {
        if (employeeshare == null) {
            employeeshare = new DoubleFilter();
        }
        return employeeshare;
    }

    public void setEmployeeshare(DoubleFilter employeeshare) {
        this.employeeshare = employeeshare;
    }

    public DoubleFilter getCompanyshare() {
        return companyshare;
    }

    public DoubleFilter companyshare() {
        if (companyshare == null) {
            companyshare = new DoubleFilter();
        }
        return companyshare;
    }

    public void setCompanyshare(DoubleFilter companyshare) {
        this.companyshare = companyshare;
    }

    public InstantFilter getSalaryFrom() {
        return salaryFrom;
    }

    public InstantFilter salaryFrom() {
        if (salaryFrom == null) {
            salaryFrom = new InstantFilter();
        }
        return salaryFrom;
    }

    public void setSalaryFrom(InstantFilter salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public InstantFilter getSalaryTo() {
        return salaryTo;
    }

    public InstantFilter salaryTo() {
        if (salaryTo == null) {
            salaryTo = new InstantFilter();
        }
        return salaryTo;
    }

    public void setSalaryTo(InstantFilter salaryTo) {
        this.salaryTo = salaryTo;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            deleted = new BooleanFilter();
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SalarySettingsCriteria that = (SalarySettingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(da, that.da) &&
            Objects.equals(hra, that.hra) &&
            Objects.equals(employeeshare, that.employeeshare) &&
            Objects.equals(companyshare, that.companyshare) &&
            Objects.equals(salaryFrom, that.salaryFrom) &&
            Objects.equals(salaryTo, that.salaryTo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            da,
            hra,
            employeeshare,
            companyshare,
            salaryFrom,
            salaryTo,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (da != null ? "da=" + da + ", " : "") +
            (hra != null ? "hra=" + hra + ", " : "") +
            (employeeshare != null ? "employeeshare=" + employeeshare + ", " : "") +
            (companyshare != null ? "companyshare=" + companyshare + ", " : "") +
            (salaryFrom != null ? "salaryFrom=" + salaryFrom + ", " : "") +
            (salaryTo != null ? "salaryTo=" + salaryTo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
