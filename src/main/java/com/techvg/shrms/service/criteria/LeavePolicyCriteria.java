package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.EmployeeType;
import com.techvg.shrms.domain.enumeration.GenderLeave;
import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.LeavePolicy} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.LeavePolicyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-policies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavePolicyCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EmployeeType
     */
    public static class EmployeeTypeFilter extends Filter<EmployeeType> {

        public EmployeeTypeFilter() {}

        public EmployeeTypeFilter(EmployeeTypeFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeTypeFilter copy() {
            return new EmployeeTypeFilter(this);
        }
    }

    /**
     * Class for filtering GenderLeave
     */
    public static class GenderLeaveFilter extends Filter<GenderLeave> {

        public GenderLeaveFilter() {}

        public GenderLeaveFilter(GenderLeaveFilter filter) {
            super(filter);
        }

        @Override
        public GenderLeaveFilter copy() {
            return new GenderLeaveFilter(this);
        }
    }

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leaveType;

    private BooleanFilter isCarryForword;

    private EmployeeTypeFilter employeeType;

    private GenderLeaveFilter genderLeave;

    private StatusFilter status;

    private StringFilter totalLeave;

    private StringFilter maxLeave;

    private BooleanFilter hasproRataLeave;

    private StringFilter description;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private Boolean distinct;

    public LeavePolicyCriteria() {}

    public LeavePolicyCriteria(LeavePolicyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leaveType = other.leaveType == null ? null : other.leaveType.copy();
        this.isCarryForword = other.isCarryForword == null ? null : other.isCarryForword.copy();
        this.employeeType = other.employeeType == null ? null : other.employeeType.copy();
        this.genderLeave = other.genderLeave == null ? null : other.genderLeave.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.totalLeave = other.totalLeave == null ? null : other.totalLeave.copy();
        this.maxLeave = other.maxLeave == null ? null : other.maxLeave.copy();
        this.hasproRataLeave = other.hasproRataLeave == null ? null : other.hasproRataLeave.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeavePolicyCriteria copy() {
        return new LeavePolicyCriteria(this);
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

    public BooleanFilter getIsCarryForword() {
        return isCarryForword;
    }

    public BooleanFilter isCarryForword() {
        if (isCarryForword == null) {
            isCarryForword = new BooleanFilter();
        }
        return isCarryForword;
    }

    public void setIsCarryForword(BooleanFilter isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public EmployeeTypeFilter getEmployeeType() {
        return employeeType;
    }

    public EmployeeTypeFilter employeeType() {
        if (employeeType == null) {
            employeeType = new EmployeeTypeFilter();
        }
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeFilter employeeType) {
        this.employeeType = employeeType;
    }

    public GenderLeaveFilter getGenderLeave() {
        return genderLeave;
    }

    public GenderLeaveFilter genderLeave() {
        if (genderLeave == null) {
            genderLeave = new GenderLeaveFilter();
        }
        return genderLeave;
    }

    public void setGenderLeave(GenderLeaveFilter genderLeave) {
        this.genderLeave = genderLeave;
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

    public StringFilter getTotalLeave() {
        return totalLeave;
    }

    public StringFilter totalLeave() {
        if (totalLeave == null) {
            totalLeave = new StringFilter();
        }
        return totalLeave;
    }

    public void setTotalLeave(StringFilter totalLeave) {
        this.totalLeave = totalLeave;
    }

    public StringFilter getMaxLeave() {
        return maxLeave;
    }

    public StringFilter maxLeave() {
        if (maxLeave == null) {
            maxLeave = new StringFilter();
        }
        return maxLeave;
    }

    public void setMaxLeave(StringFilter maxLeave) {
        this.maxLeave = maxLeave;
    }

    public BooleanFilter getHasproRataLeave() {
        return hasproRataLeave;
    }

    public BooleanFilter hasproRataLeave() {
        if (hasproRataLeave == null) {
            hasproRataLeave = new BooleanFilter();
        }
        return hasproRataLeave;
    }

    public void setHasproRataLeave(BooleanFilter hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final LeavePolicyCriteria that = (LeavePolicyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leaveType, that.leaveType) &&
            Objects.equals(isCarryForword, that.isCarryForword) &&
            Objects.equals(employeeType, that.employeeType) &&
            Objects.equals(genderLeave, that.genderLeave) &&
            Objects.equals(status, that.status) &&
            Objects.equals(totalLeave, that.totalLeave) &&
            Objects.equals(maxLeave, that.maxLeave) &&
            Objects.equals(hasproRataLeave, that.hasproRataLeave) &&
            Objects.equals(description, that.description) &&
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
            leaveType,
            isCarryForword,
            employeeType,
            genderLeave,
            status,
            totalLeave,
            maxLeave,
            hasproRataLeave,
            description,
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
        return "LeavePolicyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leaveType != null ? "leaveType=" + leaveType + ", " : "") +
            (isCarryForword != null ? "isCarryForword=" + isCarryForword + ", " : "") +
            (employeeType != null ? "employeeType=" + employeeType + ", " : "") +
            (genderLeave != null ? "genderLeave=" + genderLeave + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (totalLeave != null ? "totalLeave=" + totalLeave + ", " : "") +
            (maxLeave != null ? "maxLeave=" + maxLeave + ", " : "") +
            (hasproRataLeave != null ? "hasproRataLeave=" + hasproRataLeave + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
