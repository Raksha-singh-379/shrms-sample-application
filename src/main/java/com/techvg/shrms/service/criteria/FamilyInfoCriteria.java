package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.Relationship;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.FamilyInfo} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.FamilyInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /family-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FamilyInfoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Relationship
     */
    public static class RelationshipFilter extends Filter<Relationship> {

        public RelationshipFilter() {}

        public RelationshipFilter(RelationshipFilter filter) {
            super(filter);
        }

        @Override
        public RelationshipFilter copy() {
            return new RelationshipFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter dob;

    private RelationshipFilter relation;

    private StringFilter phoneNo;

    private StringFilter address;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private LongFilter employeeId;

    private Boolean distinct;

    public FamilyInfoCriteria() {}

    public FamilyInfoCriteria(FamilyInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.phoneNo = other.phoneNo == null ? null : other.phoneNo.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FamilyInfoCriteria copy() {
        return new FamilyInfoCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public RelationshipFilter getRelation() {
        return relation;
    }

    public RelationshipFilter relation() {
        if (relation == null) {
            relation = new RelationshipFilter();
        }
        return relation;
    }

    public void setRelation(RelationshipFilter relation) {
        this.relation = relation;
    }

    public StringFilter getPhoneNo() {
        return phoneNo;
    }

    public StringFilter phoneNo() {
        if (phoneNo == null) {
            phoneNo = new StringFilter();
        }
        return phoneNo;
    }

    public void setPhoneNo(StringFilter phoneNo) {
        this.phoneNo = phoneNo;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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
        final FamilyInfoCriteria that = (FamilyInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(phoneNo, that.phoneNo) &&
            Objects.equals(address, that.address) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            dob,
            relation,
            phoneNo,
            address,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            deleted,
            employeeId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (phoneNo != null ? "phoneNo=" + phoneNo + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
