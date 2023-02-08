package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.LeaveStatus;
import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.LeaveApplication} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.LeaveApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApplicationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    /**
     * Class for filtering LeaveStatus
     */
    public static class LeaveStatusFilter extends Filter<LeaveStatus> {

        public LeaveStatusFilter() {}

        public LeaveStatusFilter(LeaveStatusFilter filter) {
            super(filter);
        }

        @Override
        public LeaveStatusFilter copy() {
            return new LeaveStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leaveType;

    private LongFilter balanceLeave;

    private LongFilter noOfDays;

    private StringFilter reason;

    private LongFilter year;

    private InstantFilter formDate;

    private InstantFilter toDate;

    private StatusFilter status;

    private LeaveStatusFilter leaveStatus;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private LongFilter leavePolicyId;

    private Boolean distinct;

    public LeaveApplicationCriteria() {}

    public LeaveApplicationCriteria(LeaveApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leaveType = other.leaveType == null ? null : other.leaveType.copy();
        this.balanceLeave = other.balanceLeave == null ? null : other.balanceLeave.copy();
        this.noOfDays = other.noOfDays == null ? null : other.noOfDays.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.formDate = other.formDate == null ? null : other.formDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.leaveStatus = other.leaveStatus == null ? null : other.leaveStatus.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.leavePolicyId = other.leavePolicyId == null ? null : other.leavePolicyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveApplicationCriteria copy() {
        return new LeaveApplicationCriteria(this);
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

    public StringFilter getLeaveType() {
        return leaveType;
    }

    public StringFilter leaveType() {
        if (leaveType == null) {
            leaveType = new StringFilter();
        }
        return leaveType;
    }

    public void setLeaveType(StringFilter leaveType) {
        this.leaveType = leaveType;
    }

    public LongFilter getBalanceLeave() {
        return balanceLeave;
    }

    public LongFilter balanceLeave() {
        if (balanceLeave == null) {
            balanceLeave = new LongFilter();
        }
        return balanceLeave;
    }

    public void setBalanceLeave(LongFilter balanceLeave) {
        this.balanceLeave = balanceLeave;
    }

    public LongFilter getNoOfDays() {
        return noOfDays;
    }

    public LongFilter noOfDays() {
        if (noOfDays == null) {
            noOfDays = new LongFilter();
        }
        return noOfDays;
    }

    public void setNoOfDays(LongFilter noOfDays) {
        this.noOfDays = noOfDays;
    }

    public StringFilter getReason() {
        return reason;
    }

    public StringFilter reason() {
        if (reason == null) {
            reason = new StringFilter();
        }
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public LongFilter getYear() {
        return year;
    }

    public LongFilter year() {
        if (year == null) {
            year = new LongFilter();
        }
        return year;
    }

    public void setYear(LongFilter year) {
        this.year = year;
    }

    public InstantFilter getFormDate() {
        return formDate;
    }

    public InstantFilter formDate() {
        if (formDate == null) {
            formDate = new InstantFilter();
        }
        return formDate;
    }

    public void setFormDate(InstantFilter formDate) {
        this.formDate = formDate;
    }

    public InstantFilter getToDate() {
        return toDate;
    }

    public InstantFilter toDate() {
        if (toDate == null) {
            toDate = new InstantFilter();
        }
        return toDate;
    }

    public void setToDate(InstantFilter toDate) {
        this.toDate = toDate;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public LeaveStatusFilter getLeaveStatus() {
        return leaveStatus;
    }

    public LeaveStatusFilter leaveStatus() {
        if (leaveStatus == null) {
            leaveStatus = new LeaveStatusFilter();
        }
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatusFilter leaveStatus) {
        this.leaveStatus = leaveStatus;
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

    public LongFilter getLeavePolicyId() {
        return leavePolicyId;
    }

    public LongFilter leavePolicyId() {
        if (leavePolicyId == null) {
            leavePolicyId = new LongFilter();
        }
        return leavePolicyId;
    }

    public void setLeavePolicyId(LongFilter leavePolicyId) {
        this.leavePolicyId = leavePolicyId;
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
        final LeaveApplicationCriteria that = (LeaveApplicationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leaveType, that.leaveType) &&
            Objects.equals(balanceLeave, that.balanceLeave) &&
            Objects.equals(noOfDays, that.noOfDays) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(year, that.year) &&
            Objects.equals(formDate, that.formDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(leaveStatus, that.leaveStatus) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(leavePolicyId, that.leavePolicyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            leaveType,
            balanceLeave,
            noOfDays,
            reason,
            year,
            formDate,
            toDate,
            status,
            leaveStatus,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            employeeId,
            leavePolicyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leaveType != null ? "leaveType=" + leaveType + ", " : "") +
            (balanceLeave != null ? "balanceLeave=" + balanceLeave + ", " : "") +
            (noOfDays != null ? "noOfDays=" + noOfDays + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (formDate != null ? "formDate=" + formDate + ", " : "") +
            (toDate != null ? "toDate=" + toDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (leaveStatus != null ? "leaveStatus=" + leaveStatus + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (leavePolicyId != null ? "leavePolicyId=" + leavePolicyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
