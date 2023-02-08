package com.techvg.shrms.domain;

import com.techvg.shrms.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "description")
    private String description;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "cost_type")
    private String costType;

    @Column(name = "priority")
    private String priority;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "dead_line")
    private Instant deadLine;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "project_lead")
    private String projectLead;

    @Column(name = "progress_percent")
    private Double progressPercent;

    @Column(name = "open_tasks_no")
    private Long openTasksNo;

    @Column(name = "complete_tasks_no")
    private Long completeTasksNo;

    @Lob
    @Column(name = "project_logo")
    private byte[] projectLogo;

    @Column(name = "project_logo_content_type")
    private String projectLogoContentType;

    @Column(name = "project_file")
    private String projectFile;

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

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "employee_id")
    private Long employeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public Projects projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return this.description;
    }

    public Projects description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Projects clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getCost() {
        return this.cost;
    }

    public Projects cost(Double cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCostType() {
        return this.costType;
    }

    public Projects costType(String costType) {
        this.setCostType(costType);
        return this;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getPriority() {
        return this.priority;
    }

    public Projects priority(String priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Projects startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Projects endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getDeadLine() {
        return this.deadLine;
    }

    public Projects deadLine(Instant deadLine) {
        this.setDeadLine(deadLine);
        return this;
    }

    public void setDeadLine(Instant deadLine) {
        this.deadLine = deadLine;
    }

    public ProjectStatus getStatus() {
        return this.status;
    }

    public Projects status(ProjectStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getProjectLead() {
        return this.projectLead;
    }

    public Projects projectLead(String projectLead) {
        this.setProjectLead(projectLead);
        return this;
    }

    public void setProjectLead(String projectLead) {
        this.projectLead = projectLead;
    }

    public Double getProgressPercent() {
        return this.progressPercent;
    }

    public Projects progressPercent(Double progressPercent) {
        this.setProgressPercent(progressPercent);
        return this;
    }

    public void setProgressPercent(Double progressPercent) {
        this.progressPercent = progressPercent;
    }

    public Long getOpenTasksNo() {
        return this.openTasksNo;
    }

    public Projects openTasksNo(Long openTasksNo) {
        this.setOpenTasksNo(openTasksNo);
        return this;
    }

    public void setOpenTasksNo(Long openTasksNo) {
        this.openTasksNo = openTasksNo;
    }

    public Long getCompleteTasksNo() {
        return this.completeTasksNo;
    }

    public Projects completeTasksNo(Long completeTasksNo) {
        this.setCompleteTasksNo(completeTasksNo);
        return this;
    }

    public void setCompleteTasksNo(Long completeTasksNo) {
        this.completeTasksNo = completeTasksNo;
    }

    public byte[] getProjectLogo() {
        return this.projectLogo;
    }

    public Projects projectLogo(byte[] projectLogo) {
        this.setProjectLogo(projectLogo);
        return this;
    }

    public void setProjectLogo(byte[] projectLogo) {
        this.projectLogo = projectLogo;
    }

    public String getProjectLogoContentType() {
        return this.projectLogoContentType;
    }

    public Projects projectLogoContentType(String projectLogoContentType) {
        this.projectLogoContentType = projectLogoContentType;
        return this;
    }

    public void setProjectLogoContentType(String projectLogoContentType) {
        this.projectLogoContentType = projectLogoContentType;
    }

    public String getProjectFile() {
        return this.projectFile;
    }

    public Projects projectFile(String projectFile) {
        this.setProjectFile(projectFile);
        return this;
    }

    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Projects lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Projects lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Projects createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Projects createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Projects deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Projects companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Projects employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projects{" +
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
            ", projectLogoContentType='" + getProjectLogoContentType() + "'" +
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
