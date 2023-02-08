package com.techvg.shrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.BanksDetails} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.BanksDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /banks-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter accountNo;

    private StringFilter bankName;

    private StringFilter ifscCode;

    private StringFilter pan;

    private StringFilter branch;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private LongFilter employeeId;

    private Boolean distinct;

    public BanksDetailsCriteria() {}

    public BanksDetailsCriteria(BanksDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.pan = other.pan == null ? null : other.pan.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BanksDetailsCriteria copy() {
        return new BanksDetailsCriteria(this);
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

    public LongFilter getAccountNo() {
        return accountNo;
    }

    public LongFilter accountNo() {
        if (accountNo == null) {
            accountNo = new LongFilter();
        }
        return accountNo;
    }

    public void setAccountNo(LongFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public StringFilter ifscCode() {
        if (ifscCode == null) {
            ifscCode = new StringFilter();
        }
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public StringFilter getPan() {
        return pan;
    }

    public StringFilter pan() {
        if (pan == null) {
            pan = new StringFilter();
        }
        return pan;
    }

    public void setPan(StringFilter pan) {
        this.pan = pan;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public StringFilter branch() {
        if (branch == null) {
            branch = new StringFilter();
        }
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
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
        final BanksDetailsCriteria that = (BanksDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(pan, that.pan) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountNo,
            bankName,
            ifscCode,
            pan,
            branch,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            employeeId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (pan != null ? "pan=" + pan + ", " : "") +
            (branch != null ? "branch=" + branch + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
