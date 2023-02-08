package com.techvg.shrms.domain;

import com.techvg.shrms.domain.enumeration.LeaveStatus;
import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LeaveApplication.
 */
@Entity
@Table(name = "leave_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "balance_leave")
    private Long balanceLeave;

    @Column(name = "no_of_days")
    private Long noOfDays;

    @Column(name = "reason")
    private String reason;

    @Column(name = "year")
    private Long year;

    @Column(name = "form_date")
    private Instant formDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    private LeaveStatus leaveStatus;

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

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeavePolicy leavePolicy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveApplication id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeaveType() {
        return this.leaveType;
    }

    public LeaveApplication leaveType(String leaveType) {
        this.setLeaveType(leaveType);
        return this;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Long getBalanceLeave() {
        return this.balanceLeave;
    }

    public LeaveApplication balanceLeave(Long balanceLeave) {
        this.setBalanceLeave(balanceLeave);
        return this;
    }

    public void setBalanceLeave(Long balanceLeave) {
        this.balanceLeave = balanceLeave;
    }

    public Long getNoOfDays() {
        return this.noOfDays;
    }

    public LeaveApplication noOfDays(Long noOfDays) {
        this.setNoOfDays(noOfDays);
        return this;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getReason() {
        return this.reason;
    }

    public LeaveApplication reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getYear() {
        return this.year;
    }

    public LeaveApplication year(Long year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Instant getFormDate() {
        return this.formDate;
    }

    public LeaveApplication formDate(Instant formDate) {
        this.setFormDate(formDate);
        return this;
    }

    public void setFormDate(Instant formDate) {
        this.formDate = formDate;
    }

    public Instant getToDate() {
        return this.toDate;
    }

    public LeaveApplication toDate(Instant toDate) {
        this.setToDate(toDate);
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public LeaveApplication status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LeaveStatus getLeaveStatus() {
        return this.leaveStatus;
    }

    public LeaveApplication leaveStatus(LeaveStatus leaveStatus) {
        this.setLeaveStatus(leaveStatus);
        return this;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LeaveApplication lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LeaveApplication lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LeaveApplication createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LeaveApplication createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public LeaveApplication deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveApplication employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public LeavePolicy getLeavePolicy() {
        return this.leavePolicy;
    }

    public void setLeavePolicy(LeavePolicy leavePolicy) {
        this.leavePolicy = leavePolicy;
    }

    public LeaveApplication leavePolicy(LeavePolicy leavePolicy) {
        this.setLeavePolicy(leavePolicy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApplication)) {
            return false;
        }
        return id != null && id.equals(((LeaveApplication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplication{" +
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
            "}";
    }
}
