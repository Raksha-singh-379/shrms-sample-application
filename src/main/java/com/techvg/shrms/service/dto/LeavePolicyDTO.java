package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.EmployeeType;
import com.techvg.shrms.domain.enumeration.GenderLeave;
import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.LeavePolicy} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavePolicyDTO implements Serializable {

    private Long id;

    private String leaveType;

    private Boolean isCarryForword;

    private EmployeeType employeeType;

    private GenderLeave genderLeave;

    private Status status;

    private String totalLeave;

    private String maxLeave;

    private Boolean hasproRataLeave;

    private String description;

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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Boolean getIsCarryForword() {
        return isCarryForword;
    }

    public void setIsCarryForword(Boolean isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public GenderLeave getGenderLeave() {
        return genderLeave;
    }

    public void setGenderLeave(GenderLeave genderLeave) {
        this.genderLeave = genderLeave;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(String totalLeave) {
        this.totalLeave = totalLeave;
    }

    public String getMaxLeave() {
        return maxLeave;
    }

    public void setMaxLeave(String maxLeave) {
        this.maxLeave = maxLeave;
    }

    public Boolean getHasproRataLeave() {
        return hasproRataLeave;
    }

    public void setHasproRataLeave(Boolean hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof LeavePolicyDTO)) {
            return false;
        }

        LeavePolicyDTO leavePolicyDTO = (LeavePolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leavePolicyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavePolicyDTO{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", isCarryForword='" + getIsCarryForword() + "'" +
            ", employeeType='" + getEmployeeType() + "'" +
            ", genderLeave='" + getGenderLeave() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalLeave='" + getTotalLeave() + "'" +
            ", maxLeave='" + getMaxLeave() + "'" +
            ", hasproRataLeave='" + getHasproRataLeave() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
