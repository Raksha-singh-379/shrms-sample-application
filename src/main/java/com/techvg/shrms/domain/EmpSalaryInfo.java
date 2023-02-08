package com.techvg.shrms.domain;

import com.techvg.shrms.domain.enumeration.SalaryBasis;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmpSalaryInfo.
 */
@Entity
@Table(name = "emp_salary_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpSalaryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_basis")
    private SalaryBasis salaryBasis;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "is_pf_contribution")
    private Boolean isPfContribution;

    @Column(name = "pf_number")
    private String pfNumber;

    @Column(name = "pf_rate")
    private Double pfRate;

    @Column(name = "additional_pf_rate")
    private String additionalPfRate;

    @Column(name = "total_pf_rate")
    private Double totalPfRate;

    @Column(name = "is_esi_contribution")
    private Boolean isEsiContribution;

    @Column(name = "esi_number")
    private String esiNumber;

    @Column(name = "esi_rate")
    private Double esiRate;

    @Column(name = "additional_esi_rate")
    private String additionalEsiRate;

    @Column(name = "total_esi_rate")
    private Double totalEsiRate;

    @Column(name = "employe_id")
    private Long employeId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmpSalaryInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalaryBasis getSalaryBasis() {
        return this.salaryBasis;
    }

    public EmpSalaryInfo salaryBasis(SalaryBasis salaryBasis) {
        this.setSalaryBasis(salaryBasis);
        return this;
    }

    public void setSalaryBasis(SalaryBasis salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public Double getAmount() {
        return this.amount;
    }

    public EmpSalaryInfo amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public EmpSalaryInfo paymentType(String paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsPfContribution() {
        return this.isPfContribution;
    }

    public EmpSalaryInfo isPfContribution(Boolean isPfContribution) {
        this.setIsPfContribution(isPfContribution);
        return this;
    }

    public void setIsPfContribution(Boolean isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public String getPfNumber() {
        return this.pfNumber;
    }

    public EmpSalaryInfo pfNumber(String pfNumber) {
        this.setPfNumber(pfNumber);
        return this;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public Double getPfRate() {
        return this.pfRate;
    }

    public EmpSalaryInfo pfRate(Double pfRate) {
        this.setPfRate(pfRate);
        return this;
    }

    public void setPfRate(Double pfRate) {
        this.pfRate = pfRate;
    }

    public String getAdditionalPfRate() {
        return this.additionalPfRate;
    }

    public EmpSalaryInfo additionalPfRate(String additionalPfRate) {
        this.setAdditionalPfRate(additionalPfRate);
        return this;
    }

    public void setAdditionalPfRate(String additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public Double getTotalPfRate() {
        return this.totalPfRate;
    }

    public EmpSalaryInfo totalPfRate(Double totalPfRate) {
        this.setTotalPfRate(totalPfRate);
        return this;
    }

    public void setTotalPfRate(Double totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public Boolean getIsEsiContribution() {
        return this.isEsiContribution;
    }

    public EmpSalaryInfo isEsiContribution(Boolean isEsiContribution) {
        this.setIsEsiContribution(isEsiContribution);
        return this;
    }

    public void setIsEsiContribution(Boolean isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public String getEsiNumber() {
        return this.esiNumber;
    }

    public EmpSalaryInfo esiNumber(String esiNumber) {
        this.setEsiNumber(esiNumber);
        return this;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Double getEsiRate() {
        return this.esiRate;
    }

    public EmpSalaryInfo esiRate(Double esiRate) {
        this.setEsiRate(esiRate);
        return this;
    }

    public void setEsiRate(Double esiRate) {
        this.esiRate = esiRate;
    }

    public String getAdditionalEsiRate() {
        return this.additionalEsiRate;
    }

    public EmpSalaryInfo additionalEsiRate(String additionalEsiRate) {
        this.setAdditionalEsiRate(additionalEsiRate);
        return this;
    }

    public void setAdditionalEsiRate(String additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public Double getTotalEsiRate() {
        return this.totalEsiRate;
    }

    public EmpSalaryInfo totalEsiRate(Double totalEsiRate) {
        this.setTotalEsiRate(totalEsiRate);
        return this;
    }

    public void setTotalEsiRate(Double totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public EmpSalaryInfo employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public EmpSalaryInfo lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public EmpSalaryInfo lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public EmpSalaryInfo createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public EmpSalaryInfo createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public EmpSalaryInfo deleted(Boolean deleted) {
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

    public EmpSalaryInfo employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpSalaryInfo)) {
            return false;
        }
        return id != null && id.equals(((EmpSalaryInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpSalaryInfo{" +
            "id=" + getId() +
            ", salaryBasis='" + getSalaryBasis() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", isPfContribution='" + getIsPfContribution() + "'" +
            ", pfNumber='" + getPfNumber() + "'" +
            ", pfRate=" + getPfRate() +
            ", additionalPfRate='" + getAdditionalPfRate() + "'" +
            ", totalPfRate=" + getTotalPfRate() +
            ", isEsiContribution='" + getIsEsiContribution() + "'" +
            ", esiNumber='" + getEsiNumber() + "'" +
            ", esiRate=" + getEsiRate() +
            ", additionalEsiRate='" + getAdditionalEsiRate() + "'" +
            ", totalEsiRate=" + getTotalEsiRate() +
            ", employeId=" + getEmployeId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
