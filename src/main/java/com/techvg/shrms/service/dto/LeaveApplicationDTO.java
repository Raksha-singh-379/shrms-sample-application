package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.LeaveStatus;
import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.LeaveApplication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApplicationDTO implements Serializable {

    private Long id;

    private String leaveType;

    private Long balanceLeave;

    private Long noOfDays;

    private String reason;

    private Long year;

    private Instant formDate;

    private Instant toDate;

    private Status status;

    private LeaveStatus leaveStatus;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    private EmployeeDTO employee;

    private LeavePolicyDTO leavePolicy;

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

    public Long getBalanceLeave() {
        return balanceLeave;
    }

    public void setBalanceLeave(Long balanceLeave) {
        this.balanceLeave = balanceLeave;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Instant getFormDate() {
        return formDate;
    }

    public void setFormDate(Instant formDate) {
        this.formDate = formDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
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

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public LeavePolicyDTO getLeavePolicy() {
        return leavePolicy;
    }

    public void setLeavePolicy(LeavePolicyDTO leavePolicy) {
        this.leavePolicy = leavePolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApplicationDTO)) {
            return false;
        }

        LeaveApplicationDTO leaveApplicationDTO = (LeaveApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leaveApplicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationDTO{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", balanceLeave=" + getBalanceLeave() +
            ", noOfDays=" + getNoOfDays() +
            ", reason='" + getReason() + "'" +
            ", year=" + getYear() +
            ", formDate='" + getFormDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", leaveStatus='" + getLeaveStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", employee=" + getEmployee() +
            ", leavePolicy=" + getLeavePolicy() +
            "}";
    }
}
