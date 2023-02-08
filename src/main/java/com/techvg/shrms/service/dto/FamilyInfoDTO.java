package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.Relationship;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.FamilyInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FamilyInfoDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate dob;

    private Relationship relation;

    private String phoneNo;

    private String address;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    private Long employeeId;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Relationship getRelation() {
        return relation;
    }

    public void setRelation(Relationship relation) {
        this.relation = relation;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyInfoDTO)) {
            return false;
        }

        FamilyInfoDTO familyInfoDTO = (FamilyInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, familyInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyInfoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dob='" + getDob() + "'" +
            ", relation='" + getRelation() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employee=" + getEmployee() +
            "}";
    }
}
