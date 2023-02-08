package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.SalaryBasis;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.EmpSalaryInfo} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.EmpSalaryInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emp-salary-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpSalaryInfoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SalaryBasis
     */
    public static class SalaryBasisFilter extends Filter<SalaryBasis> {

        public SalaryBasisFilter() {}

        public SalaryBasisFilter(SalaryBasisFilter filter) {
            super(filter);
        }

        @Override
        public SalaryBasisFilter copy() {
            return new SalaryBasisFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SalaryBasisFilter salaryBasis;

    private DoubleFilter amount;

    private StringFilter paymentType;

    private BooleanFilter isPfContribution;

    private StringFilter pfNumber;

    private DoubleFilter pfRate;

    private StringFilter additionalPfRate;

    private DoubleFilter totalPfRate;

    private BooleanFilter isEsiContribution;

    private StringFilter esiNumber;

    private DoubleFilter esiRate;

    private StringFilter additionalEsiRate;

    private DoubleFilter totalEsiRate;

    private LongFilter employeId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmpSalaryInfoCriteria() {}

    public EmpSalaryInfoCriteria(EmpSalaryInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.salaryBasis = other.salaryBasis == null ? null : other.salaryBasis.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.isPfContribution = other.isPfContribution == null ? null : other.isPfContribution.copy();
        this.pfNumber = other.pfNumber == null ? null : other.pfNumber.copy();
        this.pfRate = other.pfRate == null ? null : other.pfRate.copy();
        this.additionalPfRate = other.additionalPfRate == null ? null : other.additionalPfRate.copy();
        this.totalPfRate = other.totalPfRate == null ? null : other.totalPfRate.copy();
        this.isEsiContribution = other.isEsiContribution == null ? null : other.isEsiContribution.copy();
        this.esiNumber = other.esiNumber == null ? null : other.esiNumber.copy();
        this.esiRate = other.esiRate == null ? null : other.esiRate.copy();
        this.additionalEsiRate = other.additionalEsiRate == null ? null : other.additionalEsiRate.copy();
        this.totalEsiRate = other.totalEsiRate == null ? null : other.totalEsiRate.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmpSalaryInfoCriteria copy() {
        return new EmpSalaryInfoCriteria(this);
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

    public SalaryBasisFilter getSalaryBasis() {
        return salaryBasis;
    }

    public SalaryBasisFilter salaryBasis() {
        if (salaryBasis == null) {
            salaryBasis = new SalaryBasisFilter();
        }
        return salaryBasis;
    }

    public void setSalaryBasis(SalaryBasisFilter salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getPaymentType() {
        return paymentType;
    }

    public StringFilter paymentType() {
        if (paymentType == null) {
            paymentType = new StringFilter();
        }
        return paymentType;
    }

    public void setPaymentType(StringFilter paymentType) {
        this.paymentType = paymentType;
    }

    public BooleanFilter getIsPfContribution() {
        return isPfContribution;
    }

    public BooleanFilter isPfContribution() {
        if (isPfContribution == null) {
            isPfContribution = new BooleanFilter();
        }
        return isPfContribution;
    }

    public void setIsPfContribution(BooleanFilter isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public StringFilter getPfNumber() {
        return pfNumber;
    }

    public StringFilter pfNumber() {
        if (pfNumber == null) {
            pfNumber = new StringFilter();
        }
        return pfNumber;
    }

    public void setPfNumber(StringFilter pfNumber) {
        this.pfNumber = pfNumber;
    }

    public DoubleFilter getPfRate() {
        return pfRate;
    }

    public DoubleFilter pfRate() {
        if (pfRate == null) {
            pfRate = new DoubleFilter();
        }
        return pfRate;
    }

    public void setPfRate(DoubleFilter pfRate) {
        this.pfRate = pfRate;
    }

    public StringFilter getAdditionalPfRate() {
        return additionalPfRate;
    }

    public StringFilter additionalPfRate() {
        if (additionalPfRate == null) {
            additionalPfRate = new StringFilter();
        }
        return additionalPfRate;
    }

    public void setAdditionalPfRate(StringFilter additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public DoubleFilter getTotalPfRate() {
        return totalPfRate;
    }

    public DoubleFilter totalPfRate() {
        if (totalPfRate == null) {
            totalPfRate = new DoubleFilter();
        }
        return totalPfRate;
    }

    public void setTotalPfRate(DoubleFilter totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public BooleanFilter getIsEsiContribution() {
        return isEsiContribution;
    }

    public BooleanFilter isEsiContribution() {
        if (isEsiContribution == null) {
            isEsiContribution = new BooleanFilter();
        }
        return isEsiContribution;
    }

    public void setIsEsiContribution(BooleanFilter isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public StringFilter getEsiNumber() {
        return esiNumber;
    }

    public StringFilter esiNumber() {
        if (esiNumber == null) {
            esiNumber = new StringFilter();
        }
        return esiNumber;
    }

    public void setEsiNumber(StringFilter esiNumber) {
        this.esiNumber = esiNumber;
    }

    public DoubleFilter getEsiRate() {
        return esiRate;
    }

    public DoubleFilter esiRate() {
        if (esiRate == null) {
            esiRate = new DoubleFilter();
        }
        return esiRate;
    }

    public void setEsiRate(DoubleFilter esiRate) {
        this.esiRate = esiRate;
    }

    public StringFilter getAdditionalEsiRate() {
        return additionalEsiRate;
    }

    public StringFilter additionalEsiRate() {
        if (additionalEsiRate == null) {
            additionalEsiRate = new StringFilter();
        }
        return additionalEsiRate;
    }

    public void setAdditionalEsiRate(StringFilter additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public DoubleFilter getTotalEsiRate() {
        return totalEsiRate;
    }

    public DoubleFilter totalEsiRate() {
        if (totalEsiRate == null) {
            totalEsiRate = new DoubleFilter();
        }
        return totalEsiRate;
    }

    public void setTotalEsiRate(DoubleFilter totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
    }

    public LongFilter getEmployeId() {
        return employeId;
    }

    public LongFilter employeId() {
        if (employeId == null) {
            employeId = new LongFilter();
        }
        return employeId;
    }

    public void setEmployeId(LongFilter employeId) {
        this.employeId = employeId;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final EmpSalaryInfoCriteria that = (EmpSalaryInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(salaryBasis, that.salaryBasis) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(isPfContribution, that.isPfContribution) &&
            Objects.equals(pfNumber, that.pfNumber) &&
            Objects.equals(pfRate, that.pfRate) &&
            Objects.equals(additionalPfRate, that.additionalPfRate) &&
            Objects.equals(totalPfRate, that.totalPfRate) &&
            Objects.equals(isEsiContribution, that.isEsiContribution) &&
            Objects.equals(esiNumber, that.esiNumber) &&
            Objects.equals(esiRate, that.esiRate) &&
            Objects.equals(additionalEsiRate, that.additionalEsiRate) &&
            Objects.equals(totalEsiRate, that.totalEsiRate) &&
            Objects.equals(employeId, that.employeId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            salaryBasis,
            amount,
            paymentType,
            isPfContribution,
            pfNumber,
            pfRate,
            additionalPfRate,
            totalPfRate,
            isEsiContribution,
            esiNumber,
            esiRate,
            additionalEsiRate,
            totalEsiRate,
            employeId,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpSalaryInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (salaryBasis != null ? "salaryBasis=" + salaryBasis + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (isPfContribution != null ? "isPfContribution=" + isPfContribution + ", " : "") +
            (pfNumber != null ? "pfNumber=" + pfNumber + ", " : "") +
            (pfRate != null ? "pfRate=" + pfRate + ", " : "") +
            (additionalPfRate != null ? "additionalPfRate=" + additionalPfRate + ", " : "") +
            (totalPfRate != null ? "totalPfRate=" + totalPfRate + ", " : "") +
            (isEsiContribution != null ? "isEsiContribution=" + isEsiContribution + ", " : "") +
            (esiNumber != null ? "esiNumber=" + esiNumber + ", " : "") +
            (esiRate != null ? "esiRate=" + esiRate + ", " : "") +
            (additionalEsiRate != null ? "additionalEsiRate=" + additionalEsiRate + ", " : "") +
            (totalEsiRate != null ? "totalEsiRate=" + totalEsiRate + ", " : "") +
            (employeId != null ? "employeId=" + employeId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
