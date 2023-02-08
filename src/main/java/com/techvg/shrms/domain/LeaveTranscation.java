package com.techvg.shrms.domain;

import com.techvg.shrms.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LeaveTranscation.
 */
@Entity
@Table(name = "leave_transcation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTranscation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "month_date")
    private Instant monthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "year")
    private Long year;

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

    public LeaveTranscation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeaveType() {
        return this.leaveType;
    }

    public LeaveTranscation leaveType(String leaveType) {
        this.setLeaveType(leaveType);
        return this;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getEmpId() {
        return this.empId;
    }

    public LeaveTranscation empId(String empId) {
        this.setEmpId(empId);
        return this;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Instant getMonthDate() {
        return this.monthDate;
    }

    public LeaveTranscation monthDate(Instant monthDate) {
        this.setMonthDate(monthDate);
        return this;
    }

    public void setMonthDate(Instant monthDate) {
        this.monthDate = monthDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public LeaveTranscation status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getYear() {
        return this.year;
    }

    public LeaveTranscation year(Long year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LeaveTranscation lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LeaveTranscation lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LeaveTranscation createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LeaveTranscation createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public LeaveTranscation deleted(Boolean deleted) {
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
        if (!(o instanceof LeaveTranscation)) {
            return false;
        }
        return id != null && id.equals(((LeaveTranscation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTranscation{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", empId='" + getEmpId() + "'" +
            ", monthDate='" + getMonthDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", year=" + getYear() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
