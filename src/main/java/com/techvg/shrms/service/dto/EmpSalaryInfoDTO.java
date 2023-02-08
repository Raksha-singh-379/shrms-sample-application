package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.SalaryBasis;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.shrms.domain.EmpSalaryInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpSalaryInfoDTO implements Serializable {

    private Long id;

    private SalaryBasis salaryBasis;

    private Double amount;

    private String paymentType;

    private Boolean isPfContribution;

    private String pfNumber;

    private Double pfRate;

    private String additionalPfRate;

    private Double totalPfRate;

    private Boolean isEsiContribution;

    private String esiNumber;

    private Double esiRate;

    private String additionalEsiRate;

    private Double totalEsiRate;

    private Long employeId;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalaryBasis getSalaryBasis() {
        return salaryBasis;
    }

    public void setSalaryBasis(SalaryBasis salaryBasis) {
        this.salaryBasis = salaryBasis;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsPfContribution() {
        return isPfContribution;
    }

    public void setIsPfContribution(Boolean isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public Double getPfRate() {
        return pfRate;
    }

    public void setPfRate(Double pfRate) {
        this.pfRate = pfRate;
    }

    public String getAdditionalPfRate() {
        return additionalPfRate;
    }

    public void setAdditionalPfRate(String additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public Double getTotalPfRate() {
        return totalPfRate;
    }

    public void setTotalPfRate(Double totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public Boolean getIsEsiContribution() {
        return isEsiContribution;
    }

    public void setIsEsiContribution(Boolean isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public String getEsiNumber() {
        return esiNumber;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Double getEsiRate() {
        return esiRate;
    }

    public void setEsiRate(Double esiRate) {
        this.esiRate = esiRate;
    }

    public String getAdditionalEsiRate() {
        return additionalEsiRate;
    }

    public void setAdditionalEsiRate(String additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public Double getTotalEsiRate() {
        return totalEsiRate;
    }

    public void setTotalEsiRate(Double totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpSalaryInfoDTO)) {
            return false;
        }

        EmpSalaryInfoDTO empSalaryInfoDTO = (EmpSalaryInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empSalaryInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpSalaryInfoDTO{" +
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
            ", employee=" + getEmployee() +
            "}";
    }
}
