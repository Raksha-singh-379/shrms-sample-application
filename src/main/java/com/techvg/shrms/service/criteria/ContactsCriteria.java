package com.techvg.shrms.service.criteria;

import com.techvg.shrms.domain.enumeration.ContactType;
import com.techvg.shrms.domain.enumeration.Relationship;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.shrms.domain.Contacts} entity. This class is used
 * in {@link com.techvg.shrms.web.rest.ContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ContactType
     */
    public static class ContactTypeFilter extends Filter<ContactType> {

        public ContactTypeFilter() {}

        public ContactTypeFilter(ContactTypeFilter filter) {
            super(filter);
        }

        @Override
        public ContactTypeFilter copy() {
            return new ContactTypeFilter(this);
        }
    }

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

    private ContactTypeFilter type;

    private RelationshipFilter relation;

    private StringFilter phoneNo1;

    private StringFilter phoneNo2;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter deleted;

    private LongFilter employeeId;

    private LongFilter employeeId;

    private Boolean distinct;

    public ContactsCriteria() {}

    public ContactsCriteria(ContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.phoneNo1 = other.phoneNo1 == null ? null : other.phoneNo1.copy();
        this.phoneNo2 = other.phoneNo2 == null ? null : other.phoneNo2.copy();
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
    public ContactsCriteria copy() {
        return new ContactsCriteria(this);
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

    public ContactTypeFilter getType() {
        return type;
    }

    public ContactTypeFilter type() {
        if (type == null) {
            type = new ContactTypeFilter();
        }
        return type;
    }

    public void setType(ContactTypeFilter type) {
        this.type = type;
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

    public StringFilter getPhoneNo1() {
        return phoneNo1;
    }

    public StringFilter phoneNo1() {
        if (phoneNo1 == null) {
            phoneNo1 = new StringFilter();
        }
        return phoneNo1;
    }

    public void setPhoneNo1(StringFilter phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public StringFilter getPhoneNo2() {
        return phoneNo2;
    }

    public StringFilter phoneNo2() {
        if (phoneNo2 == null) {
            phoneNo2 = new StringFilter();
        }
        return phoneNo2;
    }

    public void setPhoneNo2(StringFilter phoneNo2) {
        this.phoneNo2 = phoneNo2;
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
        final ContactsCriteria that = (ContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(phoneNo1, that.phoneNo1) &&
            Objects.equals(phoneNo2, that.phoneNo2) &&
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
            type,
            relation,
            phoneNo1,
            phoneNo2,
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
        return "ContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (phoneNo1 != null ? "phoneNo1=" + phoneNo1 + ", " : "") +
            (phoneNo2 != null ? "phoneNo2=" + phoneNo2 + ", " : "") +
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
