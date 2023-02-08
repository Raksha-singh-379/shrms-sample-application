package com.techvg.shrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Taluka.
 */
@Entity
@Table(name = "taluka")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Taluka implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "taluka_name", nullable = false)
    private String talukaName;

    @Column(name = "lgd_code")
    private Long lgdCode;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Taluka id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public Taluka talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Long getLgdCode() {
        return this.lgdCode;
    }

    public Taluka lgdCode(Long lgdCode) {
        this.setLgdCode(lgdCode);
        return this;
    }

    public void setLgdCode(Long lgdCode) {
        this.lgdCode = lgdCode;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Taluka createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Taluka createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDeleted() {
        return this.deleted;
    }

    public Taluka deleted(String deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Taluka lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Taluka lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taluka)) {
            return false;
        }
        return id != null && id.equals(((Taluka) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taluka{" +
            "id=" + getId() +
            ", talukaName='" + getTalukaName() + "'" +
            ", lgdCode=" + getLgdCode() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
