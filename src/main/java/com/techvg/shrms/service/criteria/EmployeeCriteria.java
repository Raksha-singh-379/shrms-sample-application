package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.Employee} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter username;

    private StringFilter password;

    private StringFilter email;

    private StringFilter phone;

    private StringFilter mobile;

    private StringFilter department;

    private StringFilter designation;

    private GenderFilter gender;

    private StringFilter employeeId;

    private InstantFilter joindate;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter deleted;

    private LongFilter reportedTo;

    private LongFilter companyId;

    private LongFilter branchId;

    private LongFilter regionId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.joindate = other.joindate == null ? null : other.joindate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.reportedTo = other.reportedTo == null ? null : other.reportedTo.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public StringFilter mobile() {
        if (mobile == null) {
            mobile = new StringFilter();
        }
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getDepartment() {
        return department;
    }

    public StringFilter department() {
        if (department == null) {
            department = new StringFilter();
        }
        return department;
    }

    public void setDepartment(StringFilter department) {
        this.department = department;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public StringFilter employeeId() {
        if (employeeId == null) {
            employeeId = new StringFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
    }

    public InstantFilter getJoindate() {
        return joindate;
    }

    public InstantFilter joindate() {
        if (joindate == null) {
            joindate = new InstantFilter();
        }
        return joindate;
    }

    public void setJoindate(InstantFilter joindate) {
        this.joindate = joindate;
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

    public LongFilter getReportedTo() {
        return reportedTo;
    }

    public LongFilter reportedTo() {
        if (reportedTo == null) {
            reportedTo = new LongFilter();
        }
        return reportedTo;
    }

    public void setReportedTo(LongFilter reportedTo) {
        this.reportedTo = reportedTo;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public LongFilter regionId() {
        if (regionId == null) {
            regionId = new LongFilter();
        }
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(department, that.department) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(joindate, that.joindate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(reportedTo, that.reportedTo) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            username,
            password,
            email,
            phone,
            mobile,
            department,
            designation,
            gender,
            employeeId,
            joindate,
            createdBy,
            createdOn,
            lastModified,
            lastModifiedBy,
            deleted,
            reportedTo,
            companyId,
            branchId,
            regionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (joindate != null ? "joindate=" + joindate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (reportedTo != null ? "reportedTo=" + reportedTo + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
