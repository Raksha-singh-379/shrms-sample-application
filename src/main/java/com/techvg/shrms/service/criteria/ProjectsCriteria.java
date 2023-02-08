package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.Projects} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.ProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProjectStatus
     */
    public static class ProjectStatusFilter extends Filter<ProjectStatus> {

        public ProjectStatusFilter() {}

        public ProjectStatusFilter(ProjectStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProjectStatusFilter copy() {
            return new ProjectStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter projectName;

    private StringFilter description;

    private StringFilter clientName;

    private DoubleFilter cost;

    private StringFilter costType;

    private StringFilter priority;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private InstantFilter deadLine;

    private ProjectStatusFilter status;

    private StringFilter projectLead;

    private DoubleFilter progressPercent;

    private LongFilter openTasksNo;

    private LongFilter completeTasksNo;

    private StringFilter projectFile;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter companyId;

    private LongFilter employeeId;

    private Boolean distinct;

    public ProjectsCriteria() {}

    public ProjectsCriteria(ProjectsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectName = other.projectName == null ? null : other.projectName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.clientName = other.clientName == null ? null : other.clientName.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.costType = other.costType == null ? null : other.costType.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.deadLine = other.deadLine == null ? null : other.deadLine.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.projectLead = other.projectLead == null ? null : other.projectLead.copy();
        this.progressPercent = other.progressPercent == null ? null : other.progressPercent.copy();
        this.openTasksNo = other.openTasksNo == null ? null : other.openTasksNo.copy();
        this.completeTasksNo = other.completeTasksNo == null ? null : other.completeTasksNo.copy();
        this.projectFile = other.projectFile == null ? null : other.projectFile.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectsCriteria copy() {
        return new ProjectsCriteria(this);
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

    public StringFilter getProjectName() {
        return projectName;
    }

    public StringFilter projectName() {
        if (projectName == null) {
            projectName = new StringFilter();
        }
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
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

    public StringFilter getClientName() {
        return clientName;
    }

    public StringFilter clientName() {
        if (clientName == null) {
            clientName = new StringFilter();
        }
        return clientName;
    }

    public void setClientName(StringFilter clientName) {
        this.clientName = clientName;
    }

    public DoubleFilter getCost() {
        return cost;
    }

    public DoubleFilter cost() {
        if (cost == null) {
            cost = new DoubleFilter();
        }
        return cost;
    }

    public void setCost(DoubleFilter cost) {
        this.cost = cost;
    }

    public StringFilter getCostType() {
        return costType;
    }

    public StringFilter costType() {
        if (costType == null) {
            costType = new StringFilter();
        }
        return costType;
    }

    public void setCostType(StringFilter costType) {
        this.costType = costType;
    }

    public StringFilter getPriority() {
        return priority;
    }

    public StringFilter priority() {
        if (priority == null) {
            priority = new StringFilter();
        }
        return priority;
    }

    public void setPriority(StringFilter priority) {
        this.priority = priority;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public InstantFilter getDeadLine() {
        return deadLine;
    }

    public InstantFilter deadLine() {
        if (deadLine == null) {
            deadLine = new InstantFilter();
        }
        return deadLine;
    }

    public void setDeadLine(InstantFilter deadLine) {
        this.deadLine = deadLine;
    }

    public ProjectStatusFilter getStatus() {
        return status;
    }

    public ProjectStatusFilter status() {
        if (status == null) {
            status = new ProjectStatusFilter();
        }
        return status;
    }

    public void setStatus(ProjectStatusFilter status) {
        this.status = status;
    }

    public StringFilter getProjectLead() {
        return projectLead;
    }

    public StringFilter projectLead() {
        if (projectLead == null) {
            projectLead = new StringFilter();
        }
        return projectLead;
    }

    public void setProjectLead(StringFilter projectLead) {
        this.projectLead = projectLead;
    }

    public DoubleFilter getProgressPercent() {
        return progressPercent;
    }

    public DoubleFilter progressPercent() {
        if (progressPercent == null) {
            progressPercent = new DoubleFilter();
        }
        return progressPercent;
    }

    public void setProgressPercent(DoubleFilter progressPercent) {
        this.progressPercent = progressPercent;
    }

    public LongFilter getOpenTasksNo() {
        return openTasksNo;
    }

    public LongFilter openTasksNo() {
        if (openTasksNo == null) {
            openTasksNo = new LongFilter();
        }
        return openTasksNo;
    }

    public void setOpenTasksNo(LongFilter openTasksNo) {
        this.openTasksNo = openTasksNo;
    }

    public LongFilter getCompleteTasksNo() {
        return completeTasksNo;
    }

    public LongFilter completeTasksNo() {
        if (completeTasksNo == null) {
            completeTasksNo = new LongFilter();
        }
        return completeTasksNo;
    }

    public void setCompleteTasksNo(LongFilter completeTasksNo) {
        this.completeTasksNo = completeTasksNo;
    }

    public StringFilter getProjectFile() {
        return projectFile;
    }

    public StringFilter projectFile() {
        if (projectFile == null) {
            projectFile = new StringFilter();
        }
        return projectFile;
    }

    public void setProjectFile(StringFilter projectFile) {
        this.projectFile = projectFile;
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
        final ProjectsCriteria that = (ProjectsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(clientName, that.clientName) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(costType, that.costType) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(deadLine, that.deadLine) &&
            Objects.equals(status, that.status) &&
            Objects.equals(projectLead, that.projectLead) &&
            Objects.equals(progressPercent, that.progressPercent) &&
            Objects.equals(openTasksNo, that.openTasksNo) &&
            Objects.equals(completeTasksNo, that.completeTasksNo) &&
            Objects.equals(projectFile, that.projectFile) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            projectName,
            description,
            clientName,
            cost,
            costType,
            priority,
            startDate,
            endDate,
            deadLine,
            status,
            projectLead,
            progressPercent,
            openTasksNo,
            completeTasksNo,
            projectFile,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            companyId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (projectName != null ? "projectName=" + projectName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (clientName != null ? "clientName=" + clientName + ", " : "") +
            (cost != null ? "cost=" + cost + ", " : "") +
            (costType != null ? "costType=" + costType + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (deadLine != null ? "deadLine=" + deadLine + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (projectLead != null ? "projectLead=" + projectLead + ", " : "") +
            (progressPercent != null ? "progressPercent=" + progressPercent + ", " : "") +
            (openTasksNo != null ? "openTasksNo=" + openTasksNo + ", " : "") +
            (completeTasksNo != null ? "completeTasksNo=" + completeTasksNo + ", " : "") +
            (projectFile != null ? "projectFile=" + projectFile + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
