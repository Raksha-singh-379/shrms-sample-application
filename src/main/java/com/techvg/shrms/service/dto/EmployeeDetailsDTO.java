package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.BloodGroup;
import com.techvg.shrms.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.EmployeeDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetailsDTO implements Serializable {

    private Long id;

    private String passportNo;

    private Instant passportExpDate;

    private String telephoneNo;

    private String nationality;

    private MaritalStatus maritalStatus;

    private String religion;

    private Boolean isSpousEmployed;

    private Long noOfChildren;

    private String createdBy;

    private Instant createdOn;

    private Instant lastModified;

    private String lastModifiedBy;

    private Boolean deleted;

    private Long employeeId;

    private Long age;

    private String fatherName;

    private String motherName;

    private String aadharNo;

    private BloodGroup bloodGroup;

    private LocalDate dob;

    private String expertise;

    private String hobbies;

    private String areaInterest;

    private String languageKnown;

    private String description;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Instant getPassportExpDate() {
        return passportExpDate;
    }

    public void setPassportExpDate(Instant passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Boolean getIsSpousEmployed() {
        return isSpousEmployed;
    }

    public void setIsSpousEmployed(Boolean isSpousEmployed) {
        this.isSpousEmployed = isSpousEmployed;
    }

    public Long getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(Long noOfChildren) {
        this.noOfChildren = noOfChildren;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAreaInterest() {
        return areaInterest;
    }

    public void setAreaInterest(String areaInterest) {
        this.areaInterest = areaInterest;
    }

    public String getLanguageKnown() {
        return languageKnown;
    }

    public void setLanguageKnown(String languageKnown) {
        this.languageKnown = languageKnown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof EmployeeDetailsDTO)) {
            return false;
        }

        EmployeeDetailsDTO employeeDetailsDTO = (EmployeeDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetailsDTO{" +
            "id=" + getId() +
            ", passportNo='" + getPassportNo() + "'" +
            ", passportExpDate='" + getPassportExpDate() + "'" +
            ", telephoneNo='" + getTelephoneNo() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", religion='" + getReligion() + "'" +
            ", isSpousEmployed='" + getIsSpousEmployed() + "'" +
            ", noOfChildren=" + getNoOfChildren() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", age=" + getAge() +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", aadharNo='" + getAadharNo() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", dob='" + getDob() + "'" +
            ", expertise='" + getExpertise() + "'" +
            ", hobbies='" + getHobbies() + "'" +
            ", areaInterest='" + getAreaInterest() + "'" +
            ", languageKnown='" + getLanguageKnown() + "'" +
            ", description='" + getDescription() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
