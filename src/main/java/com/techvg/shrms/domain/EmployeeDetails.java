package com.techvg.shrms.domain;

import com.techvg.shrms.domain.enumeration.BloodGroup;
import com.techvg.shrms.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployeeDetails.
 */
@Entity
@Table(name = "employee_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "passport_exp_date")
    private Instant passportExpDate;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "religion")
    private String religion;

    @Column(name = "is_spous_employed")
    private Boolean isSpousEmployed;

    @Column(name = "no_of_children")
    private Long noOfChildren;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "age")
    private Long age;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "aadhar_no")
    private String aadharNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "expertise")
    private String expertise;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "area_interest")
    private String areaInterest;

    @Column(name = "language_known")
    private String languageKnown;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public EmployeeDetails passportNo(String passportNo) {
        this.setPassportNo(passportNo);
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Instant getPassportExpDate() {
        return this.passportExpDate;
    }

    public EmployeeDetails passportExpDate(Instant passportExpDate) {
        this.setPassportExpDate(passportExpDate);
        return this;
    }

    public void setPassportExpDate(Instant passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public String getTelephoneNo() {
        return this.telephoneNo;
    }

    public EmployeeDetails telephoneNo(String telephoneNo) {
        this.setTelephoneNo(telephoneNo);
        return this;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getNationality() {
        return this.nationality;
    }

    public EmployeeDetails nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public EmployeeDetails maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getReligion() {
        return this.religion;
    }

    public EmployeeDetails religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Boolean getIsSpousEmployed() {
        return this.isSpousEmployed;
    }

    public EmployeeDetails isSpousEmployed(Boolean isSpousEmployed) {
        this.setIsSpousEmployed(isSpousEmployed);
        return this;
    }

    public void setIsSpousEmployed(Boolean isSpousEmployed) {
        this.isSpousEmployed = isSpousEmployed;
    }

    public Long getNoOfChildren() {
        return this.noOfChildren;
    }

    public EmployeeDetails noOfChildren(Long noOfChildren) {
        this.setNoOfChildren(noOfChildren);
        return this;
    }

    public void setNoOfChildren(Long noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public EmployeeDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public EmployeeDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public EmployeeDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public EmployeeDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public EmployeeDetails deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeDetails employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAge() {
        return this.age;
    }

    public EmployeeDetails age(Long age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public EmployeeDetails fatherName(String fatherName) {
        this.setFatherName(fatherName);
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return this.motherName;
    }

    public EmployeeDetails motherName(String motherName) {
        this.setMotherName(motherName);
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAadharNo() {
        return this.aadharNo;
    }

    public EmployeeDetails aadharNo(String aadharNo) {
        this.setAadharNo(aadharNo);
        return this;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public BloodGroup getBloodGroup() {
        return this.bloodGroup;
    }

    public EmployeeDetails bloodGroup(BloodGroup bloodGroup) {
        this.setBloodGroup(bloodGroup);
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public EmployeeDetails dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getExpertise() {
        return this.expertise;
    }

    public EmployeeDetails expertise(String expertise) {
        this.setExpertise(expertise);
        return this;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public EmployeeDetails hobbies(String hobbies) {
        this.setHobbies(hobbies);
        return this;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAreaInterest() {
        return this.areaInterest;
    }

    public EmployeeDetails areaInterest(String areaInterest) {
        this.setAreaInterest(areaInterest);
        return this;
    }

    public void setAreaInterest(String areaInterest) {
        this.areaInterest = areaInterest;
    }

    public String getLanguageKnown() {
        return this.languageKnown;
    }

    public EmployeeDetails languageKnown(String languageKnown) {
        this.setLanguageKnown(languageKnown);
        return this;
    }

    public void setLanguageKnown(String languageKnown) {
        this.languageKnown = languageKnown;
    }

    public String getDescription() {
        return this.description;
    }

    public EmployeeDetails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeDetails employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDetails)) {
            return false;
        }
        return id != null && id.equals(((EmployeeDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetails{" +
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
            "}";
    }
}
