package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.BloodGroup;
import com.techvg.shrms.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.EmployeeDetails} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.EmployeeDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {}

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }
    }

    /**
     * Class for filtering BloodGroup
     */
    public static class BloodGroupFilter extends Filter<BloodGroup> {

        public BloodGroupFilter() {}

        public BloodGroupFilter(BloodGroupFilter filter) {
            super(filter);
        }

        @Override
        public BloodGroupFilter copy() {
            return new BloodGroupFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter passportNo;

    private InstantFilter passportExpDate;

    private StringFilter telephoneNo;

    private StringFilter nationality;

    private MaritalStatusFilter maritalStatus;

    private StringFilter religion;

    private BooleanFilter isSpousEmployed;

    private LongFilter noOfChildren;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private LongFilter age;

    private StringFilter fatherName;

    private StringFilter motherName;

    private StringFilter aadharNo;

    private BloodGroupFilter bloodGroup;

    private LocalDateFilter dob;

    private StringFilter expertise;

    private StringFilter hobbies;

    private StringFilter areaInterest;

    private StringFilter languageKnown;

    private StringFilter description;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeDetailsCriteria() {}

    public EmployeeDetailsCriteria(EmployeeDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.passportNo = other.passportNo == null ? null : other.passportNo.copy();
        this.passportExpDate = other.passportExpDate == null ? null : other.passportExpDate.copy();
        this.telephoneNo = other.telephoneNo == null ? null : other.telephoneNo.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.isSpousEmployed = other.isSpousEmployed == null ? null : other.isSpousEmployed.copy();
        this.noOfChildren = other.noOfChildren == null ? null : other.noOfChildren.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.aadharNo = other.aadharNo == null ? null : other.aadharNo.copy();
        this.bloodGroup = other.bloodGroup == null ? null : other.bloodGroup.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.expertise = other.expertise == null ? null : other.expertise.copy();
        this.hobbies = other.hobbies == null ? null : other.hobbies.copy();
        this.areaInterest = other.areaInterest == null ? null : other.areaInterest.copy();
        this.languageKnown = other.languageKnown == null ? null : other.languageKnown.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeDetailsCriteria copy() {
        return new EmployeeDetailsCriteria(this);
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

    public StringFilter getPassportNo() {
        return passportNo;
    }

    public StringFilter passportNo() {
        if (passportNo == null) {
            passportNo = new StringFilter();
        }
        return passportNo;
    }

    public void setPassportNo(StringFilter passportNo) {
        this.passportNo = passportNo;
    }

    public InstantFilter getPassportExpDate() {
        return passportExpDate;
    }

    public InstantFilter passportExpDate() {
        if (passportExpDate == null) {
            passportExpDate = new InstantFilter();
        }
        return passportExpDate;
    }

    public void setPassportExpDate(InstantFilter passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public StringFilter getTelephoneNo() {
        return telephoneNo;
    }

    public StringFilter telephoneNo() {
        if (telephoneNo == null) {
            telephoneNo = new StringFilter();
        }
        return telephoneNo;
    }

    public void setTelephoneNo(StringFilter telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public MaritalStatusFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new MaritalStatusFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getReligion() {
        return religion;
    }

    public StringFilter religion() {
        if (religion == null) {
            religion = new StringFilter();
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public BooleanFilter getIsSpousEmployed() {
        return isSpousEmployed;
    }

    public BooleanFilter isSpousEmployed() {
        if (isSpousEmployed == null) {
            isSpousEmployed = new BooleanFilter();
        }
        return isSpousEmployed;
    }

    public void setIsSpousEmployed(BooleanFilter isSpousEmployed) {
        this.isSpousEmployed = isSpousEmployed;
    }

    public LongFilter getNoOfChildren() {
        return noOfChildren;
    }

    public LongFilter noOfChildren() {
        if (noOfChildren == null) {
            noOfChildren = new LongFilter();
        }
        return noOfChildren;
    }

    public void setNoOfChildren(LongFilter noOfChildren) {
        this.noOfChildren = noOfChildren;
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

    public LongFilter getAge() {
        return age;
    }

    public LongFilter age() {
        if (age == null) {
            age = new LongFilter();
        }
        return age;
    }

    public void setAge(LongFilter age) {
        this.age = age;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public StringFilter fatherName() {
        if (fatherName == null) {
            fatherName = new StringFilter();
        }
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public StringFilter motherName() {
        if (motherName == null) {
            motherName = new StringFilter();
        }
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public StringFilter getAadharNo() {
        return aadharNo;
    }

    public StringFilter aadharNo() {
        if (aadharNo == null) {
            aadharNo = new StringFilter();
        }
        return aadharNo;
    }

    public void setAadharNo(StringFilter aadharNo) {
        this.aadharNo = aadharNo;
    }

    public BloodGroupFilter getBloodGroup() {
        return bloodGroup;
    }

    public BloodGroupFilter bloodGroup() {
        if (bloodGroup == null) {
            bloodGroup = new BloodGroupFilter();
        }
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDateFilter getDob() {
        return dob;
    }

    public LocalDateFilter dob() {
        if (dob == null) {
            dob = new LocalDateFilter();
        }
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
    }

    public StringFilter getExpertise() {
        return expertise;
    }

    public StringFilter expertise() {
        if (expertise == null) {
            expertise = new StringFilter();
        }
        return expertise;
    }

    public void setExpertise(StringFilter expertise) {
        this.expertise = expertise;
    }

    public StringFilter getHobbies() {
        return hobbies;
    }

    public StringFilter hobbies() {
        if (hobbies == null) {
            hobbies = new StringFilter();
        }
        return hobbies;
    }

    public void setHobbies(StringFilter hobbies) {
        this.hobbies = hobbies;
    }

    public StringFilter getAreaInterest() {
        return areaInterest;
    }

    public StringFilter areaInterest() {
        if (areaInterest == null) {
            areaInterest = new StringFilter();
        }
        return areaInterest;
    }

    public void setAreaInterest(StringFilter areaInterest) {
        this.areaInterest = areaInterest;
    }

    public StringFilter getLanguageKnown() {
        return languageKnown;
    }

    public StringFilter languageKnown() {
        if (languageKnown == null) {
            languageKnown = new StringFilter();
        }
        return languageKnown;
    }

    public void setLanguageKnown(StringFilter languageKnown) {
        this.languageKnown = languageKnown;
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
        final EmployeeDetailsCriteria that = (EmployeeDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(passportNo, that.passportNo) &&
            Objects.equals(passportExpDate, that.passportExpDate) &&
            Objects.equals(telephoneNo, that.telephoneNo) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(isSpousEmployed, that.isSpousEmployed) &&
            Objects.equals(noOfChildren, that.noOfChildren) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(age, that.age) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(aadharNo, that.aadharNo) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(expertise, that.expertise) &&
            Objects.equals(hobbies, that.hobbies) &&
            Objects.equals(areaInterest, that.areaInterest) &&
            Objects.equals(languageKnown, that.languageKnown) &&
            Objects.equals(description, that.description) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            passportNo,
            passportExpDate,
            telephoneNo,
            nationality,
            maritalStatus,
            religion,
            isSpousEmployed,
            noOfChildren,
            createdBy,
            createdOn,
            lastModified,
            lastModifiedBy,
            deleted,
            employeeId,
            age,
            fatherName,
            motherName,
            aadharNo,
            bloodGroup,
            dob,
            expertise,
            hobbies,
            areaInterest,
            languageKnown,
            description,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (passportNo != null ? "passportNo=" + passportNo + ", " : "") +
            (passportExpDate != null ? "passportExpDate=" + passportExpDate + ", " : "") +
            (telephoneNo != null ? "telephoneNo=" + telephoneNo + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (religion != null ? "religion=" + religion + ", " : "") +
            (isSpousEmployed != null ? "isSpousEmployed=" + isSpousEmployed + ", " : "") +
            (noOfChildren != null ? "noOfChildren=" + noOfChildren + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
            (motherName != null ? "motherName=" + motherName + ", " : "") +
            (aadharNo != null ? "aadharNo=" + aadharNo + ", " : "") +
            (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (expertise != null ? "expertise=" + expertise + ", " : "") +
            (hobbies != null ? "hobbies=" + hobbies + ", " : "") +
            (areaInterest != null ? "areaInterest=" + areaInterest + ", " : "") +
            (languageKnown != null ? "languageKnown=" + languageKnown + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
