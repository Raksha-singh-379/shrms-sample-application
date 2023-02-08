package com.techvg.shrms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.shrms.domain.enumeration.BranchType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Branch.
 */
@Entity
@Table(name = "branch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "branch_name", nullable = false, unique = true)
    private String branchName;

    @Column(name = "description")
    private String description;

    @Column(name = "branchcode", unique = true)
    private String branchcode;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "micr_code")
    private String micrCode;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "iban_code")
    private String ibanCode;

    @Column(name = "is_head_office")
    private Boolean isHeadOffice;

    @Column(name = "routing_transit_no")
    private String routingTransitNo;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "fax")
    private String fax;

    @Column(name = "is_activate")
    private Boolean isActivate;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_type")
    private BranchType branchType;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "deleted")
    private String deleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "employee", "state", "district", "taluka", "city" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Region branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Branch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public Branch branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDescription() {
        return this.description;
    }

    public Branch description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchcode() {
        return this.branchcode;
    }

    public Branch branchcode(String branchcode) {
        this.setBranchcode(branchcode);
        return this;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public Branch ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return this.micrCode;
    }

    public Branch micrCode(String micrCode) {
        this.setMicrCode(micrCode);
        return this;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getSwiftCode() {
        return this.swiftCode;
    }

    public Branch swiftCode(String swiftCode) {
        this.setSwiftCode(swiftCode);
        return this;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getIbanCode() {
        return this.ibanCode;
    }

    public Branch ibanCode(String ibanCode) {
        this.setIbanCode(ibanCode);
        return this;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

    public Boolean getIsHeadOffice() {
        return this.isHeadOffice;
    }

    public Branch isHeadOffice(Boolean isHeadOffice) {
        this.setIsHeadOffice(isHeadOffice);
        return this;
    }

    public void setIsHeadOffice(Boolean isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }

    public String getRoutingTransitNo() {
        return this.routingTransitNo;
    }

    public Branch routingTransitNo(String routingTransitNo) {
        this.setRoutingTransitNo(routingTransitNo);
        return this;
    }

    public void setRoutingTransitNo(String routingTransitNo) {
        this.routingTransitNo = routingTransitNo;
    }

    public String getPhone() {
        return this.phone;
    }

    public Branch phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Branch email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public Branch webSite(String webSite) {
        this.setWebSite(webSite);
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getFax() {
        return this.fax;
    }

    public Branch fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getIsActivate() {
        return this.isActivate;
    }

    public Branch isActivate(Boolean isActivate) {
        this.setIsActivate(isActivate);
        return this;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public BranchType getBranchType() {
        return this.branchType;
    }

    public Branch branchType(BranchType branchType) {
        this.setBranchType(branchType);
        return this;
    }

    public void setBranchType(BranchType branchType) {
        this.branchType = branchType;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Branch createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Branch createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDeleted() {
        return this.deleted;
    }

    public Branch deleted(String deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Branch lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Branch lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Branch address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Branch company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Region getBranch() {
        return this.branch;
    }

    public void setBranch(Region region) {
        this.branch = region;
    }

    public Branch branch(Region region) {
        this.setBranch(region);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }
        return id != null && id.equals(((Branch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Branch{" +
            "id=" + getId() +
            ", branchName='" + getBranchName() + "'" +
            ", description='" + getDescription() + "'" +
            ", branchcode='" + getBranchcode() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", micrCode='" + getMicrCode() + "'" +
            ", swiftCode='" + getSwiftCode() + "'" +
            ", ibanCode='" + getIbanCode() + "'" +
            ", isHeadOffice='" + getIsHeadOffice() + "'" +
            ", routingTransitNo='" + getRoutingTransitNo() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", fax='" + getFax() + "'" +
            ", isActivate='" + getIsActivate() + "'" +
            ", branchType='" + getBranchType() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
