package com.techvg.shrms.service.dto;

import com.techvg.shrms.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.techvg.shrms.domain.Projects} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectsDTO implements Serializable {

    private Long id;

    private String projectName;

    private String description;

    private String clientName;

    private Double cost;

    private String costType;

    private String priority;

    private Instant startDate;

    private Instant endDate;

    private Instant deadLine;

    private ProjectStatus status;

    private String projectLead;

    private Double progressPercent;

    private Long openTasksNo;

    private Long completeTasksNo;

    @Lob
    private byte[] projectLogo;

    private String projectLogoContentType;
    private String projectFile;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    private Long companyId;

    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Instant deadLine) {
        this.deadLine = deadLine;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getProjectLead() {
        return projectLead;
    }

    public void setProjectLead(String projectLead) {
        this.projectLead = projectLead;
    }

    public Double getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(Double progressPercent) {
        this.progressPercent = progressPercent;
    }

    public Long getOpenTasksNo() {
        return openTasksNo;
    }

    public void setOpenTasksNo(Long openTasksNo) {
        this.openTasksNo = openTasksNo;
    }

    public Long getCompleteTasksNo() {
        return completeTasksNo;
    }

    public void setCompleteTasksNo(Long completeTasksNo) {
        this.completeTasksNo = completeTasksNo;
    }

    public byte[] getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(byte[] projectLogo) {
        this.projectLogo = projectLogo;
    }

    public String getProjectLogoContentType() {
        return projectLogoContentType;
    }

    public void setProjectLogoContentType(String projectLogoContentType) {
        this.projectLogoContentType = projectLogoContentType;
    }

    public String getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectsDTO)) {
            return false;
        }

        ProjectsDTO projectsDTO = (ProjectsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsDTO{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", description='" + getDescription() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", cost=" + getCost() +
            ", costType='" + getCostType() + "'" +
            ", priority='" + getPriority() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", deadLine='" + getDeadLine() + "'" +
            ", status='" + getStatus() + "'" +
            ", projectLead='" + getProjectLead() + "'" +
            ", progressPercent=" + getProgressPercent() +
            ", openTasksNo=" + getOpenTasksNo() +
            ", completeTasksNo=" + getCompleteTasksNo() +
            ", projectLogo='" + getProjectLogo() + "'" +
            ", projectFile='" + getProjectFile() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", companyId=" + getCompanyId() +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
